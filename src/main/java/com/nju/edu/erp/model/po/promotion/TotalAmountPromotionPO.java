package com.nju.edu.erp.model.po.promotion;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author zym
 * @date 2022/06/28 18:58
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TotalAmountPromotionPO {
    /**
     * 达到促销条件的最低总价
     */
    private Integer totalAmountMin;
    /**
     * 赠品
     */
    private String giftName;
    /**
     * 代金券
     */
    private Integer voucher;
    /**
     * 起始日期
     */
    private Date beginTime;
    /**
     * 结束日期
     */
    private Date endTime;
}
