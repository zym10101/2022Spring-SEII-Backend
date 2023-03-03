package com.nju.edu.erp.service;

import com.nju.edu.erp.model.po.pay.PayReceiveSheetIODetailPO;
import com.nju.edu.erp.model.po.salary.WageSheetPO;

import java.util.List;

/**
 * @author zym
 * @date 2022/06/29 23:19
 **/
public interface FinancialService {
    /**
     * 获取所有收付款单
     * @param beginDateStr 开始日期
     * @param endDateStr 结束日期
     * @return
     */
    List<PayReceiveSheetIODetailPO> getPayReceiveSheet(String beginDateStr, String endDateStr);

    /**
     * 获取所有工资单
     * @param beginDateStr 开始日期
     * @param endDateStr 结束日期
     * @return
     */
    List<WageSheetPO> getWageSheet(String beginDateStr, String endDateStr);
}
