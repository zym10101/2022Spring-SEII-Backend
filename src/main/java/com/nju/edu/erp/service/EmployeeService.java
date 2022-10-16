package com.nju.edu.erp.service;
import com.nju.edu.erp.model.po.EmployeePO;
import com.nju.edu.erp.model.po.EmployeeWageIODetailPO;
import com.nju.edu.erp.model.vo.EmployeeVO;

import java.util.List;

public interface EmployeeService {
    //添加员工信息，Dao-sddEmployeeSql
    void addEmployee(EmployeeVO employeeVO);

    //更新员工信息，用PO封装一个已经修改好的对象,Dao-updateEmployeeSql
    void updateEmployee(EmployeePO employeePO);

    //删除员工,Dao-deleteEmployeeSql
    void deleteEmployee(Integer id);

    //查找员工信息
    EmployeePO findEmployeeById(Integer id);

    //查找所有员工信息
    List<EmployeeWageIODetailPO> findAllEmployee();



}
