package com.nju.edu.erp.service;

import com.nju.edu.erp.model.po.employee.AccountPO;
import com.nju.edu.erp.model.vo.employee.AccountVO;

import java.util.List;

public interface AccountService {
    /**
     * 根据id更新账户信息
     */
    void updateAccount(String name, Integer id);

    /**
     * @return 账户列表
     */
    List<AccountPO> getAllAccount();


    AccountPO findAccountByName(String accountName);

    /**
     * 添加账户
     */
    void registerAccount(AccountVO accountVO);

    void dropAccount(String accountName);
}
