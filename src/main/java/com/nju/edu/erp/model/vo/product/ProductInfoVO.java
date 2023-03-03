package com.nju.edu.erp.model.vo.product;


import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductInfoVO {

    /**
     * 商品id
     */
    @Excel(name = "商品id", width = 30)
    private String id;

    /**
     * 商品名
     */
    @Excel(name = "商品名")
    private String name;

    /**
     * 分类ID
     */
    @Excel(name = "分类ID")
    private Integer categoryId;

    /**
     * 商品型号
     */
    @Excel(name = "商品型号")
    private String type;

    /**
     * 商品数量
     */
    @Excel(name = "商品数量")
    private Integer quantity;

    /**
     * 进价
     */
    @Excel(name = "进价")
    private BigDecimal purchasePrice;

    /**
     * 零售价
     */
    @Excel(name = "零售价")
    private BigDecimal retailPrice;

    /**
     * 最近进价
     */
    @Excel(name = "最近进价")
    private BigDecimal recentPp;

    /**
     * 最近售价
     */
    @Excel(name = "最近售价")
    private BigDecimal recentRp;
}
