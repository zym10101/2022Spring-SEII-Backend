package com.nju.edu.erp.model.po.receive;

import com.nju.edu.erp.enums.sheetState.ReceiveSheetState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReceiveSheetPO {
    /**
     * 收款单单据编号（格式为：SKD-yyyyMMdd-xxxxx), 新建单据时前端传null
     */
    private String id;
    /**
     * 客户id
     */
    private Integer customer;
    /**
     * 操作员
     */
    private String operator;
    /**
     * 单据状态, 新建单据时前端传null
     */
    private ReceiveSheetState state;
    /**
     * 总额汇总, 新建单据时前端传null
     */
    private BigDecimal totalAmount;
    /**
     * 创建时间
     */
    private Date createTime;
}
