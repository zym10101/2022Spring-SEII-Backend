package com.nju.edu.erp.enums.sheetState;

import com.nju.edu.erp.enums.BaseEnum;

/**
 * @author zym
 * @date 2022/06/28 12:51
 **/
public enum WarehouseBSDState implements BaseEnum<WarehouseBSDState, String> {
    DRAFT("草稿"), // 待仓库管理员确认
    PENDING("待审批"), // 待总经理审批
    SUCCESS("审批完成"),
    FAILURE("审批失败");

    private final String value;

    WarehouseBSDState(String value) {
        this.value = value;
    }


    @Override
    public String getValue() {
        return value;
    }
}
