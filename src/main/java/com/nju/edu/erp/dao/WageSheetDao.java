package com.nju.edu.erp.dao;


import com.nju.edu.erp.enums.sheetState.WageSheetState;
import com.nju.edu.erp.model.po.salary.WageSheetPO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
@Mapper
public interface WageSheetDao {

    /**
     * 获取最近一条工资单
     *
     * @return
     */
    WageSheetPO getLatestSheet();

    /**
     * 存入一条工资单记录
     *
     * @param toSave 一条工资单记录
     * @return 影响的行数
     */
    int saveSheet(WageSheetPO toSave);

    /**
     * 查找所有工资单
     */
    List<WageSheetPO> findAllSheet();


    /**
     * 根据state返回工资单
     *
     * @param state 工资单状态
     * @return 工资单列表
     */
    List<WageSheetPO> findAllByState(WageSheetState state);

    /**
     * 查找指定id的工资单
     *
     * @param id
     * @return
     */
    WageSheetPO findSheetById(String id);

    /**
     * 更新指定工资单的状态
     *
     * @param sheetId
     * @param state
     * @return
     */
    int updateSheetState(String sheetId, WageSheetState state);

    /**
     * 根据当前状态更新工资单状态
     *
     * @param sheetId
     * @param prev
     * @param state
     * @return
     */
    int updateSheetStateOnPrev(String sheetId, WageSheetState prev, WageSheetState state);

    /**
     * 获取一段时间里的所有员工的工资总额
     * @param beginTime
     * @param endTime
     * @return 工资总额
     */
    int getSalaryByTime(Date beginTime, Date endTime);

}

