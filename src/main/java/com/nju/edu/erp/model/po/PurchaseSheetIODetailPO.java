package com.nju.edu.erp.model.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author zym
 * @date 2022/06/28 09:24
 **/

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseSheetIODetailPO {
    /**
     * 类型，进货/进货退货
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
