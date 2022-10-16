package com.nju.edu.erp.model.po;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class YearlyPayPO { //年薪制
    /**
     * id
     */
    private Integer id;
    /**
     * 年薪
     */
    private Integer yearlySalary;
}
