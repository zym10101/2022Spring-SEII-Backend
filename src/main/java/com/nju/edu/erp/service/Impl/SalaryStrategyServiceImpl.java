package com.nju.edu.erp.service.Impl;

import com.nju.edu.erp.dao.SalaryStrategyDao;
import com.nju.edu.erp.exception.MyServiceException;
import com.nju.edu.erp.model.po.salary.BaseAndCommissionPO;
import com.nju.edu.erp.model.po.salary.MonthlyPayPO;
import com.nju.edu.erp.model.po.salary.YearlyPayPO;
import com.nju.edu.erp.model.vo.salary.BaseAndCommissionVO;
import com.nju.edu.erp.model.vo.salary.MonthlyPayVO;
import com.nju.edu.erp.model.vo.salary.YearlyPayVO;
import com.nju.edu.erp.service.SalaryStrategyService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SalaryStrategyServiceImpl implements SalaryStrategyService {
    private final SalaryStrategyDao salaryStrategyDao;

    @Autowired
    public SalaryStrategyServiceImpl(SalaryStrategyDao salaryStrategyDao) {
        this.salaryStrategyDao = salaryStrategyDao;
    }

    @Override
    public void setMonthlyPara(MonthlyPayVO monthlyPayVO) {
        MonthlyPayPO monthlyPayPO = salaryStrategyDao.getMonthlyInfo(monthlyPayVO.getId());
        if (monthlyPayPO != null) {
            throw new MyServiceException("A0001", "该员工已存在");
        }
        MonthlyPayPO monthlyPayPOSave = new MonthlyPayPO();
        BeanUtils.copyProperties(monthlyPayPO, monthlyPayPOSave);
        salaryStrategyDao.setMonthlyParaSql(monthlyPayPOSave);
    }

    @Override
    public void setBaseAndCommissionPara(BaseAndCommissionVO baseAndCommissionVO) {
        BaseAndCommissionPO baseAndCommissionPO = salaryStrategyDao.getBaseAndCommissionInfo(baseAndCommissionVO.getId());
        if (baseAndCommissionPO != null) {
            throw new MyServiceException("A0001", "该员工已存在");
        }
        BaseAndCommissionPO baseAndCommissionPOSave = new BaseAndCommissionPO();
        BeanUtils.copyProperties(baseAndCommissionPO,baseAndCommissionPOSave);
        salaryStrategyDao.setBaseAndCommissionParaSql(baseAndCommissionPOSave);
    }

    @Override
    public void setYearlyPara(YearlyPayVO yearlyPayVO) {
        YearlyPayPO yearlyPayPO = salaryStrategyDao.getYearlyInfo(yearlyPayVO.getId());
        if (yearlyPayPO != null) {
            throw new MyServiceException("A0001", "该员工已存在");
        }
        YearlyPayPO yearlyPayPOSave = new YearlyPayPO();
        BeanUtils.copyProperties(yearlyPayPO,yearlyPayPOSave);
        salaryStrategyDao.setYearlyParaSql(yearlyPayPOSave);
    }

    @Override
    public void calculateEmployeeSalary(Integer id) {

    }
}
