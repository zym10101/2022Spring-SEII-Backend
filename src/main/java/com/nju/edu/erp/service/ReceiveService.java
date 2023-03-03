package com.nju.edu.erp.service;

import com.nju.edu.erp.enums.sheetState.ReceiveSheetState;
import com.nju.edu.erp.model.vo.user.UserVO;
import com.nju.edu.erp.model.vo.receive.ReceiveSheetVO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ReceiveService {

    /**
     * 指定收款单
     * @param userVO
     * @param receiveSheetVO
     */
    void makeReceiveSheet(UserVO userVO, ReceiveSheetVO receiveSheetVO);

    /**
     * 根据单据状态获取收款单
     * @param state
     * @return
     */
    List<ReceiveSheetVO> getReceiveSheetByState(ReceiveSheetState state);

    /**
     * 审批单据
     * @param receiveSheetId
     * @param state
     */
    void approval(String receiveSheetId, ReceiveSheetState state);
}
