package com.nju.edu.erp.dao;

import com.nju.edu.erp.model.po.warehouse.WarehouseBSDPO;
import com.nju.edu.erp.model.po.warehouse.WarehouseBYDPO;
import com.nju.edu.erp.model.po.warehouse.WarehousePO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
@Mapper
public interface WarehouseDao {
    void saveBatch(List<WarehousePO> warehousePOList);

    void deductQuantity(WarehousePO warehousePO);

    List<WarehousePO> findAllNotZeroByPidSortedByBatchId(String pid);

    /**
     * 按照商品id获取现存商品（存量>0）并按价格排序
     *
     * @param pid
     * @return
     */
    List<WarehousePO> findByPidOrderByPurchasePricePos(String pid);

    WarehousePO findOneByPidAndBatchId(String pid, Integer batchId);


    /**
     * 查看所有库存（库存盘点）
     *
     * @return 所有库存
     */
    List<WarehousePO> findAll();

    /**
     * 查看所有报溢单
     *
     * @return 所有报溢单
     */
    List<WarehouseBYDPO> getAllOverflow();

    /**
     * 查看所有报损单
     *
     * @return 所有报损单
     */
    List<WarehouseBSDPO> getAllLoss();

    /**
     * 查看一段时间报溢总金额
     *
     * @param beginTime 开始时间
     * @param endTime   结束时间
     * @return 报溢总金额
     */
    int getOverflowByTime(Date beginTime, Date endTime);

    /**
     * 查看一段时间报损总金额
     *
     * @param beginTime 开始时间
     * @param endTime   结束时间
     * @return 报损总金额
     */
    int getLossByTime(Date beginTime, Date endTime);

    /**
     * 查看一段时间赠送总金额
     *
     * @param beginTime 开始时间
     * @param endTime   结束时间
     * @return 赠送总金额
     */
    int getPresentByTime(Date beginTime, Date endTime);
}
