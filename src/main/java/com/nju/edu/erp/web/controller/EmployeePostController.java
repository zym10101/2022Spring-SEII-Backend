package com.nju.edu.erp.web.controller;

import com.nju.edu.erp.model.po.salary.BaseAndCommissionPO;
import com.nju.edu.erp.model.po.employee.EmployeePostPO;
import com.nju.edu.erp.model.po.salary.MonthlyPayPO;
import com.nju.edu.erp.model.po.salary.YearlyPayPO;
import com.nju.edu.erp.model.vo.salary.BaseAndCommissionVO;
import com.nju.edu.erp.model.vo.employee.EmployeePostVO;
import com.nju.edu.erp.model.vo.salary.MonthlyPayVO;
import com.nju.edu.erp.model.vo.salary.YearlyPayVO;
import com.nju.edu.erp.web.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.nju.edu.erp.service.EmployeePostService;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping(path = "/employee-post")
public class EmployeePostController {
    private final EmployeePostService employeePostService;

    @Autowired
    public EmployeePostController(EmployeePostService employeePostService) {
        this.employeePostService = employeePostService;
    }

    @PostMapping("/add")
    public Response add(@RequestBody EmployeePostVO employeePostVO) {
        employeePostService.addEmployeePostInfo(employeePostVO);
        return Response.buildSuccess();
    }

    @PostMapping("/update")
    public Response update(@RequestBody EmployeePostPO employeePostPO) {
        employeePostService.updateEmployeePostInfo(employeePostPO);
        return Response.buildSuccess();
    }

    @GetMapping("/delete")
    public Response delete(@RequestParam Integer id) {
        employeePostService.deleteEmployeePostInfo(id);
        return Response.buildSuccess();
    }

    @GetMapping("/findById")
    public Response findById(@RequestParam Integer id) {
        return Response.buildSuccess(employeePostService.findEmployeePostInfoById(id));
    }

    @GetMapping("/calSalary")
    public Response calSalary(@RequestParam("beginTimeStr") String beginTimeStr,@RequestParam("endTimeStr") String endTimeStr,@RequestParam("id") Integer id) throws ParseException {
        employeePostService.calculateEmployeeSalary(beginTimeStr, endTimeStr, id);
        return Response.buildSuccess();
    }

    @GetMapping("/addYearBonus")
    public Response addYearBonus(@RequestParam Integer id, @RequestParam Integer yearBonus) {
        employeePostService.setYearBonusResult(id, yearBonus);
        return Response.buildSuccess();
    }

    @PostMapping("/addParaM")
    public Response addMonthlyPayPara(@RequestBody MonthlyPayVO monthlyPayVO) {
        employeePostService.setMonthlyPara(monthlyPayVO);
        return Response.buildSuccess();
    }

    @PostMapping("/addParaA")
    public Response addBaseAndCommissionPara(@RequestBody BaseAndCommissionVO baseAndCommissionVO) {
        employeePostService.setBaseAndCommissionPara(baseAndCommissionVO);
        return Response.buildSuccess();
    }

    @PostMapping("/addParaY")
    public Response addYearlyPayPara(@RequestBody YearlyPayVO yearlyPayVO) {
        employeePostService.setYearlyPara(yearlyPayVO);
        return Response.buildSuccess();
    }

    @PostMapping("/updateM")
    public Response updateM(@RequestBody MonthlyPayPO monthlyPayPO) {
        employeePostService.updateMonthlyPara(monthlyPayPO);
        return Response.buildSuccess();
    }

    @PostMapping("/updateB")
    public Response updateB(@RequestBody BaseAndCommissionPO baseAndCommissionPO) {
        employeePostService.updateBaseAndCommissionPara(baseAndCommissionPO);
        return Response.buildSuccess();
    }

    @PostMapping("/updateY")
    public Response updateY(@RequestBody YearlyPayPO yearlyPayPO) {
        employeePostService.updateYearlyPara(yearlyPayPO);
        return Response.buildSuccess();
    }

}
