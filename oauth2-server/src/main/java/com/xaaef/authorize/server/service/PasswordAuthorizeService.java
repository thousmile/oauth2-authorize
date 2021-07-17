package com.xaaef.authorize.server.service;

import com.xaaef.authorize.common.exception.OAuth2Exception;
import com.xaaef.authorize.common.token.OAuth2Token;
import com.xaaef.authorize.server.params.PasswordModeParam;

/**
 * All rights Reserved, Designed By www.xaaef.com
 * <p>
 * 密码 授权服务
 * </p>
 *
 * @author Wang Chen Chen
 * @version 1.0.1
 * @date 2021/7/12 14:21
 * @copyright 2021 http://www.xaaef.com Inc. All rights reserved.
 */

public interface PasswordAuthorizeService {

    /**
     * 密码 认证模式
     *
     * @param param
     * @return OAuth2Token
     * @throws OAuth2Exception
     * @author Wang Chen Chen<932560435@qq.com>
     * @date 2020/7/23 16:48
     */
    OAuth2Token authorize(PasswordModeParam param) throws OAuth2Exception;

}
