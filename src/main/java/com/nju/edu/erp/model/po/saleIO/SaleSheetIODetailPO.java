package com.nju.edu.erp.model.po.saleIO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author zym
 * @date 2022/06/27 13:46
 **/
@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SaleSheetIODetailPO {
    /**
     * 类型，销售或销售退货
     */
    private String type;
    /**
     * 单据ID
     */
    private String sheetId;
    /**
     * 商品名
     */
    private String productName;
    /**
     * 数量
     */
    private Integer quantity;
    /**
     * 单价
     */
    private BigDecimal unitPrice;
    /**
     * 总价
     */
    private BigDecimal totalPrice;
    /**
     * 创建时间
     */
    private Date createTime;
}
