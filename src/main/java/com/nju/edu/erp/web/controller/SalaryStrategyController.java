package com.nju.edu.erp.web.controller;

import com.nju.edu.erp.model.vo.salary.BaseAndCommissionVO;
import com.nju.edu.erp.model.vo.salary.MonthlyPayVO;
import com.nju.edu.erp.model.vo.salary.YearlyPayVO;
import com.nju.edu.erp.service.SalaryStrategyService;
import com.nju.edu.erp.web.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/salary-strategy")
public class SalaryStrategyController {
    private final SalaryStrategyService salaryStrategyService;

    @Autowired
    public SalaryStrategyController(SalaryStrategyService salaryStrategyService) {
        this.salaryStrategyService = salaryStrategyService;
    }

    @PostMapping("/add-m")
    public Response addMonthlyPara(@RequestBody MonthlyPayVO monthlyPayVO) {
        salaryStrategyService.setMonthlyPara(monthlyPayVO);
        return Response.buildSuccess();
    }

    @PostMapping("/add-b")
    public Response addBaseAndCommissionPara(@RequestBody BaseAndCommissionVO baseAndCommissionVO) {
        salaryStrategyService.setBaseAndCommissionPara(baseAndCommissionVO);
        return Response.buildSuccess();
    }

    @PostMapping("/add-y")
    public Response addYearlyPara(@RequestBody YearlyPayVO yearlyPayVO) {
        salaryStrategyService.setYearlyPara(yearlyPayVO);
        return Response.buildSuccess();
    }

}
