package com.nju.edu.erp.model.po.pay;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaySheetContentPO {
    /**
     * 自增id, 新建单据时前端传null
     */
    private Integer id;
    /**
     * 付款单id, 新建单据时前端传null
     */
    private String paySheetId;
    /**
     * 银行账户
     */
    private String bankAccount;
    /**
     * 转账金额
     */
    private BigDecimal amount;
    /**
     * 备注
     */
    private String remark;
}
