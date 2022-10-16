package com.nju.edu.erp.model.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeInfoUnionYearlyPO {
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
     * 银行账号
     */
    private String bankAccount;
    /**
     * 工作岗位
     */
    private String position;
    /**
     * 薪资计算方式
     */
    private String salaryCalculationStrategy;
    /**
     * 年薪
     */
    private Integer yearlySalary;
}
