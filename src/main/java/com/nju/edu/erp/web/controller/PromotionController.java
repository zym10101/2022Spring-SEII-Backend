package com.nju.edu.erp.web.controller;

import com.nju.edu.erp.auth.Authorized;
import com.nju.edu.erp.enums.Role;
import com.nju.edu.erp.model.po.TotalAmountPromotionPO;
import com.nju.edu.erp.model.po.UserLevelPromotionPO;
import com.nju.edu.erp.service.PromotionService;
import com.nju.edu.erp.web.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author zym
 * @date 2022/06/28 20:23
 **/
@RestController
@RequestMapping(path = "/promotion")
public class PromotionController {
    private final PromotionService promotionService;

    @Autowired
    public PromotionController(PromotionService promotionService) {
        this.promotionService = promotionService;
    }

    @Authorized(roles = {Role.GM, Role.ADMIN})
    @GetMapping(value = "/get-all-user-level-promotion")
    public Response GetAllUserLevelPromotion() {
        List<UserLevelPromotionPO> levelAll = promotionService.findLevelAll();
        return Response.buildSuccess(levelAll);
    }

    @Authorized(roles = {Role.GM, Role.ADMIN})
    @GetMapping(value = "/user-level-promotion")
    public Response UserLevelPromotion(@RequestParam Integer userLevel, @RequestParam String giftName, @RequestParam BigDecimal discount, @RequestParam Integer voucher, @RequestParam String beginTime, @RequestParam String endTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date beginTimeDate = null, endTimeDate = null;
        try {
            beginTimeDate = sdf.parse(beginTime);
            endTimeDate = sdf.parse(endTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        UserLevelPromotionPO userLevelPromotionPO = new UserLevelPromotionPO(userLevel, giftName, discount, voucher, beginTimeDate, endTimeDate);
        promotionService.updateUserLevelPromotionByLevel(userLevelPromotionPO);
        return Response.buildSuccess();
    }

    @Authorized(roles = {Role.GM, Role.ADMIN})
    @GetMapping(value = "/get-all-total-amount-promotion")
    public Response GetAllTotalAmountPromotion() {
        List<TotalAmountPromotionPO> amountAll = promotionService.findAmountAll();
        return Response.buildSuccess(amountAll);
    }

    @Authorized(roles = {Role.GM, Role.ADMIN})
    @GetMapping(value = "/total-amount-promotion")
    public Response TotalAmountPromotion(@RequestParam Integer totalAmountMin, @RequestParam String giftName, @RequestParam Integer voucher, @RequestParam String beginTime, @RequestParam String endTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date beginTimeDate = null, endTimeDate = null;
        try {
            beginTimeDate = sdf.parse(beginTime);
            endTimeDate = sdf.parse(endTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        TotalAmountPromotionPO totalAmountPromotionPO = new TotalAmountPromotionPO(totalAmountMin, giftName, voucher, beginTimeDate, endTimeDate);
        promotionService.updateTotalAmountPromotionByAmount(totalAmountPromotionPO);
        return Response.buildSuccess();
    }

    @Authorized(roles = {Role.GM, Role.ADMIN})
    @GetMapping(value = "/add-total-amount-promotion")
    public Response addTotalAmountPromotion(@RequestParam Integer totalAmountMin, @RequestParam String giftName, @RequestParam Integer voucher, @RequestParam String beginTime, @RequestParam String endTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date beginTimeDate = null, endTimeDate = null;
        try {
            beginTimeDate = sdf.parse(beginTime);
            endTimeDate = sdf.parse(endTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        TotalAmountPromotionPO totalAmountPromotionPO = new TotalAmountPromotionPO(totalAmountMin, giftName, voucher, beginTimeDate, endTimeDate);
        promotionService.addTotalAmountPromotion(totalAmountPromotionPO);
        return Response.buildSuccess();
    }

    @Authorized(roles = {Role.GM, Role.ADMIN})
    @DeleteMapping("/delete-total-amount-promotion/{id}")
    public Response deleteTotalAmountPromotionById(@PathVariable Integer id) {
        promotionService.deleteTotalAmountPromotionById(id);
        return Response.buildSuccess();
    }
}

