package com.nju.edu.erp.service.Impl;

import com.nju.edu.erp.dao.AccountDao;
import com.nju.edu.erp.dao.EmployeeDao;
import com.nju.edu.erp.dao.EmployeePostDao;
import com.nju.edu.erp.dao.WageSheetDao;
import com.nju.edu.erp.enums.sheetState.WageSheetState;
import com.nju.edu.erp.model.po.*;
import com.nju.edu.erp.model.vo.UserVO;
import com.nju.edu.erp.model.vo.WageSheetVO;
import com.nju.edu.erp.service.WageService;
import com.nju.edu.erp.utils.IdGenerator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class WageServiceImpl implements WageService {

    private final WageSheetDao wageSheetDao;

    private final EmployeeDao employeeDao;

    private final AccountDao accountDao;

    private final EmployeePostDao employeePostDao;

    @Autowired
    public WageServiceImpl(WageSheetDao wageSheetDao, EmployeeDao employeeDao, AccountDao accountDao, EmployeePostDao employeePostDao) {
        this.wageSheetDao = wageSheetDao;
        this.employeeDao = employeeDao;
        this.accountDao = accountDao;
        this.employeePostDao = employeePostDao;
    }

    @Override
    @Transactional
    public void makeWageSheet(UserVO userVO, WageSheetVO wageSheetVO) {
        WageSheetPO wageSheetPO = new WageSheetPO();
        BeanUtils.copyProperties(wageSheetVO, wageSheetPO);
        wageSheetPO.setOperator(userVO.getName());
        wageSheetPO.setCreateTime(new Date());
        WageSheetPO latestSheet = wageSheetDao.getLatestSheet();
        String id = IdGenerator.generateSheetId(latestSheet == null ? null : latestSheet.getId(), "GZD");
        wageSheetPO.setId(id);
        wageSheetPO.setState(WageSheetState.PENDING_LEVEL_1);
        Integer employeeId = wageSheetPO.getEmployeeId();
        EmployeePO employeePO = employeeDao.findEmployeeSql(employeeId);
        wageSheetPO.setName(employeePO.getName());
        EmployeePostPO employeePostPO = employeePostDao.findEmployeePostById(employeeId);
        BigDecimal totalAmount = BigDecimal.valueOf(employeePostPO.getSalaryStrategyResult());
        wageSheetPO.setBankAccount(employeePostPO.getBankAccount());
        if(wageSheetPO.isCalBonus()){
            BigDecimal yearBonus = BigDecimal.valueOf(employeePostPO.getYearBonus());
            totalAmount = totalAmount.add(yearBonus);
        }
        wageSheetPO.setTotalAmount(totalAmount);
        BigDecimal tax = getTax(totalAmount);
        wageSheetPO.setTax(tax);
        BigDecimal finalAmount = wageSheetPO.getTotalAmount().subtract(wageSheetPO.getTax());
        wageSheetPO.setFinalAmount(finalAmount);
        wageSheetDao.saveSheet(wageSheetPO);
    }

    private BigDecimal getTax(BigDecimal totalAmount) {
        BigDecimal[] floor = {BigDecimal.valueOf(0), BigDecimal.valueOf(3000), BigDecimal.valueOf(12000), BigDecimal.valueOf(25000), BigDecimal.valueOf(35000), BigDecimal.valueOf(55000), BigDecimal.valueOf(80000)};
        BigDecimal[] ceil = {BigDecimal.valueOf(3000), BigDecimal.valueOf(12000), BigDecimal.valueOf(25000), BigDecimal.valueOf(35000), BigDecimal.valueOf(55000), BigDecimal.valueOf(80000),BigDecimal.valueOf(Integer.MAX_VALUE)};
        BigDecimal[] rate = {BigDecimal.valueOf(0.03), BigDecimal.valueOf(0.1), BigDecimal.valueOf(0.2), BigDecimal.valueOf(0.25), BigDecimal.valueOf(0.3), BigDecimal.valueOf(0.35), BigDecimal.valueOf(0.45)};
        BigDecimal[] deduction = {BigDecimal.valueOf(0), BigDecimal.valueOf(210), BigDecimal.valueOf(1410), BigDecimal.valueOf(2660), BigDecimal.valueOf(4410), BigDecimal.valueOf(7160), BigDecimal.valueOf(15160)};

        BigDecimal ans = BigDecimal.valueOf(-1);
        for (int i = 0; i < 7; i ++){
            int result1 = totalAmount.compareTo(floor[i]);
            int result2 = totalAmount.compareTo(ceil[i]);
            if(result1 == 1 && result2 < 1){
                ans = totalAmount.multiply(rate[i]).subtract(deduction[i]);
                break;
            }
        }
        return ans;
    }

    @Override
    public List<WageSheetVO> getWageSheetByState(WageSheetState state) {
        List<WageSheetVO> res = new ArrayList<>();
        List<WageSheetPO> all;
        if (state == null) {
            all = wageSheetDao.findAllSheet();
        } else {
            all = wageSheetDao.findAllByState(state);
        }
        for (WageSheetPO po : all) {
            WageSheetVO vo = new WageSheetVO();
            BeanUtils.copyProperties(po, vo);
            res.add(vo);
        }
        return res;
    }

    @Override
    @Transactional
    public void approval(String wageSheetId, WageSheetState state) {
        if (state.equals(WageSheetState.FAILURE)) {
            WageSheetPO wageSheet = wageSheetDao.findSheetById(wageSheetId);
            if (wageSheet.getState().equals(WageSheetState.SUCCESS)) {
                throw new RuntimeException("状态更新失败");
            }
            int effectLines = wageSheetDao.updateSheetState(wageSheetId, state);
            if (effectLines == 0) {
                throw new RuntimeException("状态更新失败");
            }
        } else {
            WageSheetState prevState;
            if (state.equals(WageSheetState.SUCCESS)) {
                prevState = WageSheetState.PENDING_LEVEL_2;
            } else if (state.equals(WageSheetState.PENDING_LEVEL_2)){
                prevState = WageSheetState.PENDING_LEVEL_1;
            } else{
                throw new RuntimeException("状态更新失败");
            }
            int effectLines = wageSheetDao.updateSheetStateOnPrev(wageSheetId, prevState, state);
            if (effectLines == 0) {
                throw new RuntimeException("状态更新失败");
            }
            if (state.equals(WageSheetState.SUCCESS)) {
                AccountPO accountPO = accountDao.findByName("gongzi");
                BigDecimal pre = accountPO.getBalance();
                WageSheetPO wageSheetPO = wageSheetDao.findSheetById(wageSheetId);
                BigDecimal deduction = wageSheetPO.getFinalAmount();
                accountPO.setBalance(pre.subtract(deduction));
                accountDao.updateBalance(accountPO);
            }
        }
    }

    @Override
    public int getSalaryByTime(String beginDateStr, String endDateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date beginDate, endDate;
        int salary = 0;
        try {
            beginDate = sdf.parse(beginDateStr);
            endDate = sdf.parse(endDateStr);
            if (beginDate.before(endDate)) {
                salary = wageSheetDao.getSalaryByTime(beginDate, endDate);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return salary;
    }

    @Override
    public BigDecimal getElevenMonthsSalary(Integer employeeId) {
        EmployeePostPO employeePostPO = employeePostDao.findEmployeePostById(employeeId);
        BigDecimal totalAmount = BigDecimal.valueOf(employeePostPO.getSalaryStrategyResult());
        return totalAmount.multiply(BigDecimal.valueOf(11));
    }

}

