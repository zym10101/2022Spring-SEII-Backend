package com.nju.edu.erp.service;

import com.nju.edu.erp.dao.SaleSheetDao;
import com.nju.edu.erp.enums.sheetState.SaleSheetState;
import com.nju.edu.erp.model.po.sale.SaleSheetPO;
import com.nju.edu.erp.model.vo.salary.BaseAndCommissionVO;
import com.nju.edu.erp.model.vo.employee.EmployeePostVO;
import com.nju.edu.erp.model.vo.employee.EmployeeVO;
import com.nju.edu.erp.model.vo.salary.MonthlyPayVO;
import com.nju.edu.erp.dao.EmployeePostDao;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootTest
public class StrategyServiceTest {
    @Autowired
    EmployeePostService employeePostService;
    @Autowired
    AttendanceService attendanceService;
    @Autowired
    EmployeeService employeeService;
    @Autowired
    EmployeePostDao employeePostDao;
    @Autowired
    SaleSheetDao saleSheetDao;

    /**
     * 月薪制员工，初级财务人员，缺勤一次
     */
    @Test
    @Transactional
    @Rollback(value = true)
    public void employeeTest_1() throws ParseException {
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
        EmployeeVO employeeVO = EmployeeVO.builder()
                .id(110)
                .name("小王")
                .sex("男")
                .birthday(dateformat.parse("1992-3-14"))
                .phoneNumber("15103328749")
                .build();
        employeeService.addEmployee(employeeVO);
        EmployeePostVO employeePostVO = EmployeePostVO.builder()
                .id(110)
                .bankAccount("1009787865554534560")
                .position("财务人员")
                .salaryCalculationStrategy("月薪制")
                .salaryStrategyResult(0)  //应发月薪和年终奖初始化为0
                .yearBonus(0)
                .build();
        employeePostService.addEmployeePostInfo(employeePostVO);
        MonthlyPayVO monthlyPayVO = MonthlyPayVO.builder()
                .id(110)
                .baseSalary(3000)
                .postSalary(0)
                .postRank("初级")
                .build();
        employeePostService.setMonthlyPara(monthlyPayVO);
        //缺勤一次，基本工资降为2900
        attendanceService.updateBaseSalary(110);
        employeePostService.calculateEmployeeSalary("2022-6-1","2022-6-30",110);
        Assertions.assertEquals(2900, employeePostDao.findEmployeePostById(employeePostVO.getId()).getSalaryStrategyResult());
    }

    /**
     * 提成制员工，高级销售人员，全勤
     */
    @Test
    @Transactional
    @Rollback(value = true)
    public void employeeTest_2() throws ParseException {
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
        EmployeeVO employeeVO = EmployeeVO.builder()
                .id(99)
                .name("小李")
                .sex("男")
                .birthday(dateformat.parse("1988-05-05"))
                .phoneNumber("18099897766")
                .build();
        employeeService.addEmployee(employeeVO);
        EmployeePostVO employeePostVO = EmployeePostVO.builder()
                .id(99)
                .bankAccount("3352987600989546337")
                .position("销售人员")
                .salaryCalculationStrategy("提成制")
                .salaryStrategyResult(0)  //应发月薪和年终奖初始化为0
                .yearBonus(0)
                .build();
        employeePostService.addEmployeePostInfo(employeePostVO);
        BaseAndCommissionVO baseAndCommissionVO = BaseAndCommissionVO.builder()
                .id(99)
                .baseSalary(3000)
                .commissionRate(3)  //提成比率百分之3
                .actualSales(0)
                .build();
        employeePostService.setBaseAndCommissionPara(baseAndCommissionVO);
        //------------需要填写销售额和退货额表单------------
        Date date = new Date();
        SaleSheetPO saleSheetPO = SaleSheetPO.builder()
                .id("XSD-20220623-00000")
                .supplier(1)
                .salesman("小李")
                .operator("xiaoshoujingli")
                .remark("")
                .finalAmount(BigDecimal.valueOf(8900))
                .state(SaleSheetState.SUCCESS)
                .createTime(dateformat.parse("2022-06-23"))
                .build();
        saleSheetDao.saveSheet(saleSheetPO);
        employeePostService.calculateEmployeeSalary("2022-6-1","2022-6-30",99);
        Assertions.assertEquals(3267, employeePostDao.findEmployeePostById(employeePostVO.getId()).getSalaryStrategyResult());
    }

}
