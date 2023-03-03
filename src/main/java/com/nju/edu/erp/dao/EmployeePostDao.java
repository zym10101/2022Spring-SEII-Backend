package com.nju.edu.erp.dao;


import com.nju.edu.erp.model.po.salary.BaseAndCommissionPO;
import com.nju.edu.erp.model.po.employee.EmployeePostPO;
import com.nju.edu.erp.model.po.salary.MonthlyPayPO;
import com.nju.edu.erp.model.po.salary.YearlyPayPO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
@Repository
@Mapper

public interface EmployeePostDao {
    //添加员工职务信息，不包括薪资计算结果，另赋
    void addEmployeePostSql(EmployeePostPO employeePostPO);

    //更新员工职务信息
    void updateEmployeePostSql(EmployeePostPO employeePostPO);

    //删除员工职务信息
    void deleteEmployeePostSql(Integer id);

    //查找员工职务信息
    EmployeePostPO findEmployeePostById(Integer id);

    //设置薪资计算结果
    void setCalResultSql(Integer id,Integer salaryStrategyResult);

    //设置年终奖计算结果
    void setYearBonusResultSql(Integer id,Integer yearBonus);

    //获取年终奖计算结果
    Integer getYearBonusSql(Integer id);

    //设置月薪参数
    void setMonthlyParaSql(MonthlyPayPO monthlyPayPO);

    //设置提成参数
    void setBaseAndCommissionParaSql(BaseAndCommissionPO baseAndCommissionPO);

    //设置年薪制参数
    void setYearlyParaSql(YearlyPayPO yearlyPayPO);

    //查找月薪参数
    MonthlyPayPO getMonthlyParaSql(Integer id);

    //查找提成参数
    BaseAndCommissionPO getBaseAndCommissionParaSql(Integer id);

    //查找年薪制参数
    YearlyPayPO getYearlyParaSql(Integer id);

    //修改月薪参数
    void updateMonthlyParaSql(MonthlyPayPO monthlyPayPO);

    //修改提成参数
    void updateBaseAndCommissionParaSql(BaseAndCommissionPO baseAndCommissionPO);

    //修改年薪制参数
    void updateYearlyParaSql(YearlyPayPO yearlyPayPO);

    //删除月薪参数某条记录
    void deleteMonthlyParaSql(Integer id);

    //删除提成参数某条记录
    void deleteBaseAndCommissionParaSql(Integer id);

    //删除年薪参数某条记录
    void deleteYearlyParaSql(Integer id);

    //设置实际销售额
    void setActualSalesSql(Integer id,Integer actualSales);
}
