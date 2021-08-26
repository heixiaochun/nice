package com.example.nice.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Demo DTO
 *
 * @author heixiaochun
 */
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode
public class DemoDTO implements Serializable {

    private static final long serialVersionUID = 7322141572689520087L;

    /**
     * 账号
     */
    private String accountNo;

    /**
     * 卡号
     */
    private String cardNo;

    /**
     * 账户余额
     */
    private Integer accountBalance;

    /**
     * 更新日期
     */
    private Timestamp updateTime;


}
