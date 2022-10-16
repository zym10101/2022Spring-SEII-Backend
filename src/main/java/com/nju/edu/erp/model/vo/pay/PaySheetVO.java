package com.nju.edu.erp.model.vo.pay;

import com.nju.edu.erp.enums.sheetState.PaySheetState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaySheetVO {

    /**
     * 付款单单据编号（格式为：FKD-yyyyMMdd-xxxxx), 新建单据时前端传null
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
    private PaySheetState state;
    /**
     * 总额, 新建单据时前端传null
     */
    private BigDecimal totalAmount;
    /**
     * 付款单具体内容
     */
    List<PaySheetContentVO> paySheetContent;
}
