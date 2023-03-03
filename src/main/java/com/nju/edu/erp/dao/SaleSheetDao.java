package com.nju.edu.erp.dao;


import com.nju.edu.erp.enums.sheetState.SaleSheetState;
import com.nju.edu.erp.model.po.customer.CustomerPurchaseAmountPO;
import com.nju.edu.erp.model.po.saleIO.SaleIODetailPO;
import com.nju.edu.erp.model.po.sale.SaleSheetContentPO;
import com.nju.edu.erp.model.po.saleIO.SaleSheetIODetailPO;
import com.nju.edu.erp.model.po.sale.SaleSheetPO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Repository
@Mapper
public interface SaleSheetDao {

    /**
     * 获取最近一条销售单
     *
     * @return
     */
    SaleSheetPO getLatestSheet();

    /**
     * 存入一条销售单记录
     *
     * @param toSave 一条销售单记录
     * @return 影响的行数
     */
    int saveSheet(SaleSheetPO toSave);

    /**
     * 把销售单上的具体内容存入数据库
     *
     * @param saleSheetContent 入销售单上的具体内容
     */
    int saveBatchSheetContent(List<SaleSheetContentPO> saleSheetContent);

    /**
     * 查找所有销售单
     */
    List<SaleSheetPO> findAllSheet();


    /**
     * 根据state返回销售单
     *
     * @param state 销售单状态
     * @return 销售单列表
     */
    List<SaleSheetPO> findAllByState(SaleSheetState state);

    /**
     * 查找指定id的销售单
     *
     * @param id
     * @return
     */
    SaleSheetPO findSheetById(String id);

    /**
     * 查找指定销售单下具体的商品内容
     *
     * @param sheetId
     */
    List<SaleSheetContentPO> findContentBySheetId(String sheetId);

    /**
     * 更新指定销售单的状态
     *
     * @param sheetId
     * @param state
     * @return
     */
    int updateSheetState(String sheetId, SaleSheetState state);

    /**
     * 根据当前状态更新销售单状态
     *
     * @param sheetId
     * @param prev
     * @param state
     * @return
     */
    int updateSheetStateOnPrev(String sheetId, SaleSheetState prev, SaleSheetState state);


    /**
     * 获取某个销售人员某段时间内消费总金额最大的客户(不考虑退货情况,销售单不需要审批通过,如果这样的客户有多个，仅保留一个)
     *
     * @param salesman  销售人员的名字
     * @param beginTime 开始时间
     * @param endTime   结束时间
     * @return
     */
    CustomerPurchaseAmountPO getMaxAmountCustomerOfSalesmanByTime(String salesman, Date beginTime, Date endTime);

    /**
     * 获取一段时间内的销售/销售退货单据
     *
     * @param beginTime 开始时间
     * @param endTime   结束时间
     * @return
     */
    List<SaleSheetIODetailPO> getSaleSheetIODetailByTime(Date beginTime, Date endTime);

    /**
     * 获取一段时间里的销售总收入
     *
     * @param beginTime 开始日期
     * @param endTime   结束日期
     * @return 销售总收入
     */
    int getIncomeByTime(Date beginTime, Date endTime);

    /**
     * 获取一段时间里的销售总折让
     *
     * @param beginTime 开始日期
     * @param endTime   结束日期
     * @return 销售总折让
     */
    int getDiscountByTime(Date beginTime, Date endTime);

    /**
     * 获取一段时间里的代金券总额
     *
     * @param beginTime 开始日期
     * @param endTime   结束日期
     * @return 代金券总额
     */
    int getVoucherByTime(Date beginTime, Date endTime);


    /**
     * 获取一段时间里的销售成本
     *
     * @param beginTime 开始日期
     * @param endTime   结束日期
     * @return 销售成本
     */
    int getCostByTime(Date beginTime, Date endTime);

    /**
     * 查询指定时间区间，商品名，客户，业务员的销售/销售退货记录
     * @param beginTime
     * @param endTime
     */
    List<SaleIODetailPO> getSaleIODetailByTime(Date beginTime, Date endTime, String productName, String customer, String salesman);

    /**
     * 根据指定时间区间和销售员查询销售金额
     * @para beginTime
     * @para endTime
     * @para salesmanName
     */
    BigDecimal getMonthlySales(Date beginTime, Date endTime,String salesmanName);
}
