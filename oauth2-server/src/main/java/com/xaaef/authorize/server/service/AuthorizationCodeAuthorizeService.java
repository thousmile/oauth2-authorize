package com.xaaef.authorize.server.service;

import com.xaaef.authorize.common.exception.OAuth2Exception;
import com.xaaef.authorize.common.token.OAuth2Token;
import com.xaaef.authorize.server.params.AuthorizationCodeModeParam;
import com.xaaef.authorize.server.params.GetCodeModeParam;
import com.xaaef.authorize.server.params.LoginParam;

/**
 * All rights Reserved, Designed By www.xaaef.com
 * <p>
 * 授权码 授权服务
 * </p>
 *
 * @author Wang Chen Chen
 * @version 1.0.1
 * @date 2021/7/12 14:21
 * @copyright 2021 http://www.xaaef.com Inc. All rights reserved.
 */

public interface AuthorizationCodeAuthorizeService {

    /**
     * 校验客户端，
     *
     * @param param
     * @return
     * @throws OAuth2Exception
     * @author Wang Chen Chen<932560435@qq.com>
     * @date 2020/7/23 16:48
     */
    String validateClient(GetCodeModeParam param) throws OAuth2Exception;

    /**
     * 用户登录
     *
     * @param param
     * @return 链接，跳转到第三方网站的链接
     * @throws OAuth2Exception
     * @author Wang Chen Chen<932560435@qq.com>
     * @date 2020/7/23 16:48
     */
    String login(LoginParam param) throws OAuth2Exception;

    /**
     * 客户端认证模式
     *
     * @param param
     * @return OAuth2Token
     * @throws OAuth2Exception
     * @author Wang Chen Chen<932560435@qq.com>
     * @date 2020/7/23 16:48
     */
    OAuth2Token authorize(AuthorizationCodeModeParam param) throws OAuth2Exception;

}
