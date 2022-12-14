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
        // ???????????????????????????SaleSheet???????????????content???SaleSheetContent??????????????????????????????????????????????????????????????????
        // ?????????service???dao???????????????????????????????????????????????????????????????
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

        // ????????????????????????
        Integer voucher1 = useTotalAmountPromotion(userVO, totalAmount);
        // ??????????????????????????????
        MyRes myRes = useUserLevelPromotion(saleSheetPO, userVO);

        int voucher = voucher1 + myRes.voucher2;
        BigDecimal finalAmount = totalAmount.multiply(myRes.discount).subtract(BigDecimal.valueOf(voucher));

        saleSheetPO.setDiscount(myRes.discount);
        saleSheetPO.setVoucherAmount(BigDecimal.valueOf(voucher));

        //???finalAmount??????0??????0
        if (finalAmount.compareTo(BigDecimal.valueOf(0)) < 0) {
            finalAmount = BigDecimal.valueOf(0);
        }

        saleSheetPO.setFinalAmount(finalAmount);
        saleSheetDao.saveSheet(saleSheetPO);

    }


    @Override
    @Transactional
    public List<SaleSheetVO> getSaleSheetByState(SaleSheetState state) {
        // ?????????????????????????????????????????????VO??????SaleSheetContent???
        // ?????????dao?????????????????????????????????????????????
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
     * ???????????????id????????????(state == "???????????????"/"????????????"/"????????????")
     * ???controller?????????????????????
     *
     * @param saleSheetId ?????????id
     * @param state       ???????????????????????????
     */
    @Override
    @Transactional
    public void approval(String saleSheetId, SaleSheetState state) {
        // ?????????service???dao???????????????????????????????????????????????????????????????
        /* ??????????????????
            1. ????????????????????????????????????
                 1. ??????????????????
                 2. ???????????????
                 3. ???????????????
                 4. ??????????????????
            2. ?????????????????????????????????????????????????????? ????????????????????????????????????????????????
         */
        if (state.equals(SaleSheetState.FAILURE)) {
            SaleSheetPO saleSheet = saleSheetDao.findSheetById(saleSheetId);
            if (saleSheet.getState().equals(SaleSheetState.SUCCESS)) {
                throw new RuntimeException("??????????????????");
            }
            int effectLines = saleSheetDao.updateSheetState(saleSheetId, state);
            if (effectLines == 0) {
                throw new RuntimeException("??????????????????");
            }
        } else {
            SaleSheetState prevState;
            if (state.equals(SaleSheetState.SUCCESS)) {
                prevState = SaleSheetState.PENDING_LEVEL_2;
            } else if (state.equals(SaleSheetState.PENDING_LEVEL_2)) {
                prevState = SaleSheetState.PENDING_LEVEL_1;
            } else {
                throw new RuntimeException("??????????????????");
            }
            int effectLines = saleSheetDao.updateSheetStateOnPrev(saleSheetId, prevState, state);
            if (effectLines == 0) {
                throw new RuntimeException("??????????????????");
            }
            if (state.equals(SaleSheetState.SUCCESS)) {
                // ??????????????????????????????
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
                // ???????????????(??????????????????)
                // ???????????? payable
                SaleSheetPO saleSheetPO = saleSheetDao.findSheetById(saleSheetId);
                CustomerPO customerPO = customerService.findCustomerById(saleSheetPO.getSupplier());
                customerPO.setReceivable(customerPO.getReceivable().add(saleSheetPO.getFinalAmount()));
                customerService.updateCustomer(customerPO);

                // ?????????????????????
                // ??????????????????????????????
                WarehouseOutputFormVO warehouseOutputFormVO = new WarehouseOutputFormVO();
                warehouseOutputFormVO.setOperator(null); // ?????????????????????
                warehouseOutputFormVO.setSaleSheetId(saleSheetId);
                warehouseOutputFormVO.setList(warehouseOutputFormContentVOS);
                warehouseService.productOutOfWarehouse(warehouseOutputFormVO);
            }
        }
    }

    /**
     * ?????????????????????????????????????????????????????????????????????(?????????????????????,??????????????????????????????,????????????????????????????????????????????????)
     *
     * @param salesman     ?????????????????????
     * @param beginDateStr ?????????????????????
     * @param endDateStr   ?????????????????????
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
     * ???????????????Id?????????????????????
     *
     * @param saleSheetId ?????????Id
     * @return ?????????????????????
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
         * 1.??????????????????????????????????????????
         * 2.???????????????????????????????????????????????????????????????????????????
         * 3.Dao??????service??????????????????
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
 * ?????????????????????????????????MyRes???????????????????????????
 */
class MyRes {
    Integer voucher2;
    BigDecimal discount;

    public MyRes(Integer voucher2, BigDecimal discount) {
        this.voucher2 = voucher2;
        this.discount = discount;
    }
}
