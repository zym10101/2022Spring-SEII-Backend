package com.nju.edu.erp.service;

import com.nju.edu.erp.model.vo.BaseAndCommissionVO;
import com.nju.edu.erp.model.vo.MonthlyPayVO;
import com.nju.edu.erp.model.vo.YearlyPayVO;

public interface SalaryStrategyService {
    //设置月薪参数
    void setMonthlyPara(MonthlyPayVO monthlyPayVO);

    //设置提成制参数
    void setBaseAndCommissionPara(BaseAndCommissionVO baseAndCommissionVO);

    //设置年薪制参数
    void setYearlyPara(YearlyPayVO yearlyPayVO);

    //计算员工薪资
    void calculateEmployeeSalary(Integer id);
}
