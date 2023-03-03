package com.nju.edu.erp.model.po.employee;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author zym
 * @date 2022/07/01 17:22
 **/
@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeWageIODetailPO {
    /**
     * 编号
     * */
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
     * 前11个月工资总和
     */
    private BigDecimal total;
    /**
     * 年终奖
     */
    private Integer yearBonus;
}
