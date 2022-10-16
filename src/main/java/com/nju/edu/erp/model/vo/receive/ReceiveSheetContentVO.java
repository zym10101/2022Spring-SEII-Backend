package com.nju.edu.erp.model.vo.receive;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReceiveSheetContentVO {
    /**
     * 收款单id, 新建单据时前端传null
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
