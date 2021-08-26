package com.example.nice.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * Demo Post DTO
 *
 * @author heixiaochun
 */
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode
public class DemoPostDTO implements Serializable {

    private static final long serialVersionUID = 7322141572689520087L;

    /**
     * 账号
     */
    @NotBlank
    private String accountNo;

    /**
     * 卡号
     */
    @NotBlank
    private String cardNo;

}
