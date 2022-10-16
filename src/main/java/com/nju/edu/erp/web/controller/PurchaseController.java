package com.nju.edu.erp.web.controller;

import com.nju.edu.erp.auth.Authorized;
import com.nju.edu.erp.enums.Role;
import com.nju.edu.erp.enums.sheetState.PurchaseSheetState;
import com.nju.edu.erp.model.po.PurchaseSheetIODetailPO;
import com.nju.edu.erp.model.vo.UserVO;
import com.nju.edu.erp.model.vo.purchase.PurchaseSheetVO;
import com.nju.edu.erp.service.PurchaseService;
import com.nju.edu.erp.web.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping(path = "/purchase")
public class PurchaseController {

    private final PurchaseService purchaseService;

    @Autowired
    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    /**
     * 销售人员制定进货单
     */
    @Authorized(roles = {Role.SALE_STAFF, Role.SALE_MANAGER, Role.GM, Role.ADMIN})
    @PostMapping(value = "/sheet-make")
    public Response makePurchaseOrder(UserVO userVO, @RequestBody PurchaseSheetVO purchaseSheetVO) {
        purchaseService.makePurchaseSheet(userVO, purchaseSheetVO);
        return Response.buildSuccess();
    }

    /**
     * 销售经理审批
     *
     * @param purchaseSheetId 进货单id
     * @param state           修改后的状态("审批失败"/"待二级审批")
     */
    @GetMapping(value = "/first-approval")
    @Authorized(roles = {Role.SALE_MANAGER, Role.GM, Role.ADMIN})
    public Response firstApproval(@RequestParam("purchaseSheetId") String purchaseSheetId,
                                  @RequestParam("state") PurchaseSheetState state) {
        if (state.equals(PurchaseSheetState.FAILURE) || state.equals(PurchaseSheetState.PENDING_LEVEL_2)) {
            purchaseService.approval(purchaseSheetId, state);
            return Response.buildSuccess();
        } else {
            return Response.buildFailed("000000", "操作失败"); // code可能得改一个其他的
        }
    }

    /**
     * 总经理审批
     *
     * @param purchaseSheetId 进货单id
     * @param state           修改后的状态("审批失败"/"审批完成")
     */
    @Authorized(roles = {Role.GM, Role.ADMIN})
    @GetMapping(value = "/second-approval")
    public Response secondApproval(@RequestParam("purchaseSheetId") String purchaseSheetId,
                                   @RequestParam("state") PurchaseSheetState state) {
        if (state.equals(PurchaseSheetState.FAILURE) || state.equals(PurchaseSheetState.SUCCESS)) {
            purchaseService.approval(purchaseSheetId, state);
            return Response.buildSuccess();
        } else {
            return Response.buildFailed("000000", "操作失败"); // code可能得改一个其他的
        }
    }

    /**
     * 根据状态查看进货单
     */
    @GetMapping(value = "/sheet-show")
    public Response showSheetByState(@RequestParam(value = "state", required = false) PurchaseSheetState state) {
        return Response.buildSuccess(purchaseService.getPurchaseSheetByState(state));
    }


    /**
     * 根据进货单Id搜索进货单信息
     *
     * @param id 进货单Id
     * @return 进货单全部信息
     */
    @GetMapping(value = "/find-sheet")
    public Response findBySheetId(@RequestParam(value = "id") String id) {
        return Response.buildSuccess(purchaseService.getPurchaseSheetById(id));
    }

    /**
     * 进货单据查看：查询指定时间段内进货/退货单据
     *
     * @param beginDateStr 格式：“yyyy-MM-dd HH:mm:ss”，如“2022-05-12 11:38:30”
     * @param endDateStr   格式：“yyyy-MM-dd HH:mm:ss”，如“2022-05-12 11:38:30”
     * @return
     */
    @GetMapping("/sheetContent/time")
    @Authorized(roles = {Role.ADMIN, Role.FINANCIAL_STAFF, Role.GM})
    public Response getPurchaseSheetIODetailByTime(@RequestParam String beginDateStr, @RequestParam String endDateStr) throws ParseException {
        List<PurchaseSheetIODetailPO> ans = purchaseService.getPurchaseSheetIODetailByTime(beginDateStr, endDateStr);
        return Response.buildSuccess(ans);
    }

    /**
     * 获取一段时间里的进货总额
     *
     * @param beginDateStr 开始日期
     * @param endDateStr   结束日期
     * @return 进货总额
     */
    @GetMapping("/time")
    public Response getInputByTime(@RequestParam String beginDateStr, @RequestParam String endDateStr) throws ParseException {
        int inputByTime = purchaseService.getInputByTime(beginDateStr, endDateStr);
        return Response.buildSuccess(inputByTime);
    }

}
