package com.nju.edu.erp.web.controller;


import com.nju.edu.erp.auth.Authorized;
import com.nju.edu.erp.enums.Role;
import com.nju.edu.erp.enums.sheetState.SaleSheetState;
import com.nju.edu.erp.enums.sheetState.WageSheetState;
import com.nju.edu.erp.model.vo.UserVO;
import com.nju.edu.erp.model.vo.WageSheetVO;
import com.nju.edu.erp.service.WageService;
import com.nju.edu.erp.web.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.text.ParseException;

@RestController
@RequestMapping(path = "/wage")
public class WageController {

    private final WageService wageService;

    @Autowired
    public WageController(WageService wageService) {
        this.wageService = wageService;
    }

    /**
     * 制定工资单
     */
    @Authorized(roles = {Role.FINANCIAL_STAFF, Role.GM, Role.ADMIN})
    @PostMapping(value = "/sheet-make")
    public Response makeWageSheet(UserVO userVO, @RequestBody WageSheetVO wageSheetVO) {
        wageService.makeWageSheet(userVO, wageSheetVO);
        return Response.buildSuccess();
    }

    /**
     * 根据状态查看工资单
     */
    @GetMapping(value = "/sheet-show")
    public Response showSheetByState(@RequestParam(value = "state", required = false) WageSheetState state) {
        return Response.buildSuccess(wageService.getWageSheetByState(state));
    }

    /**
     * 人力资源人员审批
     *
     * @param wageSheetId 工资单id
     * @param state       修改后的状态("审批失败"/"待二级审批")
     */
    @GetMapping(value = "/first-approval")
    @Authorized(roles = {Role.HR, Role.ADMIN})
    public Response firstApproval(@RequestParam("wageSheetId") String wageSheetId,
                                  @RequestParam("state") WageSheetState state) {
        if (state.equals(WageSheetState.FAILURE) || state.equals(WageSheetState.PENDING_LEVEL_2)) {
            wageService.approval(wageSheetId, state);
            return Response.buildSuccess();
        } else {
            return Response.buildFailed("000000", "操作失败"); // code可能得改一个其他的
        }
    }

    /**
     * 总经理审批
     *
     * @param wageSheetId 工资单id
     * @param state       修改后的状态("审批失败"/"审批完成")
     */
    @Authorized(roles = {Role.GM, Role.ADMIN})
    @GetMapping(value = "/second-approval")
    public Response approval(@RequestParam("wageSheetId") String wageSheetId,
                             @RequestParam("state") WageSheetState state) {
        if (state.equals(WageSheetState.FAILURE) || state.equals(WageSheetState.SUCCESS)) {
            wageService.approval(wageSheetId, state);
            return Response.buildSuccess();
        } else {
            return Response.buildFailed("000000", "操作失败"); // code可能得改一个其他的
        }
    }

    /**
     * 获取一段时间里的员工工资总额
     *
     * @param beginDateStr 开始日期
     * @param endDateStr   结束日期
     * @return 工资总额
     */
    @GetMapping("/salary/time")
    public Response getSalaryByTime(@RequestParam String beginDateStr, @RequestParam String endDateStr) throws ParseException {
        int salaryByTime = wageService.getSalaryByTime(beginDateStr, endDateStr);
        return Response.buildSuccess(salaryByTime);
    }

}

