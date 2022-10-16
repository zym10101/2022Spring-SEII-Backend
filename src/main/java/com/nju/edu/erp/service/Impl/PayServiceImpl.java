package com.nju.edu.erp.service.Impl;

import com.nju.edu.erp.dao.AccountDao;
import com.nju.edu.erp.dao.PaySheetDao;
import com.nju.edu.erp.enums.sheetState.PaySheetState;
import com.nju.edu.erp.model.po.AccountPO;
import com.nju.edu.erp.model.po.CustomerPO;
import com.nju.edu.erp.model.po.PaySheetContentPO;
import com.nju.edu.erp.model.po.PaySheetPO;
import com.nju.edu.erp.model.vo.UserVO;
import com.nju.edu.erp.model.vo.pay.PaySheetContentVO;
import com.nju.edu.erp.model.vo.pay.PaySheetVO;
import com.nju.edu.erp.service.AccountService;
import com.nju.edu.erp.service.CustomerService;
import com.nju.edu.erp.service.PayService;
import com.nju.edu.erp.utils.IdGenerator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PayServiceImpl implements PayService {

    private final PaySheetDao paySheetDao;

    private final AccountDao accountDao;

    private final CustomerService customerService;

    private final AccountService accountService;


    @Autowired
    public PayServiceImpl(PaySheetDao paySheetDao, AccountDao accountDao, CustomerService customerService, AccountService accountService) {
        this.paySheetDao = paySheetDao;
        this.accountDao = accountDao;
        this.customerService = customerService;
        this.accountService = accountService;
    }

    @Override
    @Transactional
    public void makePaySheet(UserVO userVO, PaySheetVO paySheetVO) {
        PaySheetPO paySheetPO = new PaySheetPO();
        BeanUtils.copyProperties(paySheetVO,paySheetPO);
        paySheetPO.setOperator(userVO.getName());
        paySheetPO.setCreateTime(new Date());
        PaySheetPO latestSheet = paySheetDao.getLatestSheet();
        String id = IdGenerator.generateSheetId(latestSheet == null ? null : latestSheet.getId(),"SKD");
        paySheetPO.setId(id);
        paySheetPO.setState(PaySheetState.PENDING_LEVEL_1);
        BigDecimal totalAmount = BigDecimal.ZERO;
        List<PaySheetContentPO> rContentPOList = new ArrayList<>();
        for(PaySheetContentVO content : paySheetVO.getPaySheetContent()){
            PaySheetContentPO rContentPO = new PaySheetContentPO();
            BeanUtils.copyProperties(content,rContentPO);
            rContentPO.setPaySheetId(id);
            BigDecimal amount = rContentPO.getAmount();
            rContentPOList.add(rContentPO);
            totalAmount = totalAmount.add(amount);
        }
        paySheetDao.saveBatchSheetContent(rContentPOList);
        paySheetPO.setTotalAmount(totalAmount);
        paySheetDao.saveSheet(paySheetPO);
    }

    @Override
    public List<PaySheetVO> getPaySheetByState(PaySheetState state) {
        List<PaySheetVO> res = new ArrayList<>();
        List<PaySheetPO> all;
        if(state == null) {
            all = paySheetDao.findAllSheet();
        } else {
            all = paySheetDao.findAllByState(state);
        }
        for(PaySheetPO po : all){
            PaySheetVO vo = new PaySheetVO();
            BeanUtils.copyProperties(po, vo);
            List<PaySheetContentPO> alll = paySheetDao.findContentBySheetId(po.getId());
            List<PaySheetContentVO> vos = new ArrayList<>();
            for (PaySheetContentPO p : alll){
                PaySheetContentVO v = new PaySheetContentVO();
                BeanUtils.copyProperties(p,v);
                vos.add(v);
            }
            vo.setPaySheetContent(vos);
            res.add(vo);
        }
        return res;
    }

    @Override
    @Transactional
    public void approval(String paySheetId, PaySheetState state) {
        if (state.equals(PaySheetState.FAILURE)){
            PaySheetPO paySheet = paySheetDao.findSheetById(paySheetId);
            if (paySheet.getState().equals(PaySheetState.SUCCESS)){
                throw new RuntimeException("状态更新失败");
            }
            int effectLines = paySheetDao.updateSheetState(paySheetId, state);
            if(effectLines == 0) {
                throw new RuntimeException("状态更新失败");
            }
        } else {
            PaySheetState prevState;
            if(state.equals(PaySheetState.SUCCESS)){
                prevState = PaySheetState.PENDING_LEVEL_1;
            } else {
                throw new RuntimeException("状态更新失败");
            }
            int effectLines = paySheetDao.updateSheetStateOnPrev(paySheetId, prevState, state);
            if (effectLines == 0) {
                throw new RuntimeException("状态更新失败");
            }
            if(state.equals(PaySheetState.SUCCESS)){
                PaySheetPO paySheetPO = paySheetDao.findSheetById(paySheetId);
                CustomerPO customerPO = customerService.findCustomerById(paySheetPO.getCustomer());
                customerPO.setReceivable(customerPO.getReceivable().add(paySheetPO.getTotalAmount()));
                customerService.updateCustomer(customerPO);
                // 更改账户余额
                List<PaySheetContentPO> pSheetContentPOs = paySheetDao.findContentBySheetId(paySheetId);
                for (PaySheetContentPO contentPO : pSheetContentPOs){
                    String accountName = contentPO.getBankAccount();
                    AccountPO accountPO = accountService.findAccountByName(accountName);
                    if (accountPO != null) {
                        accountPO.setBalance(accountPO.getBalance().subtract(contentPO.getAmount()));
                        accountDao.updateBalance(accountPO);
                    }
                }
            }
        }
    }
}

