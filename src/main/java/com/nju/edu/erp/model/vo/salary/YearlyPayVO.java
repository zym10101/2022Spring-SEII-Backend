package com.nju.edu.erp.model.vo.salary;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class YearlyPayVO {//年薪制
    /**
     * id
     */
    private Integer id;
    /**
     * 年薪
     */
    private Integer yearlySalary;
}
