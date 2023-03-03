package com.nju.edu.erp.PromotionTest;

import com.nju.edu.erp.dao.PromotionDao;
import com.nju.edu.erp.model.po.promotion.TotalAmountPromotionPO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;

/**
 * @author zym
 * @date 2022/07/02 09:02
 **/
@SpringBootTest
public class PromotionTests {
    @Autowired
    PromotionDao promotionDao;


    @Test
    void findOneByLevel() {
        System.out.println(promotionDao.findOneByLevel(1));
    }

    @Test
    void findLevelAll() {
        System.out.println(promotionDao.findLevelAll());
    }


    @Test
    @Transactional
    @Rollback
    void addTotalAmountPromotion() {
        TotalAmountPromotionPO totalAmountPromotionPO = new TotalAmountPromotionPO(8000, "iphone6", 800, new Date(), new Date());
        promotionDao.addTotalAmountPromotion(totalAmountPromotionPO);
        System.out.println(promotionDao.findAmountAll());
    }


    @Test
    @Transactional
    @Rollback
    void updateTotalAmountPromotionByAmount() {
        TotalAmountPromotionPO totalAmountPromotionPO = new TotalAmountPromotionPO(8000, "iphone12", 800, new Date(), new Date());
        promotionDao.addTotalAmountPromotion(totalAmountPromotionPO);
        promotionDao.updateTotalAmountPromotionByAmount(totalAmountPromotionPO);
        System.out.println(promotionDao.findAmountAll());
    }


    @Test
    @Transactional
    @Rollback
    void deleteTotalAmountPromotionById() {
        promotionDao.deleteTotalAmountPromotionById(10000);
        System.out.println(promotionDao.findAmountAll());
    }
}
