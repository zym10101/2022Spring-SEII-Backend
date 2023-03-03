package com.nju.edu.erp.web.controller;


import com.nju.edu.erp.auth.Authorized;
import com.nju.edu.erp.enums.Role;
import com.nju.edu.erp.enums.sheetState.PaySheetState;
import com.nju.edu.erp.model.vo.user.UserVO;
import com.nju.edu.erp.model.vo.pay.PaySheetVO;
import com.nju.edu.erp.service.PayService;
import com.nju.edu.erp.web.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/pay")
public class PayController {

    private final PayService payService;

    @Autowired
    public PayController(PayService payService) {
        this.payService = payService;
    }

    /**
     * 制定付款单
     */
    @Authorized(roles = {Role.FINANCIAL_STAFF, Role.GM, Role.ADMIN})
    @PostMapping(value = "/sheet-make")
    public Response makePaySheet(UserVO userVO, @RequestBody PaySheetVO paySheetVO) {
        payService.makePaySheet(userVO, paySheetVO);
        return Response.buildSuccess();
    }

    /**
     * 根据状态查看付款单
     */
    @GetMapping(value = "/sheet-show")
    public Response showSheetByState(@RequestParam(value = "state", required = false) PaySheetState state) {
        return Response.buildSuccess(payService.getPaySheetByState(state));
    }

    /**
     * 总经理审批
     *
     * @param paySheetId 付款单id
     * @param state       修改后的状态("审批失败"/"审批完成")
     */
    @Authorized(roles = {Role.GM, Role.ADMIN})
    @GetMapping(value = "/approval")
    public Response approval(@RequestParam("paySheetId") String paySheetId,
                             @RequestParam("state") PaySheetState state) {
        if (state.equals(PaySheetState.FAILURE) || state.equals(PaySheetState.SUCCESS)) {
            payService.approval(paySheetId, state);
            return Response.buildSuccess();
        } else {
            return Response.buildFailed("000000", "操作失败"); // code可能得改一个其他的
        }
    }
}

