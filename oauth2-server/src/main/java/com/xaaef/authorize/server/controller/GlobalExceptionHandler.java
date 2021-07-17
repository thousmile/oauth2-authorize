package com.xaaef.authorize.server.controller;

import com.xaaef.authorize.common.exception.OAuth2Exception;
import com.xaaef.authorize.common.util.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * All rights Reserved, Designed By www.xaaef.com
 * <p>
 * 全局异常处理
 * </p>
 *
 * @author Wang Chen Chen
 * @version 1.0.1
 * @date 2021/7/13 13:47
 * @copyright 2021 http://www.xaaef.com Inc. All rights reserved.
 */

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理自定义的业务异常
     *
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler(value = OAuth2Exception.class)
    public JsonResult<String> bizExceptionHandler(HttpServletRequest request, OAuth2Exception e) {
        log.error("发生授权异常！ URL : {}  原因是： {}  {} ", request.getRequestURI(), e.getStatus(), e.getMessage());
        return JsonResult.error(e.getStatus(), e.getMessage());
    }

    /**
     * 处理空指针的异常
     *
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler(value = NullPointerException.class)
    public JsonResult<String> exceptionHandler(HttpServletRequest request, NullPointerException e) {
        log.error("发生空指针异常！ URL : {}  原因是: {} ", request.getRequestURI(), e);
        int status = HttpStatus.INTERNAL_SERVER_ERROR.value();
        return JsonResult.error(status, e.getMessage());
    }


    /**
     * 处理其他异常
     *
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    public JsonResult<String> exceptionHandler(HttpServletRequest request, Exception e) {
        log.error("发生空指针异常！ URL : {}  原因是: {} ", request.getRequestURI(), e);
        int status = HttpStatus.INTERNAL_SERVER_ERROR.value();
        return JsonResult.error(status, e.getMessage());
    }

}
