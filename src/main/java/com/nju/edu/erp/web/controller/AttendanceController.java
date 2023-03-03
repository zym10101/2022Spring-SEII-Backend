package com.nju.edu.erp.web.controller;

import com.nju.edu.erp.model.po.employee.EmployeeAttendanceIODetailPO;
import com.nju.edu.erp.model.vo.employee.AttendanceVO;
import com.nju.edu.erp.service.AttendanceService;
import com.nju.edu.erp.web.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/attendance")
public class AttendanceController {
    private final AttendanceService attendanceService;

    @Autowired
    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    @PostMapping("/add")
    public Response add(@RequestBody AttendanceVO attendanceVO) {
        attendanceService.clockIn(attendanceVO);
        return Response.buildSuccess();
    }

    @GetMapping("/findById")
    public Response findById(@RequestParam Integer employeeId) {
        return Response.buildSuccess(attendanceService.findAttendanceRecord(employeeId));
    }

    @GetMapping("/findByTime")
    public Response findByTime(@RequestParam String beginDateStr, @RequestParam String endDateStr) {
        List<EmployeeAttendanceIODetailPO> attendanceByTime = attendanceService.getAttendanceByTime(beginDateStr, endDateStr);
        return Response.buildSuccess(attendanceByTime);
    }

    @PostMapping("/update")
    public Response updateBaseSalary(@RequestParam Integer employeeId) {
        attendanceService.updateBaseSalary(employeeId);
        return Response.buildSuccess();
    }
}
