package com.nju.edu.erp.service;
import com.nju.edu.erp.model.po.salary.BaseAndCommissionPO;
import com.nju.edu.erp.model.po.employee.EmployeePostPO;
import com.nju.edu.erp.model.po.salary.MonthlyPayPO;
import com.nju.edu.erp.model.po.salary.YearlyPayPO;
import com.nju.edu.erp.model.vo.salary.BaseAndCommissionVO;
import com.nju.edu.erp.model.vo.employee.EmployeePostVO;
import com.nju.edu.erp.model.vo.salary.MonthlyPayVO;
import com.nju.edu.erp.model.vo.salary.YearlyPayVO;

import java.text.ParseException;

public interface EmployeePostService {

    //添加员工职务信息
    void addEmployeePostInfo(EmployeePostVO employeePostVO);

    //更新员工职务信息
    void updateEmployeePostInfo(EmployeePostPO employeePostPO);

    //删除员工职务信息
    void deleteEmployeePostInfo(Integer id);

    //查找员工职务信息
    EmployeePostPO findEmployeePostInfoById(Integer id);

    //计算薪资并插入表中
    void calculateEmployeeSalary(String beginTimeStr, String endTimeStr,Integer id) throws ParseException;

    //设置年终奖计算结果
    void setYearBonusResult(Integer id,Integer yearBonus);

    //设置月薪参数
    void setMonthlyPara(MonthlyPayVO monthlyPayVO);

    //设置提成参数
    void setBaseAndCommissionPara(BaseAndCommissionVO baseAndCommissionVO);

    //设置年薪制参数
    void setYearlyPara(YearlyPayVO yearlyPayVO);

    //修改月薪参数
    void updateMonthlyPara(MonthlyPayPO monthlyPayPO);

    //修改提成参数
    void updateBaseAndCommissionPara(BaseAndCommissionPO baseAndCommissionPO);

    //修改年薪制参数
    void updateYearlyPara(YearlyPayPO yearlyPayPO);
}
