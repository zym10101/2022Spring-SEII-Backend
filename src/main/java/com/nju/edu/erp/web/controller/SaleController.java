package com.nju.edu.erp.web.controller;


import com.nju.edu.erp.auth.Authorized;
import com.nju.edu.erp.enums.Role;
import com.nju.edu.erp.enums.sheetState.SaleSheetState;
import com.nju.edu.erp.model.po.customer.CustomerPurchaseAmountPO;
import com.nju.edu.erp.model.po.saleIO.SaleIODetailPO;
import com.nju.edu.erp.model.po.saleIO.SaleSheetIODetailPO;
import com.nju.edu.erp.model.vo.sale.SaleSheetVO;
import com.nju.edu.erp.model.vo.user.UserVO;
import com.nju.edu.erp.service.SaleService;
import com.nju.edu.erp.web.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping(path = "/sale")
public class SaleController {

    private final SaleService saleService;

    @Autowired
    public SaleController(SaleService saleService) {
        this.saleService = saleService;
    }

    /**
     * 销售人员制定销售单
     */
    @Authorized(roles = {Role.SALE_STAFF, Role.SALE_MANAGER, Role.GM, Role.ADMIN})
    @PostMapping(value = "/sheet-make")
    public Response makePurchaseOrder(UserVO userVO, @RequestBody SaleSheetVO saleSheetVO) {
        saleService.makeSaleSheet(userVO, saleSheetVO);
        return Response.buildSuccess();
    }

    /**
     * 根据状态查看销售单
     */
    @GetMapping(value = "/sheet-show")
    public Response showSheetByState(@RequestParam(value = "state", required = false) SaleSheetState state) {
        return Response.buildSuccess(saleService.getSaleSheetByState(state));
    }

    /**
     * 销售经理审批
     *
     * @param saleSheetId 进货单id
     * @param state       修改后的状态("审批失败"/"待二级审批")
     */
    @GetMapping(value = "/first-approval")
    @Authorized(roles = {Role.SALE_MANAGER, Role.ADMIN})
    public Response firstApproval(@RequestParam("saleSheetId") String saleSheetId,
                                  @RequestParam("state") SaleSheetState state) {
        if (state.equals(SaleSheetState.FAILURE) || state.equals(SaleSheetState.PENDING_LEVEL_2)) {
            saleService.approval(saleSheetId, state);
            return Response.buildSuccess();
        } else {
            return Response.buildFailed("000000", "操作失败"); // code可能得改一个其他的
        }
    }

    /**
     * 总经理审批
     *
     * @param saleSheetId 进货单id
     * @param state       修改后的状态("审批失败"/"审批完成")
     */
    @Authorized(roles = {Role.GM, Role.ADMIN})
    @GetMapping(value = "/second-approval")
    public Response secondApproval(@RequestParam("saleSheetId") String saleSheetId,
                                   @RequestParam("state") SaleSheetState state) {
        if (state.equals(SaleSheetState.FAILURE) || state.equals(SaleSheetState.SUCCESS)) {
            saleService.approval(saleSheetId, state);
            return Response.buildSuccess();
        } else {
            return Response.buildFailed("000000", "操作失败"); // code可能得改一个其他的
        }
    }

    /**
     * 获取某个销售人员某段时间内消费总金额最大的客户(不考虑退货情况,销售单不需要审批通过,如果这样的客户有多个,仅保留一个)
     *
     * @param salesman     销售人员的名字
     * @param beginDateStr 开始时间字符串 格式：“yyyy-MM-dd HH:mm:ss”，如“2022-05-12 11:38:30”
     * @param endDateStr   结束时间字符串 格式：“yyyy-MM-dd HH:mm:ss”，如“2022-05-12 11:38:30”
     * @return
     */
    @GetMapping("/maxAmountCustomer")
    @Authorized(roles = {Role.SALE_MANAGER, Role.GM, Role.ADMIN})
    public Response getMaxAmountCustomerOfSalesmanByTime(@RequestParam String salesman, @RequestParam String beginDateStr, @RequestParam String endDateStr) {
        CustomerPurchaseAmountPO ans = saleService.getMaxAmountCustomerOfSalesmanByTime(salesman, beginDateStr, endDateStr);
        return Response.buildSuccess(ans);
    }

    /**
     * 销售单据查看：查询指定时间段内销售/销售退货单据
     *
     * @param beginDateStr 格式：“yyyy-MM-dd HH:mm:ss”，如“2022-05-12 11:38:30”
     * @param endDateStr   格式：“yyyy-MM-dd HH:mm:ss”，如“2022-05-12 11:38:30”
     * @return
     */
    @GetMapping("/sheetContent/time")
    @Authorized(roles = {Role.ADMIN, Role.FINANCIAL_STAFF, Role.GM})
    public Response getSaleSheetIODetailByTime(@RequestParam String beginDateStr, @RequestParam String endDateStr) throws ParseException {
        List<SaleSheetIODetailPO> ans = saleService.getSaleSheetIODetailByTime(beginDateStr, endDateStr);
        return Response.buildSuccess(ans);
    }

    /**
     * 根据销售单Id搜索销售单信息
     *
     * @param id 销售单Id
     * @return 销售单全部信息
     */
    @GetMapping(value = "/find-sheet")
    public Response findBySheetId(@RequestParam(value = "id") String id) {
        return Response.buildSuccess(saleService.getSaleSheetById(id));
    }

    /**
     * 获取一段时间里的销售总收入
     *
     * @param beginDateStr 开始日期
     * @param endDateStr   结束日期
     * @return 销售总收入
     * @throws ParseException
     */
    @GetMapping("/income")
    @Authorized(roles = {Role.ADMIN, Role.FINANCIAL_STAFF, Role.GM})
    public Response getIncomeByTime(@RequestParam String beginDateStr, @RequestParam String endDateStr) throws ParseException {
        int incomeByTime = saleService.getIncomeByTime(beginDateStr, endDateStr);
        return Response.buildSuccess(incomeByTime);
    }

    /**
     * 获取一段时间里的销售总折让
     *
     * @param beginDateStr 开始日期
     * @param endDateStr   结束日期
     * @return 销售总折让
     * @throws ParseException
     */
    @GetMapping("/discount")
    @Authorized(roles = {Role.ADMIN, Role.FINANCIAL_STAFF, Role.GM})
    public Response getDiscountByTime(@RequestParam String beginDateStr, @RequestParam String endDateStr) throws ParseException {
        int discountByTime = saleService.getDiscountByTime(beginDateStr, endDateStr);
        return Response.buildSuccess(discountByTime);
    }

    /**
     * 获取一段时间里的代金券总额
     *
     * @param beginDateStr 开始日期
     * @param endDateStr   结束日期
     * @return 代金券总额
     * @throws ParseException
     */
    @GetMapping("/voucher")
    @Authorized(roles = {Role.ADMIN, Role.FINANCIAL_STAFF, Role.GM})
    public Response getVoucherByTime(@RequestParam String beginDateStr, @RequestParam String endDateStr) throws ParseException {
        int voucherByTime = saleService.getVoucherByTime(beginDateStr, endDateStr);
        return Response.buildSuccess(voucherByTime);
    }

    /**
     * 获取一段时间里的销售成本
     *
     * @param beginDateStr 开始日期
     * @param endDateStr   结束日期
     * @return 销售成本
     */
    @GetMapping("/cost")
    public Response getCostByTime(@RequestParam String beginDateStr, @RequestParam String endDateStr) throws ParseException {
        int costByTime = saleService.getCostByTime(beginDateStr, endDateStr);
        return Response.buildSuccess(costByTime);
    }

    /**
     * 查看销售明细表
     *
     * @param beginDateStr 格式：“yyyy-MM-dd HH:mm:ss”，如“2022-05-12 11:38:30”
     * @param endDateStr   格式：“yyyy-MM-dd HH:mm:ss”，如“2022-05-12 11:38:30”
     * @return
     */
    @GetMapping("/saleDetail")
    @Authorized(roles = {Role.ADMIN, Role.FINANCIAL_STAFF, Role.GM})
    public Response getSaleIODetailByTime(@RequestParam String beginDateStr, @RequestParam String endDateStr, @RequestParam String productName, @RequestParam String customer, @RequestParam String salesman) throws ParseException {
        List<SaleIODetailPO> ans = saleService.getSaleIODetailByTime(beginDateStr, endDateStr, productName, customer, salesman);
        return Response.buildSuccess(ans);
    }
}
