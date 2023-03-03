package com.nju.edu.erp.service;

import com.nju.edu.erp.enums.sheetState.SaleReturnsSheetState;
import com.nju.edu.erp.model.vo.user.UserVO;
import com.nju.edu.erp.model.vo.saleReturns.SaleReturnsSheetVO;

import java.text.ParseException;
import java.util.List;

// 制定销售退货单 + 销售经理审批/总经理二级审批 + 更新客户表 + 更新库存
public interface SaleReturnsService {
    /**
     * 制定销售退货单
     * @param saleReturnsSheetVO 销售退货单
     */
    void makeSaleReturnsSheet(UserVO userVO, SaleReturnsSheetVO saleReturnsSheetVO);

    /**
     * 根据状态获取销售退货单(state == null 则获取所有销售退货单)
     * @param state 销售退货单状态
     * @return 销售退货单
     */
    List<SaleReturnsSheetVO> getSaleReturnsSheetByState(SaleReturnsSheetState state);

    /**
     * 根据销售退货单id进行审批(state == "待二级审批"/"审批完成"/"审批失败")
     * 在controller层进行权限控制
     * @param saleReturnsSheetId 销售退货单id
     * @param state 销售退货单修改后的状态
     */
    void approval(String saleReturnsSheetId, SaleReturnsSheetState state);

    /**
     * 获取一段时间里的销售退货总额
     *
     * @param beginDateStr 开始日期
     * @param endDateStr   结束日期
     * @return 销售退货总额
     */
    int getOutgoByTime(String beginDateStr, String endDateStr);

    /**
     * 获取一段时间里的销售退货成本
     *
     * @param beginDateStr 开始日期
     * @param endDateStr   结束日期
     * @return 销售退货成本
     */
    int getReturnCostByTime(String beginDateStr, String endDateStr) throws ParseException;
}
