package com.xaaef.authorize.common.exception;

import com.xaaef.authorize.common.enums.OAuth2Error;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import static com.xaaef.authorize.common.enums.OAuth2Error.OAUTH2_EXCEPTION;

/**
 * All rights Reserved, Designed By www.xaaef.com
 * <p>
 * OAuth2.0 认证异常
 * </p>
 *
 * @author Wang Chen Chen
 * @version 1.0.1
 * @date 2021/7/5 9:31
 * @copyright 2021 http://www.xaaef.com Inc. All rights reserved.
 */

@Getter
@Setter
@ToString
public class OAuth2Exception extends Exception {

    private Integer status;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public OAuth2Exception(int status, String message) {
        super(message);
        this.status = status;
    }

    public OAuth2Exception(String message) {
        this(OAUTH2_EXCEPTION.getStatus(), message);
    }

    public OAuth2Exception(OAuth2Error status) {
        this(status.getStatus(), status.getError());
    }

    public OAuth2Exception() {
        this(OAUTH2_EXCEPTION.getError());
    }

}
