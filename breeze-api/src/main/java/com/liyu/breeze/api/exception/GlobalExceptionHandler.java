package com.liyu.breeze.api.exception;


import com.liyu.breeze.api.annotation.Logging;
import com.liyu.breeze.api.util.I18nUtil;
import com.liyu.breeze.api.vo.ResponseVO;
import com.liyu.breeze.common.enums.ResponseCodeEnum;
import com.liyu.breeze.common.exception.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;


/**
 * 全局exception处理器
 *
 * @author gleiyu
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @Logging
    @ResponseBody
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseVO> defaultException(Exception e) {
        log.error(Arrays.toString(e.getStackTrace()));
        ResponseVO errorInfo = ResponseVO.error(I18nUtil.get(ResponseCodeEnum.ERROR.getValue()));
        return new ResponseEntity<>(errorInfo, HttpStatus.OK);
    }


    /**
     * 捕获自定义异常
     *
     * @param e 异常
     * @return ResponseEntity<ResponseVO>
     */
    @Logging
    @ResponseBody
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ResponseVO> defaultException(CustomException e) {
        ResponseVO errorInfo;
        if (ObjectUtils.isEmpty(e.getExceptionCode())) {
            errorInfo = ResponseVO.error(e.getMessage());
        } else {
            errorInfo = ResponseVO.error(e.getExceptionCode(), e.getMessage());
        }
        return new ResponseEntity<>(errorInfo, HttpStatus.OK);
    }

    /**
     * 权限访问异常
     *
     * @param e AccessDeniedException
     * @return ResponseEntity<ResponseVO>
     */
    @Logging
    @ResponseBody
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ResponseVO> defaultException(AccessDeniedException e) {
        ResponseVO errorInfo = ResponseVO.error(ResponseCodeEnum.ERROR_NO_PRIVILEGE.getCode(), I18nUtil.get(ResponseCodeEnum.ERROR_NO_PRIVILEGE.getValue()));
        return new ResponseEntity<>(errorInfo, HttpStatus.OK);
    }

    /**
     * primary key duplicate
     *
     * @param e SQLIntegrityConstraintViolationException
     * @return ResponseEntity<ResponseVO>
     */
    @Logging
    @ResponseBody
    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity<ResponseVO> defaultException(DuplicateKeyException e) {
        ResponseVO errorInfo = ResponseVO.error(ResponseCodeEnum.ERROR_DUPLICATE_DATA.getCode(), I18nUtil.get(ResponseCodeEnum.ERROR_DUPLICATE_DATA.getValue()));
        return new ResponseEntity<>(errorInfo, HttpStatus.OK);
    }


    /**
     * 邮件发送异常
     *
     * @param e MailException
     * @return ResponseEntity<ResponseVO>
     */
    @Logging
    @ResponseBody
    @ExceptionHandler(MailException.class)
    public ResponseEntity<ResponseVO> defaultException(MailException e) {
        ResponseVO errorInfo = ResponseVO.error(ResponseCodeEnum.ERROR_EMAIL.getCode(), I18nUtil.get(ResponseCodeEnum.ERROR_EMAIL.getValue()));
        return new ResponseEntity<>(errorInfo, HttpStatus.OK);
    }

}
