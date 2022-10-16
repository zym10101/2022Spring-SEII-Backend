package com.nju.edu.erp.dao;

import com.nju.edu.erp.model.po.AccountPO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface AccountDao {
    /**
     * 创建新的银行账户
     * @param accountPO
     * @return
     */
    int createAccount(AccountPO accountPO);

    /**
     * 用账户名称删除银行账户
     * @param name
     * @return
     */
    int deleteAccount(String name);

    /**
     * 修改账户名称
     * @param name,id
     * @return
     */
    int updateName(String name, Integer id);

    /**
     * 更新余额
     * @param accountPO
     * @return
     */
    int updateBalance(AccountPO accountPO);

    /**
     * 根据姓名找账户
     * @param name
     * @return
     */
    AccountPO findByName(String name);

    /**
     * 查找所有账户
     * @return
     */
    List<AccountPO> findAll();

}
