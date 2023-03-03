package com.nju.edu.erp.model.po.salary;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BaseAndCommissionPO {
    //提成制PO
    /**
     * id
     */
    private Integer id;
    /**
     * 基本工资
     */
    private Integer baseSalary;
    /**
     * 提成比率
     */
    private Integer commissionRate;
    /**
     * 当月实际销售额 = 月销售额 - 月退货额
     */
    private Integer actualSales;
}
