package com.nju.edu.erp.model.po;

import com.nju.edu.erp.enums.sheetState.WarehouseBYDState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.poi.hpsf.Decimal;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author zym
 * @date 2022/06/28 12:46
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WarehouseBYDPO {
    /**
     * 报溢单单据编号
     */
    private String id;
    /**
     * 批次
     */
    private Integer batchId;
    /**
     * 操作员
     */
    private String operator;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 单据状态
     */
    private WarehouseBYDState state;
    /**
     * 商品ID
     */
    private char pid;
    /**
     * 商品报溢数量
     */
    private Integer byQuantity;
    /**
     * 单价
     */
    private BigDecimal purchasePrice;
    /**
     * 出厂日期
     */
    private Date productionDate;
    /**
     * 备注
     */
    private String remark;
}
