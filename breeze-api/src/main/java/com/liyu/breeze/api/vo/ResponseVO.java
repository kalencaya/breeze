package com.liyu.breeze.api.vo;

import cn.hutool.json.JSONUtil;
import com.liyu.breeze.common.enums.ErrorShowTypeEnum;
import com.liyu.breeze.common.enums.ResponseCodeEnum;
import lombok.Data;
import lombok.SneakyThrows;

/**
 * @author gleiyu
 */
@Data
public class ResponseVO {
    /**
     * operate result
     */
    private Boolean success;

    /**
     * result data
     */
    private Object data;
    /**
     * error code
     */
    private String errorCode;
    /**
     * error message
     */
    private String errorMessage;
    /**
     * ErrorShowTypeEnum
     */
    private Integer showType;

    private ResponseVO() {
    }

    public static ResponseVO sucess() {
        ResponseVO info = new ResponseVO();
        info.setSuccess(true);
        return info;
    }

    /**
     * default error info
     *
     * @param message message
     * @return ResponseVO
     */
    public static ResponseVO error(String message) {
        ResponseVO info = new ResponseVO();
        info.setSuccess(false);
        info.setErrorCode(ResponseCodeEnum.ERROR.getCode());
        info.setErrorMessage(message);
        info.setShowType(ErrorShowTypeEnum.ERROR_MESSAGE.getCode());
        return info;
    }

    public static ResponseVO error(ResponseCodeEnum info) {
        return error(info.getCode(), info.getValue());
    }

    public static ResponseVO error(String code, String message) {
        ResponseVO info = new ResponseVO();
        info.setSuccess(false);
        info.setErrorCode(code);
        info.setErrorMessage(message);
        info.setShowType(ErrorShowTypeEnum.ERROR_MESSAGE.getCode());
        return info;
    }

    public static ResponseVO error(String code, String message, ErrorShowTypeEnum showType) {
        ResponseVO info = new ResponseVO();
        info.setSuccess(false);
        info.setErrorCode(code);
        info.setErrorMessage(message);
        info.setShowType(showType.getCode());
        return info;
    }


    @SneakyThrows
    @Override
    public String toString() {
        return JSONUtil.toJsonStr(this);
    }
}
