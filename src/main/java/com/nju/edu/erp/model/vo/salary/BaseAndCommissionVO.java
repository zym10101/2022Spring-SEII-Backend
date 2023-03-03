package com.nju.edu.erp.model.vo.salary;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BaseAndCommissionVO {
    //提成制VO
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
     * 当月实际销售量 = 月销售量 - 月退货量
     */
    private Integer actualSales;
}
