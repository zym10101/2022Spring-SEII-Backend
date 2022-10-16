package com.nju.edu.erp.service.Impl;

import com.nju.edu.erp.dao.PromotionDao;
import com.nju.edu.erp.model.po.TotalAmountPromotionPO;
import com.nju.edu.erp.model.po.UserLevelPromotionPO;
import com.nju.edu.erp.service.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author zym
 * @date 2022/06/28 19:15
 **/
@Service
public class PromotionServiceImpl implements PromotionService {


    PromotionDao promotionDao;

    @Autowired
    public PromotionServiceImpl(PromotionDao promotionDao) {
        this.promotionDao = promotionDao;
    }

    @Override
    public List<UserLevelPromotionPO> findLevelAll() {
        return promotionDao.findLevelAll();
    }

    @Override
    public UserLevelPromotionPO findOneByLevel(Integer level) {
        return promotionDao.findOneByLevel(level);
    }

    @Override
    public List<TotalAmountPromotionPO> findAmountAll() {
        return promotionDao.findAmountAll();
    }

    @Override
    public TotalAmountPromotionPO findOneByAmount(Integer amount) {
        return promotionDao.findOneByAmount(amount);
    }

    @Override
    public List<Integer> findAmountNumAll() {
        return promotionDao.findAmountNumAll();
    }

    @Override
    public void updateUserLevelPromotionByLevel(UserLevelPromotionPO userLevelPromotionPO) {
        UserLevelPromotionPO oneByLevel = promotionDao.findOneByLevel(userLevelPromotionPO.getUserLevel());
        if (oneByLevel != null) {
            promotionDao.updateUserLevelPromotionByLevel(userLevelPromotionPO);
        } else {
            throw new RuntimeException("该用户等级不存在！");
        }
    }

    @Override
    public void updateTotalAmountPromotionByAmount(TotalAmountPromotionPO totalAmountPromotionPO) {
        TotalAmountPromotionPO oneByAmount = promotionDao.findOneByAmount(totalAmountPromotionPO.getTotalAmountMin());
        if (oneByAmount != null) {
            promotionDao.updateTotalAmountPromotionByAmount(totalAmountPromotionPO);
        } else {
            throw new RuntimeException("该总价不存在！");
        }
    }

    @Override
    public void deleteTotalAmountPromotionById(Integer id) {
        TotalAmountPromotionPO oneByAmount = promotionDao.findOneByAmount(id);
        if (oneByAmount != null) {
            promotionDao.deleteTotalAmountPromotionById(id);
        } else {
            throw new RuntimeException("该总价不存在！");
        }
    }

    @Override
    public void addTotalAmountPromotion(TotalAmountPromotionPO totalAmountPromotionPO) {
        TotalAmountPromotionPO oneByAmount = promotionDao.findOneByAmount(totalAmountPromotionPO.getTotalAmountMin());
        if (oneByAmount == null) {
            promotionDao.addTotalAmountPromotion(totalAmountPromotionPO);
        } else {
            throw new RuntimeException("该总价已存在！");
        }
    }
}
