package com.nju.edu.erp.dao;

import com.nju.edu.erp.model.po.employee.*;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper

public interface EmployeeDao {
    //操作数据库，添加员工
    int addEmployeeSql(EmployeePO employeePO);

    //操作数据库，更新员工信息
    //Service里的update没有返回值，参考customer的service和Dao
    int updateEmployeeSql(EmployeePO employeePO);

    //操作数据库，删除指定员工
    void deleteEmployeeSql(Integer id);

    //操作数据库，根据id查找员工信息
    EmployeePO findEmployeeSql(Integer id);

    //查找所有员工信息
    List<EmployeeWageIODetailPO> findAllEmployee();

    /**
     * 查找两张表内员工信息
     */
    List<EmployeeInfoUnionGeneralPO> findGeneralInfoUnion();

    /**
     * 查找两张表内月薪制员工信息
     */
    List<EmployeeInfoUnionMonthlyPO> findMonthlyInfoUnion();

    /**
     * 查找两张表内年薪制员工信息
     */
    List<EmployeeInfoUnionYearlyPO> findYearlyInfoUnion();

    /**
     * 查找两张表内提成制员工信息
     */
    List<EmployeeInfoUnionCommissionPO> findCommissionInfoUnion();


}



