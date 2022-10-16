package com.nju.edu.erp.model.po;

import com.nju.edu.erp.enums.sheetState.PaySheetState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author zym
 * @date 2022/06/29 23:11
 **/

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PayReceiveSheetIODetailPO {
    /**
     * 单据类型
     */
    private String type;
    /**
     * 单据编号
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
     * 单据状态
     */
    private PaySheetState state;
    /**
     * 总额
     */
    private BigDecimal totalAmount;
    /**
     * 创建时间
     */
    private Date createTime;
}
