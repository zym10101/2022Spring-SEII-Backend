package com.nju.edu.erp.web.controller;

import com.nju.edu.erp.auth.Authorized;
import com.nju.edu.erp.enums.CustomerType;
import com.nju.edu.erp.enums.Role;
import com.nju.edu.erp.model.po.PayReceiveSheetIODetailPO;
import com.nju.edu.erp.model.po.WageSheetPO;
import com.nju.edu.erp.service.CustomerService;
import com.nju.edu.erp.service.FinancialService;
import com.nju.edu.erp.web.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author zym
 * @date 2022/06/29 23:22
 **/
@RestController
@RequestMapping(path = "/financial")
public class FinancialController {
    private final FinancialService financialService;

    @Autowired
    public FinancialController(FinancialService financialService) {
        this.financialService = financialService;
    }

    @GetMapping("/sheetContent/time")
    @Authorized(roles = {Role.ADMIN, Role.GM, Role.FINANCIAL_STAFF})
    public Response getPayReceiveSheet(@RequestParam String beginDateStr, @RequestParam String endDateStr) {
        List<PayReceiveSheetIODetailPO> payReceiveSheet = financialService.getPayReceiveSheet(beginDateStr, endDateStr);
        return Response.buildSuccess(payReceiveSheet);
    }

    @GetMapping("/wageSheetContent/time")
    @Authorized(roles = {Role.ADMIN, Role.GM, Role.FINANCIAL_STAFF})
    public Response getWageSheet(@RequestParam String beginDateStr, @RequestParam String endDateStr) {
        List<WageSheetPO> wageSheet = financialService.getWageSheet(beginDateStr, endDateStr);
        return Response.buildSuccess(wageSheet);
    }
}
