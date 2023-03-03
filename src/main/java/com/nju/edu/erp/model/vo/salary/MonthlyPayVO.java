package com.nju.edu.erp.model.vo.salary;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MonthlyPayVO {
    /**
     * id
     */
    private Integer id;
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
