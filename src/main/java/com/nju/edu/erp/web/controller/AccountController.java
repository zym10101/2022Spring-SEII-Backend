package com.nju.edu.erp.web.controller;

import com.nju.edu.erp.auth.Authorized;
import com.nju.edu.erp.enums.Role;
import com.nju.edu.erp.model.vo.AccountVO;
import com.nju.edu.erp.service.AccountService;
import com.nju.edu.erp.web.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(path = "/account")
public class AccountController {
    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/register")
    @Authorized(roles = {Role.ADMIN, Role.GM, Role.FINANCIAL_STAFF})
    public Response accountRegister(@RequestBody AccountVO accountVO) {
        accountService.registerAccount(accountVO);
        return Response.buildSuccess();
    }

    @GetMapping("/drop")
    @Authorized(roles = {Role.ADMIN, Role.GM, Role.FINANCIAL_STAFF})
    public Response accountDrop(@RequestParam String accountName) {
        accountService.dropAccount(accountName);
        return Response.buildSuccess();
    }

    @GetMapping("/update")
    @Authorized(roles = {Role.ADMIN, Role.GM, Role.FINANCIAL_STAFF})
    public Response accountUpdate(@RequestParam String name, @RequestParam Integer id){
        accountService.updateAccount(name, id);
        return Response.buildSuccess();
    }

    @GetMapping("/queryAll")
    @Authorized(roles = {Role.ADMIN, Role.GM, Role.FINANCIAL_STAFF})
    public Response queryAll() {
        return Response.buildSuccess(accountService.getAllAccount());
    }

    @GetMapping("/queryByName")
    @Authorized(roles = {Role.ADMIN, Role.GM, Role.FINANCIAL_STAFF})
    public Response queryByName(@RequestParam String name) {
        return Response.buildSuccess(accountService.findAccountByName(name));
    }
}

