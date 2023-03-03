package com.nju.edu.erp.service.Impl;

import com.nju.edu.erp.dao.EmployeeDao;
import com.nju.edu.erp.dao.EmployeePostDao;
import com.nju.edu.erp.exception.MyServiceException;
import com.nju.edu.erp.model.po.employee.EmployeePO;
import com.nju.edu.erp.model.po.employee.EmployeePostPO;
import com.nju.edu.erp.model.po.employee.EmployeeWageIODetailPO;
import com.nju.edu.erp.model.vo.employee.EmployeeVO;
import com.nju.edu.erp.service.EmployeeService;
import com.nju.edu.erp.service.WageService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeDao employeeDao;
    private final WageService wageService;
    private final EmployeePostDao employeePostDao;

    @Autowired
    public EmployeeServiceImpl(EmployeeDao employeeDao, WageService wageService, EmployeePostDao employeePostDao) {
        this.employeeDao = employeeDao;
        this.wageService = wageService;
        this.employeePostDao = employeePostDao;
    }

    @Override
    public void addEmployee(EmployeeVO employeeVO) {
        //先检查这个员工是否存在

        EmployeePO employeePO = employeeDao.findEmployeeSql(employeeVO.getId());
        if (employeePO != null) {
            throw new MyServiceException("A0001", "该员工已存在");
        }
        EmployeePO employeePOSave = new EmployeePO();
        BeanUtils.copyProperties(employeeVO, employeePOSave);
        employeeDao.addEmployeeSql(employeePOSave);

    }

    @Override
    public void updateEmployee(EmployeePO employeePO) {
        employeeDao.updateEmployeeSql(employeePO);
    }

    @Override
    public void deleteEmployee(Integer id) {
        //先检查这个用户是否存在，需要级联删除post信息表和参数表
        EmployeePO employeePO = employeeDao.findEmployeeSql(id);
        if (employeePO == null) {
            throw new MyServiceException("A0001", "该员工不存在");
        }
        EmployeePostPO employeePostPO = employeePostDao.findEmployeePostById(id);
        employeeDao.deleteEmployeeSql(id);
        employeePostDao.deleteEmployeePostSql(id);
        if (employeePostPO.getSalaryCalculationStrategy().equals("月薪制")) {
            employeePostDao.deleteMonthlyParaSql(id);
        } else if(employeePostPO.getSalaryCalculationStrategy().equals("提成制")) {
            employeePostDao.deleteBaseAndCommissionParaSql(id);
        } else if(employeePostPO.getSalaryCalculationStrategy().equals("年薪制")) {
            employeePostDao.deleteYearlyParaSql(id);
        }
    }

    @Override
    public EmployeePO findEmployeeById(Integer id) {
        return employeeDao.findEmployeeSql(id);
    }

    @Override
    public List<EmployeeWageIODetailPO> findAllEmployee() {
        List<EmployeeWageIODetailPO> allEmployee = employeeDao.findAllEmployee();
        for (EmployeeWageIODetailPO employeeWageIODetailPO : allEmployee) {
            Integer id = employeeWageIODetailPO.getId();
            BigDecimal elevenMonthsSalary = wageService.getElevenMonthsSalary(id);
            employeeWageIODetailPO.setTotal(elevenMonthsSalary);
            Integer yearBonusSql = employeePostDao.getYearBonusSql(id);
            employeeWageIODetailPO.setYearBonus(yearBonusSql);
        }
        return allEmployee;
    }


}
