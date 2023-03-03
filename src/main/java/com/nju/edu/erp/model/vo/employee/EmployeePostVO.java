package com.nju.edu.erp.model.vo.employee;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeePostVO {
    //员工个人银行账户
    /**
     * 编号
     * */
    private Integer id;
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
     * 应发月薪
     */
    private Integer salaryStrategyResult;
    /**
     * 年终奖
     */
    private Integer yearBonus;
}
