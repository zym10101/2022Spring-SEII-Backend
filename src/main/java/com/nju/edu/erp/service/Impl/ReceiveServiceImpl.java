package com.nju.edu.erp.service.Impl;

import com.nju.edu.erp.dao.AccountDao;
import com.nju.edu.erp.dao.ReceiveSheetDao;
import com.nju.edu.erp.enums.sheetState.ReceiveSheetState;
import com.nju.edu.erp.model.po.*;
import com.nju.edu.erp.model.vo.UserVO;
import com.nju.edu.erp.model.vo.receive.ReceiveSheetVO;
import com.nju.edu.erp.model.vo.receive.ReceiveSheetContentVO;
import com.nju.edu.erp.service.*;
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
public class ReceiveServiceImpl implements ReceiveService {

    private final ReceiveSheetDao receiveSheetDao;

    private final AccountService accountService;

    private final CustomerService customerService;

    private final AccountDao accountDao;


    @Autowired
    public ReceiveServiceImpl(ReceiveSheetDao receiveSheetDao, AccountService accountService, CustomerService customerService, AccountDao accountDao) {
        this.receiveSheetDao = receiveSheetDao;
        this.accountService = accountService;
        this.customerService = customerService;
        this.accountDao = accountDao;
    }

    @Override
    @Transactional
    public void makeReceiveSheet(UserVO userVO, ReceiveSheetVO receiveSheetVO) {
        ReceiveSheetPO receiveSheetPO = new ReceiveSheetPO();
        BeanUtils.copyProperties(receiveSheetVO,receiveSheetPO);
        receiveSheetPO.setOperator(userVO.getName());
        receiveSheetPO.setCreateTime(new Date());
        ReceiveSheetPO latestSheet = receiveSheetDao.getLatestSheet();
        String id = IdGenerator.generateSheetId(latestSheet == null ? null : latestSheet.getId(),"SKD");
        receiveSheetPO.setId(id);
        receiveSheetPO.setState(ReceiveSheetState.PENDING_LEVEL_1);
        BigDecimal totalAmount = BigDecimal.ZERO;
        List<ReceiveSheetContentPO> rContentPOList = new ArrayList<>();
        for(ReceiveSheetContentVO content : receiveSheetVO.getReceiveSheetContent()){
            ReceiveSheetContentPO rContentPO = new ReceiveSheetContentPO();
            BeanUtils.copyProperties(content,rContentPO);
            rContentPO.setReceiveSheetId(id);
            BigDecimal amount = rContentPO.getAmount();
            rContentPOList.add(rContentPO);
            totalAmount = totalAmount.add(amount);
        }
        receiveSheetDao.saveBatchSheetContent(rContentPOList);
        receiveSheetPO.setTotalAmount(totalAmount);
        receiveSheetDao.saveSheet(receiveSheetPO);
    }

    @Override
    public List<ReceiveSheetVO> getReceiveSheetByState(ReceiveSheetState state) {
        List<ReceiveSheetVO> res = new ArrayList<>();
        List<ReceiveSheetPO> all;
        if(state == null) {
            all = receiveSheetDao.findAllSheet();
        } else {
            all = receiveSheetDao.findAllByState(state);
        }
        for(ReceiveSheetPO po : all){
            ReceiveSheetVO vo = new ReceiveSheetVO();
            BeanUtils.copyProperties(po, vo);
            List<ReceiveSheetContentPO> alll = receiveSheetDao.findContentBySheetId(po.getId());
            List<ReceiveSheetContentVO> vos = new ArrayList<>();
            for (ReceiveSheetContentPO p : alll){
                ReceiveSheetContentVO v = new ReceiveSheetContentVO();
                BeanUtils.copyProperties(p,v);
                vos.add(v);
            }
            vo.setReceiveSheetContent(vos);
            res.add(vo);
        }
        return res;
    }

    @Override
    @Transactional
    public void approval(String receiveSheetId, ReceiveSheetState state) {
        if (state.equals(ReceiveSheetState.FAILURE)){
            ReceiveSheetPO receiveSheet = receiveSheetDao.findSheetById(receiveSheetId);
            if (receiveSheet.getState().equals(ReceiveSheetState.SUCCESS)){
                throw new RuntimeException("状态更新失败");
            }
            int effectLines = receiveSheetDao.updateSheetState(receiveSheetId, state);
            if(effectLines == 0) {
                throw new RuntimeException("状态更新失败");
            }
        } else {
            ReceiveSheetState prevState;
            if(state.equals(ReceiveSheetState.SUCCESS)){
                prevState = ReceiveSheetState.PENDING_LEVEL_1;
            } else {
                throw new RuntimeException("状态更新失败");
            }
            int effectLines = receiveSheetDao.updateSheetStateOnPrev(receiveSheetId, prevState, state);
            if (effectLines == 0) {
                throw new RuntimeException("状态更新失败");
            }
            if(state.equals(ReceiveSheetState.SUCCESS)){
                ReceiveSheetPO receiveSheetPO = receiveSheetDao.findSheetById(receiveSheetId);
                CustomerPO customerPO = customerService.findCustomerById(receiveSheetPO.getCustomer());
                customerPO.setPayable(customerPO.getPayable().add(receiveSheetPO.getTotalAmount()));
                customerService.updateCustomer(customerPO);
                // 更改账户余额
                List<ReceiveSheetContentPO> rSheetContentPOs = receiveSheetDao.findContentBySheetId(receiveSheetId);
                for (ReceiveSheetContentPO contentPO : rSheetContentPOs){
                    String accountName = contentPO.getBankAccount();
                    AccountPO accountPO = accountService.findAccountByName(accountName);
                    if (accountPO != null) {
                        accountPO.setBalance(accountPO.getBalance().add(contentPO.getAmount()));
                        accountDao.updateBalance(accountPO);
                    }
                }
            }
        }
    }
}
