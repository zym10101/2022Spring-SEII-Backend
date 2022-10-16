package com.nju.edu.erp.service.Impl;

import com.nju.edu.erp.dao.*;
import com.nju.edu.erp.exception.MyServiceException;
import com.nju.edu.erp.model.po.*;
import com.nju.edu.erp.model.vo.BaseAndCommissionVO;
import com.nju.edu.erp.model.vo.EmployeePostVO;
import com.nju.edu.erp.model.vo.MonthlyPayVO;
import com.nju.edu.erp.model.vo.YearlyPayVO;
import com.nju.edu.erp.service.EmployeePostService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class EmployeePostServiceImpl implements EmployeePostService {
    private final EmployeePostDao employeePostDao;
    private final SalaryStrategyDao salaryStrategyDao;
    private final SaleSheetDao saleSheetDao;
    private final EmployeeDao employeeDao;
    private final SaleReturnsSheetDao saleReturnsSheetDao;

    @Autowired
    public EmployeePostServiceImpl(EmployeePostDao employeePostDao, SalaryStrategyDao salaryStrategyDao, SaleSheetDao saleSheetDao, EmployeeDao employeeDao, SaleReturnsSheetDao saleReturnsSheetDao) {
        this.employeePostDao = employeePostDao;
        this.salaryStrategyDao = salaryStrategyDao;
        this.saleSheetDao = saleSheetDao;
        this.employeeDao = employeeDao;
        this.saleReturnsSheetDao = saleReturnsSheetDao;
    }


    @Override
    public void addEmployeePostInfo(EmployeePostVO employeePostVO) {
        Integer id = employeePostVO.getId();
        EmployeePostPO employeePostPO = employeePostDao.findEmployeePostById(id);
        if (employeePostPO != null) {
            throw new MyServiceException("A0001", "该员工已存在");
        }
        EmployeePostPO employeePostPOSave = new EmployeePostPO();
        BeanUtils.copyProperties(employeePostVO, employeePostPOSave);
        employeePostDao.addEmployeePostSql(employeePostPOSave);
        employeePostDao.setCalResultSql(id,0);
        employeePostDao.setYearBonusResultSql(id,0);
    }

    @Override
    public void updateEmployeePostInfo(EmployeePostPO employeePostPO) {
        employeePostDao.updateEmployeePostSql(employeePostPO);
    }

    @Override
    public void deleteEmployeePostInfo(Integer id) {
        EmployeePostPO employeePostPO = employeePostDao.findEmployeePostById(id);
        if (employeePostPO == null) {
            throw new MyServiceException("A0001", "该员工不存在");
        }

        employeePostDao.deleteEmployeePostSql(id);
    }

    @Override
    public EmployeePostPO findEmployeePostInfoById(Integer id) {
        return employeePostDao.findEmployeePostById(id);
    }

    @Override
    public void calculateEmployeeSalary(String beginTimeStr, String endTimeStr, Integer id) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date beginTime = sdf.parse(beginTimeStr);
        Date endTime = sdf.parse(endTimeStr);
        EmployeePostPO employeePostPO = employeePostDao.findEmployeePostById(id);
        EmployeePO employeePO = employeeDao.findEmployeeSql(id);
        String salesName = employeePO.getName();
        Integer calResult = null;
        if (employeePostPO.getSalaryCalculationStrategy().equals("月薪制")) {
            MonthlyPayPO monthlyPayPO = salaryStrategyDao.getMonthlyInfo(id);
            Integer baseSalary = monthlyPayPO.getBaseSalary();
            Integer postSalary = monthlyPayPO.getPostSalary();
            calResult = baseSalary + postSalary;
        } else if (employeePostPO.getSalaryCalculationStrategy().equals("提成制")) {
            BaseAndCommissionPO baseAndCommissionPO = salaryStrategyDao.getBaseAndCommissionInfo(id);
            Integer baseSalary = baseAndCommissionPO.getBaseSalary();
            Integer commissionRate = baseAndCommissionPO.getCommissionRate();
            String monthSales = saleSheetDao.getMonthlySales(beginTime, endTime, salesName).toString();
            Integer sales = Integer.parseInt(monthSales.substring(0, monthSales.length() - 3));
            String monthSaleReturns = saleReturnsSheetDao.getMonthlyReturnSales(beginTime, endTime, salesName).toString();
            Integer returnSales = Integer.parseInt(monthSaleReturns.substring(0, monthSaleReturns.length() - 3));
            calResult = baseSalary + commissionRate * (sales - returnSales) / 100;
        } else if (employeePostPO.getSalaryCalculationStrategy().equals("年薪制")) {
            YearlyPayPO yearlyPayPO = salaryStrategyDao.getYearlyInfo(id);
            Integer yearlySalary = yearlyPayPO.getYearlySalary();
            calResult = yearlySalary / 12;
        }

        employeePostDao.setCalResultSql(id, calResult);
    }

    @Override
    public void setYearBonusResult(Integer id, Integer yearBonus) {
        employeePostDao.setYearBonusResultSql(id, yearBonus);
    }

    @Override
    public void setMonthlyPara(MonthlyPayVO monthlyPayVO) {
        MonthlyPayPO monthlyPayPOSave = new MonthlyPayPO();
        BeanUtils.copyProperties(monthlyPayVO,monthlyPayPOSave);
        employeePostDao.setMonthlyParaSql(monthlyPayPOSave);
    }

    @Override
    public void setBaseAndCommissionPara(BaseAndCommissionVO baseAndCommissionVO) {
        BaseAndCommissionPO baseAndCommissionPOSave = new BaseAndCommissionPO();
        BeanUtils.copyProperties(baseAndCommissionVO,baseAndCommissionPOSave);
        employeePostDao.setBaseAndCommissionParaSql(baseAndCommissionPOSave);
        //增加的时候将ActualSales初始化为0
        employeePostDao.setActualSalesSql(baseAndCommissionVO.getId(), 0);
    }

    @Override
    public void setYearlyPara(YearlyPayVO yearlyPayVO) {
        YearlyPayPO yearlyPayPOSave = new YearlyPayPO();
        BeanUtils.copyProperties(yearlyPayVO,yearlyPayPOSave);
        employeePostDao.setYearlyParaSql(yearlyPayPOSave);
    }

    @Override
    public void updateMonthlyPara(MonthlyPayPO monthlyPayPO) {
        employeePostDao.updateMonthlyParaSql(monthlyPayPO);
    }

    @Override
    public void updateBaseAndCommissionPara(BaseAndCommissionPO baseAndCommissionPO) {
        employeePostDao.updateBaseAndCommissionParaSql(baseAndCommissionPO);
    }

    @Override
    public void updateYearlyPara(YearlyPayPO yearlyPayPO) {
        employeePostDao.updateYearlyParaSql(yearlyPayPO);
    }
}
