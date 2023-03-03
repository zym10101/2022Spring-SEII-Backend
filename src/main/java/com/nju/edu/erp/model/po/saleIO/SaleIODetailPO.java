package com.nju.edu.erp.model.po.saleIO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SaleIODetailPO {
    /**
     * 销售操作类型，销售/销售退货
     */
    private String type;

    /**
     * 销售/销售退货单id
     */
    private String sheetId;

    /**
     * 商品名
     */
    private String productName;

    /**
     * 商品数量
     */
    private Integer quantity;

    /**
     * 商品单价
     */
    private BigDecimal unitPrice;

    /**
     * 商品总价
     */
    private BigDecimal totalPrice;

    /**
     * 销售/销售退货创建时间
     */
    private Date createTime;

}
