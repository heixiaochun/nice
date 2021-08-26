package com.example.nice.controller.base;

import com.example.nice.common.constant.NiceConstants;
import com.example.nice.dto.JsonResponseDTO;

/**
 * 提供controller层的公共方法
 *
 * @author heixiaochun
 */
public abstract class BaseController {

    /**
     * 封装json成功返回对象
     *
     * @param data 返回对象
     * @return json返回封装
     */
    protected JsonResponseDTO jsonSuccessResult(Object data) {
        JsonResponseDTO jsonResponseDTO = new JsonResponseDTO();
        jsonResponseDTO.setResponseCode(NiceConstants.RESULT_SUCCESS);
        jsonResponseDTO.setResponseMsg(NiceConstants.RESULT_MSG_SUCCESS);
        jsonResponseDTO.setData(data);
        return jsonResponseDTO;
    }

    /**
     * 封装json未查询到记录返回对象
     *
     * @return json返回封装
     */
    protected JsonResponseDTO jsonNotFoundResult() {
        JsonResponseDTO jsonResponseDTO = new JsonResponseDTO();
        jsonResponseDTO.setResponseCode(NiceConstants.RESULT_NOT_FOUND);
        jsonResponseDTO.setResponseMsg(NiceConstants.RESULT_MSG_NOT_FOUND);
        return jsonResponseDTO;
    }

}
