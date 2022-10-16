package com.nju.edu.erp.web.controller;

import com.nju.edu.erp.auth.Authorized;
import com.nju.edu.erp.enums.Role;
import com.nju.edu.erp.enums.sheetState.SaleReturnsSheetState;
import com.nju.edu.erp.model.vo.UserVO;
import com.nju.edu.erp.model.vo.saleReturns.SaleReturnsSheetVO;
import com.nju.edu.erp.service.SaleReturnsService;
import com.nju.edu.erp.web.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequestMapping(path = "/sale-returns")
public class SaleReturnsController {

    private final SaleReturnsService saleReturnsService;

    @Autowired
    public SaleReturnsController(SaleReturnsService saleReturnsService) {
        this.saleReturnsService = saleReturnsService;
    }

    /**
     * 销售人员制定销售退货单
     */
    @Authorized(roles = {Role.SALE_STAFF, Role.SALE_MANAGER, Role.GM, Role.ADMIN})
    @PostMapping(value = "/sheet-make")
    public Response makeSaleOrder(UserVO userVO, @RequestBody SaleReturnsSheetVO saleReturnsSheetVO) {
        saleReturnsService.makeSaleReturnsSheet(userVO, saleReturnsSheetVO);
        return Response.buildSuccess();
    }

    /**
     * 销售经理审批
     *
     * @param saleReturnsSheetId 销售退货单id
     * @param state              修改后的状态("审批失败"/"待二级审批")
     */
    @GetMapping(value = "/first-approval")
    @Authorized(roles = {Role.SALE_MANAGER, Role.GM, Role.ADMIN})
    public Response firstApproval(@RequestParam("saleReturnsSheetId") String saleReturnsSheetId,
                                  @RequestParam("state") SaleReturnsSheetState state) {
        if (state.equals(SaleReturnsSheetState.FAILURE) || state.equals(SaleReturnsSheetState.PENDING_LEVEL_2)) {
            saleReturnsService.approval(saleReturnsSheetId, state);
            return Response.buildSuccess();
        } else {
            return Response.buildFailed("000000", "操作失败"); // code可能得改一个其他的
        }
    }

    /**
     * 总经理审批
     *
     * @param saleReturnsSheetId 销售退货单id
     * @param state              修改后的状态("审批失败"/"审批完成")
     */
    @Authorized(roles = {Role.GM, Role.ADMIN})
    @GetMapping(value = "/second-approval")
    public Response secondApproval(@RequestParam("saleReturnsSheetId") String saleReturnsSheetId,
                                   @RequestParam("state") SaleReturnsSheetState state) {
        if (state.equals(SaleReturnsSheetState.FAILURE) || state.equals(SaleReturnsSheetState.SUCCESS)) {
            saleReturnsService.approval(saleReturnsSheetId, state);
            return Response.buildSuccess();
        } else {
            return Response.buildFailed("000000", "操作失败"); // code可能得改一个其他的
        }
    }

    /**
     * 根据状态查看销售退货单
     */
    @GetMapping(value = "/sheet-show")
    public Response showSheetByState(@RequestParam(value = "state", required = false) SaleReturnsSheetState state) {
        return Response.buildSuccess(saleReturnsService.getSaleReturnsSheetByState(state));
    }


    /**
     * 获取一段时间里的销售退货总额
     *
     * @param beginDateStr 开始日期
     * @param endDateStr   结束日期
     * @return 销售退货总额
     */
    @GetMapping("/outgo")
    @Authorized(roles = {Role.ADMIN, Role.FINANCIAL_STAFF, Role.GM})
    public Response getOutgoByTime(@RequestParam String beginDateStr, @RequestParam String endDateStr) {
        int outgoByTime = saleReturnsService.getOutgoByTime(beginDateStr, endDateStr);
        return Response.buildSuccess(outgoByTime);
    }

    /**
     * 获取一段时间里的销售退货成本
     *
     * @param beginDateStr 开始日期
     * @param endDateStr   结束日期
     * @return 销售退货成本
     */
    @GetMapping("/cost")
    public Response getReturnCostByTime(@RequestParam String beginDateStr, @RequestParam String endDateStr) throws ParseException {
        int returnCostByTime = saleReturnsService.getReturnCostByTime(beginDateStr, endDateStr);
        return Response.buildSuccess(returnCostByTime);
    }

}
