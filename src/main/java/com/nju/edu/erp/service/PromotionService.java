package com.nju.edu.erp.service;

import com.nju.edu.erp.model.po.promotion.TotalAmountPromotionPO;
import com.nju.edu.erp.model.po.promotion.UserLevelPromotionPO;

import java.util.List;

/**
 * @author zym
 * @date 2022/06/28 19:13
 **/
public interface PromotionService {
    /**
     * 查找所有用户等级促销策略
     */
    List<UserLevelPromotionPO> findLevelAll();

    /**
     * 根据用户等级查找促销策略
     */
    UserLevelPromotionPO findOneByLevel(Integer level);

    /**
     * 更新用户等级促销策略
     */
    void updateUserLevelPromotionByLevel(UserLevelPromotionPO userLevelPromotionPO);

    /**
     * 查找所有总价促销策略
     */
    List<TotalAmountPromotionPO> findAmountAll();

    /**
     * 根据总价查找促销策略
     */
    TotalAmountPromotionPO findOneByAmount(Integer amount);

    /**
     * 查找所有总价促销策略的最低总价取值
     */
    List<Integer> findAmountNumAll();

    /**
     * 更新总价促销策略
     */
    void updateTotalAmountPromotionByAmount(TotalAmountPromotionPO totalAmountPromotionPO);

    /**
     * 根据id删除总价促销策略
     */
    void deleteTotalAmountPromotionById(Integer id);

    /**
     * 增加一条总价促销策略
     */
    void addTotalAmountPromotion(TotalAmountPromotionPO totalAmountPromotionPO);

}
