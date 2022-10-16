package com.nju.edu.erp.service;

import com.nju.edu.erp.enums.sheetState.SaleSheetState;
import com.nju.edu.erp.model.po.CustomerPurchaseAmountPO;
import com.nju.edu.erp.model.po.SaleIODetailPO;
import com.nju.edu.erp.model.po.SaleSheetIODetailPO;
import com.nju.edu.erp.model.vo.sale.SaleSheetVO;
import com.nju.edu.erp.model.vo.UserVO;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public interface SaleService {

    /**
     * 制定销售单
     *
     * @param userVO
     * @param saleSheetVO
     */
    void makeSaleSheet(UserVO userVO, SaleSheetVO saleSheetVO);

    /**
     * 根据单据状态获取销售单
     *
     * @param state
     * @return
     */
    List<SaleSheetVO> getSaleSheetByState(SaleSheetState state);

    /**
     * 审批单据
     *
     * @param saleSheetId
     * @param state
     */
    void approval(String saleSheetId, SaleSheetState state);

    /**
     * 获取某个销售人员某段时间内消费总金额最大的客户(不考虑退货情况,销售单不需要审批通过,如果这样的客户有多个，仅保留一个)
     *
     * @param salesman     销售人员的名字
     * @param beginDateStr 开始时间字符串
     * @param endDateStr   结束时间字符串
     * @return
     */
    CustomerPurchaseAmountPO getMaxAmountCustomerOfSalesmanByTime(String salesman, String beginDateStr, String endDateStr);

    /**
     * 根据销售单Id搜索销售单信息
     *
     * @param saleSheetId 销售单Id
     * @return 销售单全部信息
     */
    SaleSheetVO getSaleSheetById(String saleSheetId);


    /**
     * 单据查看：设定一个时间段，查看此时间段内的销售/销售退货单据
     *
     * @param beginDateStr 开始时间字符串
     * @param endDateStr   结束时间字符串
     * @return
     * @throws ParseException
     */
    List<SaleSheetIODetailPO> getSaleSheetIODetailByTime(String beginDateStr, String endDateStr) throws ParseException;

    /**
     * 获取一段时间里的销售总收入
     *
     * @param beginDateStr 开始日期
     * @param endDateStr   结束日期
     * @return 销售总收入
     * @throws ParseException
     */
    int getIncomeByTime(String beginDateStr, String endDateStr) throws ParseException;

    /**
     * 获取一段时间里的销售总折让
     *
     * @param beginDateStr 开始日期
     * @param endDateStr   结束日期
     * @return 销售总折让
     */
    int getDiscountByTime(String beginDateStr, String endDateStr) throws ParseException;

    /**
     * 获取一段时间里的代金券总额
     *
     * @param beginDateStr 开始日期
     * @param endDateStr   结束日期
     * @return 代金券总额
     */
    int getVoucherByTime(String beginDateStr, String endDateStr) throws ParseException;

    /**
     * 获取一段时间里的销售成本
     *
     * @param beginDateStr 开始日期
     * @param endDateStr   结束日期
     * @return 销售成本
     */
    int getCostByTime(String beginDateStr, String endDateStr) throws ParseException;

    /**
     * 查看销售明细表
     *
     * @param beginDateStr 开始时间字符串
     * @param endDateStr   结束时间字符串
     * @return
     * @throws ParseException
     */
    List<SaleIODetailPO> getSaleIODetailByTime(String beginDateStr, String endDateStr, String productName, String customer, String salesman) throws ParseException;
}
