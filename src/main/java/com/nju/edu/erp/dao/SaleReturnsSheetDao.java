package com.nju.edu.erp.dao;

import com.nju.edu.erp.enums.sheetState.SaleReturnsSheetState;
import com.nju.edu.erp.model.po.saleReturn.SaleReturnsSheetContentPO;
import com.nju.edu.erp.model.po.saleReturn.SaleReturnsSheetPO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Repository
@Mapper
public interface SaleReturnsSheetDao {
    /**
     * 获取最近一条销售退货单
     *
     * @return 最近一条销售退货单
     */
    SaleReturnsSheetPO getLatest();

    /**
     * 存入一条销售退货单记录
     *
     * @param toSave 一条销售退货单记录
     * @return 影响的行数
     */
    int save(SaleReturnsSheetPO toSave);

    /**
     * 把销售退货单上的具体内容存入数据库
     *
     * @param SaleReturnsSheetContent 销售退货单上的具体内容
     */
    void saveBatch(List<SaleReturnsSheetContentPO> SaleReturnsSheetContent);

    /**
     * 返回所有销售退货单
     *
     * @return 销售退货单列表
     */
    List<SaleReturnsSheetPO> findAll();

    /**
     * 根据state返回销售退货单
     *
     * @param state 销售退货单状态
     * @return 销售退货单列表
     */
    List<SaleReturnsSheetPO> findAllByState(SaleReturnsSheetState state);

    /**
     * 根据 saleReturnsSheetId 找到条目， 并更新其状态为state
     *
     * @param saleReturnsSheetId 销售退货单id
     * @param state              销售退货单状态
     * @return 影响的条目数
     */
    int updateState(String saleReturnsSheetId, SaleReturnsSheetState state);

    /**
     * 根据 saleReturnsSheetId 和 prevState 找到条目， 并更新其状态为state
     *
     * @param saleReturnsSheetId 销售退货单id
     * @param prevState          销售退货单之前的状态
     * @param state              销售退货单状态
     * @return 影响的条目数
     */
    int updateStateV2(String saleReturnsSheetId, SaleReturnsSheetState prevState, SaleReturnsSheetState state);

    /**
     * 通过saleReturnsSheetId找到对应条目
     *
     * @param saleReturnsSheetId 销售退货单id
     * @return
     */
    SaleReturnsSheetPO findOneById(String saleReturnsSheetId);

    /**
     * 通过saleReturnsSheetId找到对应的content条目
     *
     * @param saleReturnsSheetId
     * @return
     */
    List<SaleReturnsSheetContentPO> findContentBySaleReturnsSheetId(String saleReturnsSheetId);

    /**
     * 通过saleReturnsSheetId找到退的货的对应批次
     *
     * @param saleReturnsSheetId
     * @return 批次号
     */
    Integer findBatchId(String saleReturnsSheetId);

    /**
     * 获取一段时间里的销售退货总金额
     *
     * @param beginTime 开始时间
     * @param endTime   结束时间
     * @return 销售退货总金额
     */
    int getOutgoByTime(Date beginTime, Date endTime);

    /**
     * 获取一段时间里的销售退货成本
     *
     * @param beginTime 开始日期
     * @param endTime   结束日期
     * @return 销售退货成本
     */
    int getReturnCostByTime(Date beginTime, Date endTime);

    /**
     * 根据指定时间区间和销售员查询退货金额
     */
    BigDecimal getMonthlyReturnSales(Date beginTime, Date endTime, String salesmanName);
}
