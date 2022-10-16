package com.nju.edu.erp.model.vo;

import com.nju.edu.erp.enums.sheetState.WageSheetState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WageSheetVO {

    /**
     * 工资单单据编号（格式为：FKD-yyyyMMdd-xxxxx), 新建单据时前端传null
     */
    private String id;
    /**
     * 员工id
     */
    private Integer employeeId;
    /**
     * 员工姓名，传null
     */
    private String name;
    /**
     * 操作员
     */
    private String operator;
    /**
     * 单据状态, 新建单据时前端传null
     */
    private WageSheetState state;
    /**
     * 工资卡，null
     */
    private String bankAccount;
    /**
     * 应发
     */
    private BigDecimal totalAmount;
    /**
     * 扣除税款，null
     */
    private BigDecimal tax;
    /**
     * 实发，null
     */
    private BigDecimal finalAmount;
    /**
     * 是否计算年终奖
     */
    private boolean calBonus;
}

