package com.xaaef.authorize.common.constant;

import lombok.Getter;

/**
 * All rights Reserved, Designed By www.xaaef.com
 * <p>
 * 认证状态
 * </p>
 *
 * @author Wang Chen Chen<932560435@qq.com>
 * @version 1.0.0
 * @date 2020/7/2313:44
 */

@Getter
public enum Oauth2ReturnStatus {

    /**
     * 认证错误，不知道啥原因
     */
    OAUTH2_EXCEPTION(400004, "认证错误!"),

    /**
     * access_token 不存在
     */
    ACCESS_TOKEN_INVALID(400010, "access_token does not exist"),

    /**
     * access_token 过期
     */
    ACCESS_TOKEN_EXPIRED(400011, "access_token has expired"),

    /**
     * refresh_token 不存在
     */
    REFRESH_TOKEN_INVALID(400012, "refresh_token does not exist"),

    /**
     * refresh_token 过期
     */
    REFRESH_TOKEN_EXPIRED(400013, "refresh_token has expired"),

    /**
     * 用户不存在
     */
    USER_INVALID(400020, "user does not exist"),

    /**
     * 用户密码错误
     */
    USER_PASSWORD_ERROR(400021, "user password error"),

    /**
     * 当前用户被禁用
     */
    USER_DISABLE(400022, "current user is disabled"),

    /**
     * 客户端不存在
     */
    CLIENT_INVALID(400030, "client does not exist"),

    /**
     * 客户端被禁用
     */
    CLIENT_DISABLE(400031, "current client is disabled"),

    /**
     * 客户端secret 错误
     */
    CLIENT_SECRET_ERROR(400032, "the is client_secret error"),

    /**
     * 授权类型错误
     */
    AUTHORIZATION_GRANT_TYPE(400033, "authorization grant_type error"),

    /**
     * 授权码模式中，使用code来换取 access_token，这个code不存在。
     */
    CODE_INVALID(400034, "code does not exist"),

    /**
     * 授权码模式中，回调的域名和数据库的域名不一致
     */
    DOMAIN_NAME_ILLEGAL(400035, "This domain name is illegal"),


    /**
     * 请求参数解析错误
     */
    REQUEST_PARAM_VALIDATE(400044, "request parameter parsing error"),


    /**
     * 验证码错误
     */
    VERIFICATION_CODE_ERROR(400045, "verification code error");


    /**
     * 状态信息
     */
    private Integer status;

    /**
     * 错误消息
     */
    private String error;

    Oauth2ReturnStatus(Integer status, String error) {
        this.status = status;
        this.error = error;
    }

}
