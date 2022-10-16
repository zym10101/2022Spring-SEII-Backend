package com.nju.edu.erp.model.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author zym
 * @date 2022/07/05 10:27
 **/
@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeAttendanceIODetailPO {
    /**
     * 编号
     */
    private Integer id;
    /**
     * 姓名
     */
    private String name;
    /**
     * 性别
     */
    private String sex;
    /**
     * 出生日期
     */
    private Date birthday;
    /**
     * 电话号码
     */
    private String phoneNumber;
    /**
     * 工作岗位
     */
    private String position;
    /**
     * 打卡时间
     */
    private Date attendanceDate;
}
