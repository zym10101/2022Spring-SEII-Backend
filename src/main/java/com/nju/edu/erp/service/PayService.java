package com.nju.edu.erp.service;

import com.nju.edu.erp.enums.sheetState.PaySheetState;
import com.nju.edu.erp.model.vo.user.UserVO;
import com.nju.edu.erp.model.vo.pay.PaySheetVO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PayService {

    /**
     * 指定付款单
     * @param userVO
     * @param paySheetVO
     */
    void makePaySheet(UserVO userVO, PaySheetVO paySheetVO);

    /**
     * 根据单据状态获取付款单
     * @param state
     * @return
     */
    List<PaySheetVO> getPaySheetByState(PaySheetState state);

    /**
     * 审批单据
     * @param paySheetId
     * @param state
     */
    void approval(String paySheetId, PaySheetState state);
}

