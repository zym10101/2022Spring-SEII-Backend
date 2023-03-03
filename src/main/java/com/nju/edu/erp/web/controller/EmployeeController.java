package com.nju.edu.erp.web.controller;

import com.nju.edu.erp.model.po.employee.*;
import com.nju.edu.erp.model.vo.employee.EmployeeVO;
import com.nju.edu.erp.dao.EmployeeDao;
import com.nju.edu.erp.service.EmployeeService;
import com.nju.edu.erp.web.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/employee")
public class EmployeeController {
    private final EmployeeService employeeService;

    private final EmployeeDao employeeDao;

    @Autowired
    public EmployeeController(EmployeeService employeeService, EmployeeDao employeeDao) {
        this.employeeService = employeeService;
        this.employeeDao = employeeDao;
    }

    @PostMapping("/add")
    public Response add(@RequestBody EmployeeVO employeeVO) {
        employeeService.addEmployee(employeeVO);
        return Response.buildSuccess();
    }

    @PostMapping("/update")
    public Response update(@RequestBody EmployeePO employeePO) {
        employeeService.updateEmployee(employeePO);
        return Response.buildSuccess();
    }

    @GetMapping("/delete")
    public Response delete(@RequestParam Integer id) {
        employeeService.deleteEmployee(id);
        return Response.buildSuccess();
    }

    @GetMapping("/findById")
    public Response findById(@RequestParam Integer id) {
        return Response.buildSuccess(employeeService.findEmployeeById(id));
    }

    @GetMapping("/findAll")
    public Response findAll() {
        List<EmployeeWageIODetailPO> allEmployee = employeeService.findAllEmployee();
        return Response.buildSuccess(allEmployee);
    }

    @GetMapping("/findGeneralInfoUnion")
    public Response findGeneralInfoUnion() {
        List<EmployeeInfoUnionGeneralPO> ans = employeeDao.findGeneralInfoUnion();
        return Response.buildSuccess(ans);
    }

    @GetMapping("/findMonthlyInfoUnion")
    public Response findMonthlyInfoUnion() {
        List<EmployeeInfoUnionMonthlyPO> ans = employeeDao.findMonthlyInfoUnion();
        return Response.buildSuccess(ans);
    }

    @GetMapping("/findYearlyInfoUnion")
    public Response findYearlyInfoUnion() {
        List<EmployeeInfoUnionYearlyPO> ans = employeeDao.findYearlyInfoUnion();
        return Response.buildSuccess(ans);
    }

    @GetMapping("/findCommissionInfoUnion")
    public Response findCommissionInfoUnion() {
        List<EmployeeInfoUnionCommissionPO> ans = employeeDao.findCommissionInfoUnion();
        return Response.buildSuccess(ans);
    }

}
