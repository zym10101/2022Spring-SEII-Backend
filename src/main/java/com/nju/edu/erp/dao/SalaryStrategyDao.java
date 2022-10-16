package com.nju.edu.erp.dao;

import com.nju.edu.erp.model.po.BaseAndCommissionPO;
import com.nju.edu.erp.model.po.MonthlyPayPO;
import com.nju.edu.erp.model.po.YearlyPayPO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface SalaryStrategyDao {
    //设置月薪参数
    void setMonthlyParaSql(MonthlyPayPO monthlyPayPO);

    //设置提成制参数
    void setBaseAndCommissionParaSql(BaseAndCommissionPO baseAndCommissionPO);

    //设置年薪制参数
    void setYearlyParaSql(YearlyPayPO yearlyPayPO);

    //根据id获取某员工薪资信息,只供ServiceImpl使用
    MonthlyPayPO getMonthlyInfo(Integer id);

    BaseAndCommissionPO getBaseAndCommissionInfo(Integer id);

    YearlyPayPO getYearlyInfo(Integer id);


}
