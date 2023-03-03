package com.nju.edu.erp.service;

import com.nju.edu.erp.model.po.employee.EmployeeAttendanceIODetailPO;
import com.nju.edu.erp.model.vo.employee.AttendanceVO;

import java.util.List;

public interface AttendanceService {
    //打卡
    void clockIn(AttendanceVO attendanceVO);

    //根据id查询打卡记录
    List<AttendanceVO> findAttendanceRecord(Integer employeeId);

    //根据考勤情况修改基本工资
    void updateBaseSalary(Integer employeeId);

    List<EmployeeAttendanceIODetailPO> getAttendanceByTime(String beginDateStr, String endDateStr);

}
