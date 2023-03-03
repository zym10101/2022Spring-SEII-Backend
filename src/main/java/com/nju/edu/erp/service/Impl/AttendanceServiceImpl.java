package com.nju.edu.erp.service.Impl;

import com.nju.edu.erp.dao.AttendanceDao;
import com.nju.edu.erp.dao.EmployeePostDao;
import com.nju.edu.erp.model.po.employee.AttendancePO;
import com.nju.edu.erp.model.po.employee.EmployeeAttendanceIODetailPO;
import com.nju.edu.erp.model.po.employee.EmployeePostPO;
import com.nju.edu.erp.model.vo.employee.AttendanceVO;
import com.nju.edu.erp.service.AttendanceService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

//实现VO到PO的转换
@Service
public class AttendanceServiceImpl implements AttendanceService {
    private final AttendanceDao attendanceDao;
    private final EmployeePostDao employeePostDao;

    @Autowired
    public AttendanceServiceImpl(AttendanceDao attendanceDao, EmployeePostDao employeePostDao) {
        this.attendanceDao = attendanceDao;
        this.employeePostDao = employeePostDao;
    }


    @Override
    public void clockIn(AttendanceVO attendanceVO) {
        AttendancePO attendancePO = attendanceDao.findAttendanceByNo(attendanceVO.getAttendanceNo());
        AttendancePO attendancePOSave = new AttendancePO();
        BeanUtils.copyProperties(attendanceVO, attendancePOSave);
        attendanceDao.addAttendanceSql(attendancePOSave);
    }

    @Override
    public List<AttendanceVO> findAttendanceRecord(Integer employeeId) {
        //参考ProductServiceImpl
        List<AttendancePO> attendancePOList = attendanceDao.findAttendanceById(employeeId);
        List<AttendanceVO> attendanceVOList = attendancePOList.stream().map(attendancePO -> {
            AttendanceVO attendanceVO = new AttendanceVO();
            BeanUtils.copyProperties(attendancePO, attendanceVO);
            return attendanceVO;
        }).collect(Collectors.toList());
        return attendanceVOList;
    }

    @Override
    public void updateBaseSalary(Integer employeeId) {
        EmployeePostPO employeePostPO = employeePostDao.findEmployeePostById(employeeId);
        if(employeePostPO.getSalaryCalculationStrategy().equals("月薪制")) {
            attendanceDao.updateBaseSalaryMonthlySql(employeeId);
        } else if(employeePostPO.getSalaryCalculationStrategy().equals("提成制")) {
            attendanceDao.updateBaseSalaryCommissionSql(employeeId);
        }
    }

    @Override
    public List<EmployeeAttendanceIODetailPO> getAttendanceByTime(String beginDateStr, String endDateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date beginTime = sdf.parse(beginDateStr);
            Date endTime = sdf.parse(endDateStr);
            if (beginTime.before(endTime)) {
                return attendanceDao.findAttendanceByTime(beginTime, endTime);
            } else {
                return null;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

}
