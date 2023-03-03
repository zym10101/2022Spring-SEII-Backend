package com.nju.edu.erp.model.vo.employee;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class AccountVO { //账户的VO
    /**
     * id
     */
    private Integer id;

    /**
     * 账户名称
     */
    private String accountName;

    /**
     * 账户余额
     */
    private BigDecimal balance;

}
