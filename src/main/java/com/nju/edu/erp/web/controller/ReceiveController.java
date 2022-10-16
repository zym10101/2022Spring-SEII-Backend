package com.nju.edu.erp.web.controller;


import com.nju.edu.erp.auth.Authorized;
import com.nju.edu.erp.enums.Role;
import com.nju.edu.erp.enums.sheetState.ReceiveSheetState;
import com.nju.edu.erp.model.vo.UserVO;
import com.nju.edu.erp.model.vo.receive.ReceiveSheetVO;
import com.nju.edu.erp.service.ReceiveService;
import com.nju.edu.erp.web.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping(path = "/receive")
public class ReceiveController {

    private final ReceiveService receiveService;

    @Autowired
    public ReceiveController(ReceiveService receiveService) {
        this.receiveService = receiveService;
    }

    /**
     * 制定收款单
     */
    @Authorized(roles = {Role.FINANCIAL_STAFF, Role.GM, Role.ADMIN})
    @PostMapping(value = "/sheet-make")
    public Response makeReceiveSheet(UserVO userVO, @RequestBody ReceiveSheetVO receiveSheetVO) {
        receiveService.makeReceiveSheet(userVO, receiveSheetVO);
        return Response.buildSuccess();
    }

    /**
     * 根据状态查看收款单
     */
    @GetMapping(value = "/sheet-show")
    public Response showSheetByState(@RequestParam(value = "state", required = false) ReceiveSheetState state) {
        return Response.buildSuccess(receiveService.getReceiveSheetByState(state));
    }

    /**
     * 总经理审批
     *
     * @param receiveSheetId 收款单id
     * @param state       修改后的状态("审批失败"/"审批完成")
     */
    @Authorized(roles = {Role.GM, Role.ADMIN})
    @GetMapping(value = "/approval")
    public Response approval(@RequestParam("receiveSheetId") String receiveSheetId,
                                   @RequestParam("state") ReceiveSheetState state) {
        if (state.equals(ReceiveSheetState.FAILURE) || state.equals(ReceiveSheetState.SUCCESS)) {
            receiveService.approval(receiveSheetId, state);
            return Response.buildSuccess();
        } else {
            return Response.buildFailed("000000", "操作失败"); // code可能得改一个其他的
        }
    }
}
