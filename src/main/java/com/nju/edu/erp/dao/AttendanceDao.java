package com.nju.edu.erp.dao;

import com.nju.edu.erp.model.po.AttendancePO;
import com.nju.edu.erp.model.po.EmployeeAttendanceIODetailPO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
@Mapper

public interface AttendanceDao {
    //操作数据库，添加考勤记录
    int addAttendanceSql(AttendancePO attendancePO);

    //操作数据库，获取指定员工的考勤记录
    List<AttendancePO> findAttendanceById(Integer employeeId);

    //操作数据库，获取指定编号的考勤记录
    AttendancePO findAttendanceByNo(Integer attendanceNo);

    //根据考勤情况修改月薪制基本工资
    void updateBaseSalaryMonthlySql(Integer employeeId);

    //根据考勤情况修改提成制基本工资
    void updateBaseSalaryCommissionSql(Integer employeeId);

    //获取指定时间段内全部员工的考勤记录
    List<EmployeeAttendanceIODetailPO> findAttendanceByTime(Date beginTime, Date endTime);
}


