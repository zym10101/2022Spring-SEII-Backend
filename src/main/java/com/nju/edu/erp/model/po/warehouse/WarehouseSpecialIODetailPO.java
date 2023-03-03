package com.nju.edu.erp.model.po.warehouse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author zym
 * @date 2022/06/28 10:16
 **/
@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WarehouseSpecialIODetailPO {
    /**
     * 类型，报溢单/报损单/赠送单
     */
    private String type;

    /**
     * 单据id
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
     * 创建时间
     */
    private Date createTime;
}
