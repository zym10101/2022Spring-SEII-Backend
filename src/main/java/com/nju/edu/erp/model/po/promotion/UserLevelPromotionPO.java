package com.nju.edu.erp.model.po.promotion;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author zym
 * @date 2022/06/28 18:52
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserLevelPromotionPO {
    /**
     * 用户等级
     */
    private Integer userLevel;
    /**
     * 赠品
     */
    private String giftName;
    /**
     * 折让
     */
    private BigDecimal discount;
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
