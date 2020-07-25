package com.xaaef.authorize.common.exception;

import com.xaaef.authorize.common.constant.Oauth2ReturnStatus;

import static com.xaaef.authorize.common.constant.Oauth2ReturnStatus.OAUTH2_EXCEPTION;

/**
 * All rights Reserved, Designed By www.xaaef.com
 * <p>
 * OAuth2.0 认证异常
 * </p>
 *
 * @author Wang Chen Chen<932560435@qq.com>
 * @version 1.0.0
 * @date 2020/7/2311:06
 */

public class Oauth2Exception extends Exception {

    private Integer status;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Oauth2Exception(int status, String message) {
        super(message);
        this.status = status;
    }

    public Oauth2Exception(String message) {
        this(OAUTH2_EXCEPTION.getStatus(), message);
    }

    public Oauth2Exception(Oauth2ReturnStatus status) {
        this(status.getStatus(), status.getError());
    }

    public Oauth2Exception() {
        this(OAUTH2_EXCEPTION.getError());
    }

}
