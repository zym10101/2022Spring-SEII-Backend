package com.nju.edu.erp.service.Impl;

import com.nju.edu.erp.dao.AccountDao;
import com.nju.edu.erp.exception.MyServiceException;
import com.nju.edu.erp.model.po.AccountPO;
import com.nju.edu.erp.model.vo.AccountVO;
import com.nju.edu.erp.service.AccountService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountDao accountDao;

    @Autowired
    public AccountServiceImpl(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    @Override
    public void updateAccount(String name, Integer id) {
        accountDao.updateName(name, id);
    }

    @Override
    public List<AccountPO> getAllAccount() {
        return accountDao.findAll();
    }

    @Override
    public AccountPO findAccountByName(String accountName) {
        return accountDao.findByName(accountName);
    }

    @Override
    public void registerAccount(AccountVO accountVO) {
        AccountPO accountPO = accountDao.findByName(accountVO.getAccountName());
        if (accountPO != null) {
            throw new MyServiceException("A0001", "用户名已存在");
        }
        AccountPO accountPOSave = new AccountPO();
        BeanUtils.copyProperties(accountVO, accountPOSave);
        accountDao.createAccount(accountPOSave);
    }

    @Override
    public void dropAccount(String accountName) {
        AccountPO accountPO = accountDao.findByName(accountName);
        if (accountPO == null) {
            throw new MyServiceException("A0001", "该用户不存在");
        }

        accountDao.deleteAccount(accountName);
    }
}
