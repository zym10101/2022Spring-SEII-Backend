package com.nju.edu.erp.service;

import com.nju.edu.erp.enums.sheetState.WageSheetState;
import com.nju.edu.erp.model.vo.UserVO;
import com.nju.edu.erp.model.vo.WageSheetVO;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public interface WageService {

    /**
     * 指定工资单
     * @param userVO
     * @param wageSheetVO
     */
    void makeWageSheet(UserVO userVO, WageSheetVO wageSheetVO);

    /**
     * 根据单据状态获取工资单
     * @param state
     * @return
     */
    List<WageSheetVO> getWageSheetByState(WageSheetState state);

    /**
     * 审批单据
     * @param wageSheetId
     * @param state
     */
    void approval(String wageSheetId, WageSheetState state);

    /**
     * 获取一段时间里的所有员工的工资总额
     *
     * @param beginDateStr
     * @param endDateStr
     * @return 工资总额
     */
    int getSalaryByTime(String beginDateStr, String endDateStr);

    /**
     * 获取员工前十一个月税前工资总额
     */
    BigDecimal getElevenMonthsSalary(Integer employeeId);
}

