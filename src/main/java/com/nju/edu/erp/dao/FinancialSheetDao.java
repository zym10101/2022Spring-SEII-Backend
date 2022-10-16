package com.nju.edu.erp.dao;


import com.nju.edu.erp.model.po.PayReceiveSheetIODetailPO;
import com.nju.edu.erp.model.po.WageSheetPO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
@Mapper
public interface FinancialSheetDao {
    /**
     * 获取所有收付款单
     * @param beginTime 开始日期
     * @param endTime 结束日期
     * @return
     */
    List<PayReceiveSheetIODetailPO> getPayReceiveSheet(Date beginTime, Date endTime);

    /**
     * 获取所有工资单
     * @param beginTime 开始日期
     * @param endTime 结束日期
     * @return
     */
    List<WageSheetPO> getWageSheet(Date beginTime, Date endTime);
}
