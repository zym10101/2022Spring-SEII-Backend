package com.nju.edu.erp.dao;


import com.nju.edu.erp.enums.sheetState.ReceiveSheetState;
import com.nju.edu.erp.model.po.ReceiveSheetContentPO;
import com.nju.edu.erp.model.po.ReceiveSheetPO;
import com.nju.edu.erp.model.po.SaleSheetPO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
@Mapper
public interface ReceiveSheetDao {

    /**
     * 获取最近一条收款单
     *
     * @return
     */
    ReceiveSheetPO getLatestSheet();

    /**
     * 存入一条收款单记录
     *
     * @param toSave 一条收款单记录
     * @return 影响的行数
     */
    int saveSheet(ReceiveSheetPO toSave);

    /**
     * 把收款单上的具体内容存入数据库
     *
     * @param receiveSheetContent 入收款单上的具体内容
     */
    int saveBatchSheetContent(List<ReceiveSheetContentPO> receiveSheetContent);

    /**
     * 查找所有收款单
     */
    List<ReceiveSheetPO> findAllSheet();


    /**
     * 根据state返回收款单
     *
     * @param state 收款单状态
     * @return 收款单列表
     */
    List<ReceiveSheetPO> findAllByState(ReceiveSheetState state);

    /**
     * 查找指定id的收款单
     *
     * @param id
     * @return
     */
    ReceiveSheetPO findSheetById(String id);

    /**
     * 查找指定收款单下具体的内容
     *
     * @param sheetId
     */
    List<ReceiveSheetContentPO> findContentBySheetId(String sheetId);

    /**
     * 更新指定收款单的状态
     *
     * @param sheetId
     * @param state
     * @return
     */
    int updateSheetState(String sheetId, ReceiveSheetState state);

    /**
     * 根据当前状态更新收款单状态
     *
     * @param sheetId
     * @param prev
     * @param state
     * @return
     */
    int updateSheetStateOnPrev(String sheetId, ReceiveSheetState prev, ReceiveSheetState state);

}
