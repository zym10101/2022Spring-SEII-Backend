package com.nju.edu.erp.model.po;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AttendancePO {
    /**
     * 打卡记录编号
     */
    private Integer attendanceNo;
    /**
     * 员工编号
     * */
    private Integer employeeId;
    /**
     * 打卡日期
     */
    private Date attendanceDate;
}
