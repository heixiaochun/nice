package com.example.nice.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * json返回对象封装
 *
 * @author heixiaochun
 */
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode
public class JsonResponseDTO implements Serializable {

    private static final long serialVersionUID = -3284657233135282855L;

    private String responseCode;

    private String responseMsg;

    private Object data;

}
