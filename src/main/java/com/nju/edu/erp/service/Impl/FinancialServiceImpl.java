package com.nju.edu.erp.service.Impl;

import com.nju.edu.erp.dao.CustomerDao;
import com.nju.edu.erp.dao.FinancialSheetDao;
import com.nju.edu.erp.model.po.PayReceiveSheetIODetailPO;
import com.nju.edu.erp.model.po.WageSheetPO;
import com.nju.edu.erp.service.FinancialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author zym
 * @date 2022/06/29 23:19
 **/
@Service
public class FinancialServiceImpl implements FinancialService {


    private final FinancialSheetDao financialSheetDao;

    @Autowired
    public FinancialServiceImpl(FinancialSheetDao financialSheetDao) {
        this.financialSheetDao = financialSheetDao;
    }

    @Override
    public List<PayReceiveSheetIODetailPO> getPayReceiveSheet(String beginDateStr, String endDateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date beginTime = sdf.parse(beginDateStr);
            Date endTime = sdf.parse(endDateStr);
            if (beginTime.before(endTime)) {
                return financialSheetDao.getPayReceiveSheet(beginTime, endTime);
            } else {
                return null;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    @Override
    public List<WageSheetPO> getWageSheet(String beginDateStr, String endDateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date beginTime = sdf.parse(beginDateStr);
            Date endTime = sdf.parse(endDateStr);
            if (beginTime.before(endTime)) {
                return financialSheetDao.getWageSheet(beginTime, endTime);
            } else {
                return null;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}
