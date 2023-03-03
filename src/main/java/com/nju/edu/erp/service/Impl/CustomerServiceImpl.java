package com.nju.edu.erp.service.Impl;

import com.nju.edu.erp.dao.CustomerDao;
import com.nju.edu.erp.enums.CustomerType;
import com.nju.edu.erp.exception.MyServiceException;
import com.nju.edu.erp.model.po.customer.CustomerPO;
import com.nju.edu.erp.model.vo.customer.CustomerVO;
import com.nju.edu.erp.service.CustomerService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerDao customerDao;

    @Autowired
    public CustomerServiceImpl(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    /**
     * 根据id更新客户信息
     *
     * @param customerPO 客户信息
     */
    @Override
    public void updateCustomer(CustomerPO customerPO) {
        customerDao.updateOne(customerPO);
    }

    /**
     * 根据type查找对应类型的客户
     *
     * @param type 客户类型
     * @return 客户列表
     */
    @Override
    public List<CustomerPO> getCustomersByType(CustomerType type) {

        return customerDao.findAllByType(type);
    }

    /**
     * 新增客户
     */
    @Override
    public void registerCustomer(CustomerVO customerVO) {
        //先检查这个用户是否存在
        CustomerPO customerPO = customerDao.findOneById(customerVO.getId());
        if (customerPO != null) {
            throw new MyServiceException("A0001", "用户名已存在");
        }
        CustomerPO customerPOSave = new CustomerPO();
        BeanUtils.copyProperties(customerVO, customerPOSave);
        customerDao.createCustomer(customerPOSave);

    }

    /**
     * 删除客户
     */
    @Override
    public void dropCustomer(Integer id) {
        //先检查这个用户是否存在
        CustomerPO customerPO = customerDao.findOneById(id);
        if (customerPO == null) {
            throw new MyServiceException("A0001", "该用户不存在");
        }

        customerDao.deleteCustomer(id);
    }

    @Override
    public CustomerPO findCustomerById(Integer supplier) {
        return customerDao.findOneById(supplier);
    }
}
