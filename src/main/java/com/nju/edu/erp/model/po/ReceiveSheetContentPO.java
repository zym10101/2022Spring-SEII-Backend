package com.nju.edu.erp.model.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReceiveSheetContentPO {
    /**
     * 自增id
     */
    private Integer id;
    /**
     * 收款单id
     */
    private String receiveSheetId;
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