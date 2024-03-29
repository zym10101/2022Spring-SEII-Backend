package com.nju.edu.erp.model.vo.warehouse;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelEntity;
import com.nju.edu.erp.model.vo.product.ProductInfoVO;
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
public class WarehouseCountingVO {
    /**
     * 库存id
     */
    @Excel(name = "库存id")
    private Integer id;

    /**
     * 商品编号
     */
    @ExcelEntity(name = "商品")
    private ProductInfoVO product;

    /**
     * 数量
     */
    @Excel(name = "数量")
    private Integer quantity;

    /**
     * 进价
     */
    @Excel(name = "进价")
    private BigDecimal purchasePrice;

    /**
     * 批次
     */
    @Excel(name = "批次")
    private Integer batchId;

    /**
     * 出厂日期
     */
    @Excel(name = "出厂日期")
    private Date productionDate;
}
