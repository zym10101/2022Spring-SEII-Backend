package com.nju.edu.erp.model.po.employee;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeInfoUnionMonthlyPO {
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
     * 基本工资
     */
    private Integer baseSalary;
    /**
     * 岗位工资,senior在基本工资的基础上再增加一部分固定额度
     */
    private Integer postSalary;
    /**
     * 岗位级别，primary/senior
     */
    private String postRank;
}
