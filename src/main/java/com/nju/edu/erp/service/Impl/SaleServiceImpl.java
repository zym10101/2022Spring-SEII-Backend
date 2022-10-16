package com.nju.edu.erp.service.Impl;

import com.nju.edu.erp.dao.CustomerDao;
import com.nju.edu.erp.dao.ProductDao;
import com.nju.edu.erp.dao.PromotionDao;
import com.nju.edu.erp.dao.SaleSheetDao;
import com.nju.edu.erp.enums.sheetState.SaleSheetState;
import com.nju.edu.erp.enums.sheetState.WarehouseZSDState;
import com.nju.edu.erp.model.po.*;
import com.nju.edu.erp.model.vo.ProductInfoVO;
import com.nju.edu.erp.model.vo.sale.SaleSheetContentVO;
import com.nju.edu.erp.model.vo.sale.SaleSheetVO;
import com.nju.edu.erp.model.vo.UserVO;
import com.nju.edu.erp.model.vo.warehouse.WarehouseOutputFormContentVO;
import com.nju.edu.erp.model.vo.warehouse.WarehouseOutputFormVO;
import com.nju.edu.erp.service.*;
import com.nju.edu.erp.utils.IdGenerator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class SaleServiceImpl implements SaleService {

    private final SaleSheetDao saleSheetDao;

    private final ProductDao productDao;

    private final CustomerDao customerDao;

    private final PromotionDao promotionDao;

    private final ProductService productService;

    private final CustomerService customerService;

    private final WarehouseService warehouseService;

    private final PromotionService promotionService;

    @Autowired
    public SaleServiceImpl(SaleSheetDao saleSheetDao, ProductDao productDao, CustomerDao customerDao, PromotionDao promotionDao, ProductService productService, CustomerService customerService, WarehouseService warehouseService, PromotionService promotionService) {
        this.saleSheetDao = saleSheetDao;
        this.productDao = productDao;
        this.customerDao = customerDao;
        this.promotionDao = promotionDao;
        this.productService = productService;
        this.customerService = customerService;
        this.warehouseService = warehouseService;
        this.promotionService = promotionService;
    }

    @Override
    @Transactional
    public void makeSaleSheet(UserVO userVO, SaleSheetVO saleSheetVO) {
        // 需要持久化销售单（SaleSheet）和销售单content（SaleSheetContent），其中总价或者折后价格的计算需要在后端进行
        // 需要的service和dao层相关方法均已提供，可以不用自己再实现一遍
        SaleSheetPO saleSheetPO = new SaleSheetPO();
        BeanUtils.copyProperties(saleSheetVO, saleSheetPO);
        saleSheetPO.setOperator(userVO.getName());
        saleSheetPO.setCreateTime(new Date());
        SaleSheetPO latestSheet = saleSheetDao.getLatestSheet();
        String id = IdGenerator.generateSheetId(latestSheet == null ? null : latestSheet.getId(), "XSD");
        saleSheetPO.setId(id);
        saleSheetPO.setState(SaleSheetState.PENDING_LEVEL_1);
        BigDecimal totalAmount = BigDecimal.ZERO;
        List<SaleSheetContentPO> sContentPOList = new ArrayList<>();
        for (SaleSheetContentVO content : saleSheetVO.getSaleSheetContent()) {
            SaleSheetContentPO sContentPO = new SaleSheetContentPO();
            BeanUtils.copyProperties(content, sContentPO);
            sContentPO.setSaleSheetId(id);
            BigDecimal unitPrice = sContentPO.getUnitPrice();
            if (unitPrice == null) {
                ProductPO product = productDao.findById(content.getPid());
                unitPrice = product.getPurchasePrice();
                sContentPO.setUnitPrice(unitPrice);
            }
            sContentPO.setTotalPrice(unitPrice.multiply(BigDecimal.valueOf(sContentPO.getQuantity())));
            sContentPOList.add(sContentPO);
            totalAmount = totalAmount.add(sContentPO.getTotalPrice());
        }
        saleSheetDao.saveBatchSheetContent(sContentPOList);
        saleSheetPO.setRawTotalAmount(totalAmount);

        // 考虑总价促销策略
        Integer voucher1 = useTotalAmountPromotion(userVO, totalAmount);
        // 考虑用户等级促销策略
        MyRes myRes = useUserLevelPromotion(saleSheetPO, userVO);

        int voucher = voucher1 + myRes.voucher2;
        BigDecimal finalAmount = totalAmount.multiply(myRes.discount).subtract(BigDecimal.valueOf(voucher));

        saleSheetPO.setDiscount(myRes.discount);
        saleSheetPO.setVoucherAmount(BigDecimal.valueOf(voucher));

        //若finalAmount小于0，置0
        if (finalAmount.compareTo(BigDecimal.valueOf(0)) < 0) {
            finalAmount = BigDecimal.valueOf(0);
        }

        saleSheetPO.setFinalAmount(finalAmount);
        saleSheetDao.saveSheet(saleSheetPO);

    }


    @Override
    @Transactional
    public List<SaleSheetVO> getSaleSheetByState(SaleSheetState state) {
        // 根据单据状态获取销售单（注意：VO包含SaleSheetContent）
        // 依赖的dao层部分方法未提供，需要自己实现
        List<SaleSheetVO> res = new ArrayList<>();
        List<SaleSheetPO> all;
        if (state == null) {
            all = saleSheetDao.findAllSheet();
        } else {
            all = saleSheetDao.findAllByState(state);
        }
        for (SaleSheetPO po : all) {
            SaleSheetVO vo = new SaleSheetVO();
            BeanUtils.copyProperties(po, vo);
            List<SaleSheetContentPO> alll = saleSheetDao.findContentBySheetId(po.getId());
            List<SaleSheetContentVO> vos = new ArrayList<>();
            for (SaleSheetContentPO p : alll) {
                SaleSheetContentVO v = new SaleSheetContentVO();
                BeanUtils.copyProperties(p, v);
                vos.add(v);
            }
            vo.setSaleSheetContent(vos);
            res.add(vo);
        }
        return res;
    }

    /**
     * 根据销售单id进行审批(state == "待二级审批"/"审批完成"/"审批失败")
     * 在controller层进行权限控制
     *
     * @param saleSheetId 销售单id
     * @param state       销售单要达到的状态
     */
    @Override
    @Transactional
    public void approval(String saleSheetId, SaleSheetState state) {
        // 需要的service和dao层相关方法均已提供，可以不用自己再实现一遍
        /* 一些注意点：
            1. 二级审批成功之后需要进行
                 1. 修改单据状态
                 2. 更新商品表
                 3. 更新客户表
                 4. 新建出库草稿
            2. 一级审批状态不能直接到审批完成状态； 二级审批状态不能回到一级审批状态
         */
        if (state.equals(SaleSheetState.FAILURE)) {
            SaleSheetPO saleSheet = saleSheetDao.findSheetById(saleSheetId);
            if (saleSheet.getState().equals(SaleSheetState.SUCCESS)) {
                throw new RuntimeException("状态更新失败");
            }
            int effectLines = saleSheetDao.updateSheetState(saleSheetId, state);
            if (effectLines == 0) {
                throw new RuntimeException("状态更新失败");
            }
        } else {
            SaleSheetState prevState;
            if (state.equals(SaleSheetState.SUCCESS)) {
                prevState = SaleSheetState.PENDING_LEVEL_2;
            } else if (state.equals(SaleSheetState.PENDING_LEVEL_2)) {
                prevState = SaleSheetState.PENDING_LEVEL_1;
            } else {
                throw new RuntimeException("状态更新失败");
            }
            int effectLines = saleSheetDao.updateSheetStateOnPrev(saleSheetId, prevState, state);
            if (effectLines == 0) {
                throw new RuntimeException("状态更新失败");
            }
            if (state.equals(SaleSheetState.SUCCESS)) {
                // 更新商品表的最新售价
                List<SaleSheetContentPO> saleSheetContent = saleSheetDao.findContentBySheetId(saleSheetId);
                List<WarehouseOutputFormContentVO> warehouseOutputFormContentVOS = new ArrayList<>();

                for (SaleSheetContentPO content : saleSheetContent) {
                    ProductInfoVO productInfoVO = new ProductInfoVO();
                    productInfoVO.setId(content.getPid());
                    productInfoVO.setRecentRp(content.getUnitPrice());
                    productService.updateProduct(productInfoVO);

                    WarehouseOutputFormContentVO woContentVO = new WarehouseOutputFormContentVO();
                    woContentVO.setSalePrice(content.getUnitPrice());
                    woContentVO.setQuantity(content.getQuantity());
                    woContentVO.setRemark(content.getRemark());
                    woContentVO.setPid(content.getPid());
                    warehouseOutputFormContentVOS.add(woContentVO);
                }
                // 更新客户表(更新应收字段)
                // 更新应收 payable
                SaleSheetPO saleSheetPO = saleSheetDao.findSheetById(saleSheetId);
                CustomerPO customerPO = customerService.findCustomerById(saleSheetPO.getSupplier());
                customerPO.setReceivable(customerPO.getReceivable().add(saleSheetPO.getFinalAmount()));
                customerService.updateCustomer(customerPO);

                // 制定出库单草稿
                // 调用创建出库单的方法
                WarehouseOutputFormVO warehouseOutputFormVO = new WarehouseOutputFormVO();
                warehouseOutputFormVO.setOperator(null); // 暂时不填操作人
                warehouseOutputFormVO.setSaleSheetId(saleSheetId);
                warehouseOutputFormVO.setList(warehouseOutputFormContentVOS);
                warehouseService.productOutOfWarehouse(warehouseOutputFormVO);
            }
        }
    }

    /**
     * 获取某个销售人员某段时间内消费总金额最大的客户(不考虑退货情况,销售单不需要审批通过,如果这样的客户有多个，仅保留一个)
     *
     * @param salesman     销售人员的名字
     * @param beginDateStr 开始时间字符串
     * @param endDateStr   结束时间字符串
     * @return
     */
    public CustomerPurchaseAmountPO getMaxAmountCustomerOfSalesmanByTime(String salesman, String beginDateStr, String endDateStr) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date beginTime = dateFormat.parse(beginDateStr);
            Date endTime = dateFormat.parse(endDateStr);
            if (beginTime.compareTo(endTime) > 0) {
                return null;
            } else {
                return saleSheetDao.getMaxAmountCustomerOfSalesmanByTime(salesman, beginTime, endTime);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据销售单Id搜索销售单信息
     *
     * @param saleSheetId 销售单Id
     * @return 销售单全部信息
     */
    @Override
    public SaleSheetVO getSaleSheetById(String saleSheetId) {
        SaleSheetPO saleSheetPO = saleSheetDao.findSheetById(saleSheetId);
        if (saleSheetPO == null) return null;
        List<SaleSheetContentPO> contentPO = saleSheetDao.findContentBySheetId(saleSheetId);
        SaleSheetVO sVO = new SaleSheetVO();
        BeanUtils.copyProperties(saleSheetPO, sVO);
        List<SaleSheetContentVO> saleSheetContentVOList = new ArrayList<>();
        for (SaleSheetContentPO content :
                contentPO) {
            SaleSheetContentVO sContentVO = new SaleSheetContentVO();
            BeanUtils.copyProperties(content, sContentVO);
            saleSheetContentVOList.add(sContentVO);
        }
        sVO.setSaleSheetContent(saleSheetContentVOList);
        return sVO;
    }

    @Override
    public List<SaleSheetIODetailPO> getSaleSheetIODetailByTime(String beginDateStr, String endDateStr) {
        /**
         * 1.注意日期的格式转换和转换异常
         * 2.考虑开始时间大于结束时间的情况、查询结果为空的情况
         * 3.Dao层和service层接口已实现
         *
         */
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date beginTime = sdf.parse(beginDateStr);
            Date endTime = sdf.parse(endDateStr);
            if (beginTime.before(endTime)) {
                return saleSheetDao.getSaleSheetIODetailByTime(beginTime, endTime);
            } else {
                return null;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }


    @Override
    public int getIncomeByTime(String beginDateStr, String endDateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date beginDate, endDate;
        int income = 0;
        try {
            beginDate = sdf.parse(beginDateStr);
            endDate = sdf.parse(endDateStr);
            if (beginDate.before(endDate)) {
                income = saleSheetDao.getIncomeByTime(beginDate, endDate);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return income;
    }

    @Override
    public int getDiscountByTime(String beginDateStr, String endDateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date beginDate, endDate;
        int discount = 0;
        try {
            beginDate = sdf.parse(beginDateStr);
            endDate = sdf.parse(endDateStr);
            if (beginDate.before(endDate)) {
                discount = saleSheetDao.getDiscountByTime(beginDate, endDate);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return discount;
    }

    @Override
    public int getVoucherByTime(String beginDateStr, String endDateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date beginDate, endDate;
        int voucher = 0;
        try {
            beginDate = sdf.parse(beginDateStr);
            endDate = sdf.parse(endDateStr);
            if (beginDate.before(endDate)) {
                voucher = saleSheetDao.getVoucherByTime(beginDate, endDate);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return voucher;
    }

    @Override
    public int getCostByTime(String beginDateStr, String endDateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date beginDate, endDate;
        int cost = 0;
        try {
            beginDate = sdf.parse(beginDateStr);
            endDate = sdf.parse(endDateStr);
            if (beginDate.before(endDate)) {
                cost = saleSheetDao.getCostByTime(beginDate, endDate);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return cost;
    }

    @Override
    public List<SaleIODetailPO> getSaleIODetailByTime(String beginDateStr, String endDateStr, String productName, String customer, String salesman) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date beginTime = sdf.parse(beginDateStr);
            Date endTime = sdf.parse(endDateStr);
            if (beginTime.before(endTime)) {
                return saleSheetDao.getSaleIODetailByTime(beginTime, endTime, productName, customer, salesman);
            } else {
                return null;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    private Integer useTotalAmountPromotion(UserVO userVO, BigDecimal totalAmount) {
        List<Integer> amountNumAll = promotionService.findAmountNumAll();
        amountNumAll.sort((o1, o2) -> o2 - o1);
        Integer voucher1 = 0;
        for (Integer num : amountNumAll) {
            if (totalAmount.compareTo(BigDecimal.valueOf(num)) > -1) {
                TotalAmountPromotionPO oneByAmount = promotionService.findOneByAmount(num);
                if (new Date().getTime() <= oneByAmount.getEndTime().getTime() && new Date().getTime() >= oneByAmount.getBeginTime().getTime()) {
                    voucher1 = oneByAmount.getVoucher();
                    ProductPO productPO = productDao.findByName(oneByAmount.getGiftName());
                    if (productPO != null) {
                        String newWarehouseZSDId = warehouseService.getNewWarehouseZSDId();
                        WarehouseZSDPO warehouseZSDPO = new WarehouseZSDPO(newWarehouseZSDId, 0, userVO.getName(), new Date(), WarehouseZSDState.SUCCESS, productPO.getId(), 1, productPO.getPurchasePrice(), null, null);
                        warehouseService.productGiftOutOfWarehouse(warehouseZSDPO);
                    }
                    break;
                }
            }
        }
        return voucher1;
    }

    private MyRes useUserLevelPromotion(SaleSheetPO saleSheetPO, UserVO userVO) {
        BigDecimal discount = BigDecimal.valueOf(1);
        Integer voucher2 = 0;
        Integer supplier = saleSheetPO.getSupplier();
        Integer level = customerDao.findOneById(supplier).getLevel();
        UserLevelPromotionPO user = promotionService.findOneByLevel(level);
        if (new Date().getTime() <= user.getEndTime().getTime() && new Date().getTime() >= user.getBeginTime().getTime()) {
            ProductPO productPO = productDao.findByName(user.getGiftName());
            if (productPO != null) {
                String newWarehouseZSDId = warehouseService.getNewWarehouseZSDId();
                WarehouseZSDPO warehouseZSDPO = new WarehouseZSDPO(newWarehouseZSDId, 0, userVO.getName(), new Date(), WarehouseZSDState.SUCCESS, productPO.getId(), 1, productPO.getPurchasePrice(), null, null);
                warehouseService.productGiftOutOfWarehouse(warehouseZSDPO);
            }
            discount = user.getDiscount();
            voucher2 = user.getVoucher();
        }
        return new MyRes(voucher2, discount);
    }
}

/**
 * 使用用户等级促销策略，MyRes类保存优惠券和折让
 */
class MyRes {
    Integer voucher2;
    BigDecimal discount;

    public MyRes(Integer voucher2, BigDecimal discount) {
        this.voucher2 = voucher2;
        this.discount = discount;
    }
}
