package com.nju.edu.erp.enums.sheetState;

import com.nju.edu.erp.enums.BaseEnum;

public enum WageSheetState implements BaseEnum<WageSheetState, String> {

    PENDING_LEVEL_1("待一级审批"), //待人力资源人员审批
    PENDING_LEVEL_2("待二级审批"), // 待总经理审批
    SUCCESS("审批完成"),
    FAILURE("审批失败");

    private final String value;

    WageSheetState(String value) {
        this.value = value;
    }


    @Override
    public String getValue() {
        return value;
    }
}