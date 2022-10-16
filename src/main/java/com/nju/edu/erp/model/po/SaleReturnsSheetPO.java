package com.nju.edu.erp.model.po;

import com.nju.edu.erp.enums.sheetState.SaleReturnsSheetState;
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
public class SaleReturnsSheetPO {
    /**
     * 销售退货单单据编号（格式为：JHTHD-yyyyMMdd-xxxxx
     */
    private String id;
    /**
     * 关联的销售单id
     */
    private String saleSheetId;
    /**
     * 操作员
     */
    private String operator;
    /**
     * 备注
     */
    private String remark;
    /**
     * 折让前总额
     */
    private BigDecimal rawTotalAmount;
    /**
     * 折让
     */
    private BigDecimal discount;
    /**
     * 使用代金券总额
     */
    private BigDecimal voucherAmount;
    /**
     * 折让后总额
     */
    private BigDecimal totalAmount;
    /**
     * 单据状态
     */
    private SaleReturnsSheetState state;
    /**
     * 创建时间
     */
    private Date createTime;
}

