package com.nju.edu.erp.service;

import com.nju.edu.erp.enums.sheetState.PurchaseSheetState;
import com.nju.edu.erp.model.po.PurchaseSheetIODetailPO;
import com.nju.edu.erp.model.po.SaleSheetIODetailPO;
import com.nju.edu.erp.model.vo.UserVO;
import com.nju.edu.erp.model.vo.purchase.PurchaseSheetVO;

import java.text.ParseException;
import java.util.List;

// 制定进货单 + 销售经理审批/总经理二级审批 + 更新客户表/制定入库单草稿 + 库存管理人员确认入库单/总经理审批 + 更新库存
public interface PurchaseService {
    /**
     * 制定进货单
     * @param purchaseSheetVO 进货单
     */
    void makePurchaseSheet(UserVO userVO, PurchaseSheetVO purchaseSheetVO);

    /**
     * 根据状态获取进货单(state == null 则获取所有进货单)
     * @param state 进货单状态
     * @return 进货单
     */
    List<PurchaseSheetVO> getPurchaseSheetByState(PurchaseSheetState state);

    /**
     * 根据进货单id进行审批(state == "待二级审批"/"审批完成"/"审批失败")
     * 在controller层进行权限控制
     * @param purchaseSheetId 进货单id
     * @param state 进货单修改后的状态
     */
    void approval(String purchaseSheetId, PurchaseSheetState state);

    /**
     * 根据进货单Id搜索进货单信息
     * @param purchaseSheetId 进货单Id
     * @return 进货单全部信息
     */
    PurchaseSheetVO getPurchaseSheetById(String purchaseSheetId);


    /**
     * 单据查看：设定一个时间段，查看此时间段内的进货/退货单据
     *
     * @param beginDateStr 开始时间字符串
     * @param endDateStr   结束时间字符串
     * @return
     * @throws ParseException
     */
    List<PurchaseSheetIODetailPO> getPurchaseSheetIODetailByTime(String beginDateStr, String endDateStr) throws ParseException;


    /**
     * 获取一段时间里的进货总额
     *
     * @param beginDateStr 开始日期
     * @param endDateStr   结束日期
     * @return 进货总额
     * @throws ParseException
     */
    int getInputByTime(String beginDateStr, String endDateStr) throws ParseException;
}
