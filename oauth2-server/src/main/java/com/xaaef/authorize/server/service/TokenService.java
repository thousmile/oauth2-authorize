package com.xaaef.authorize.server.service;

import com.xaaef.authorize.common.domain.TokenValue;
import com.xaaef.authorize.common.exception.OAuth2Exception;
import com.xaaef.authorize.common.token.OAuth2Token;

/**
 * All rights Reserved, Designed By www.xaaef.com
 * <p>
 * 服务端 token 认证
 * </p>
 *
 * @author Wang Chen Chen
 * @version 1.0.1
 * @date 2021/7/12 16:28
 * @copyright 2021 http://www.xaaef.com Inc. All rights reserved.
 */

public interface TokenService {

    /**
     * 校验 token
     *
     * @param jwtToken
     * @author Wang Chen Chen
     * @date 2021/7/12 16:29
     */
    TokenValue validate(String jwtToken) throws OAuth2Exception;

    /**
     * url 是否过滤
     *
     * @param url
     * @return boolean
     * @author Wang Chen Chen
     * @date 2021/7/13 10:30
     */
    boolean urlIgnore(String url);

    /**
     * 刷新 token
     *
     * @return UserVo
     */
    OAuth2Token refresh(String bearerToken) throws OAuth2Exception;

    /**
     * 退出登录
     */
    void logout();

}
