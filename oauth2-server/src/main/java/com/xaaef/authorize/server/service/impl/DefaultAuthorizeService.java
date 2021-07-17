package com.xaaef.authorize.server.service.impl;

import com.xaaef.authorize.common.domain.ClientDetails;
import com.xaaef.authorize.common.domain.TokenValue;
import com.xaaef.authorize.common.domain.UserInfo;
import com.xaaef.authorize.common.enums.GrantType;
import com.xaaef.authorize.common.enums.OAuth2Error;
import com.xaaef.authorize.common.enums.UserStatus;
import com.xaaef.authorize.common.exception.OAuth2Exception;
import com.xaaef.authorize.common.token.OAuth2Token;
import com.xaaef.authorize.common.util.SecurityUtils;
import com.xaaef.authorize.server.props.OAuth2ServerProperties;
import com.xaaef.authorize.server.repository.ClientDetailsRepository;
import com.xaaef.authorize.server.repository.UserInfoRepository;
import com.xaaef.authorize.server.service.TokenCacheService;
import com.xaaef.authorize.server.util.JwtTokenUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.concurrent.TimeUnit;

/**
 * All rights Reserved, Designed By www.xaaef.com
 * <p>
 *
 * </p>
 *
 * @author Wang Chen Chen
 * @version 1.0.1
 * @date 2021/7/12 14:21
 * @copyright 2021 http://www.xaaef.com Inc. All rights reserved.
 */

@Slf4j
@AllArgsConstructor
public class DefaultAuthorizeService {

    protected ClientDetailsRepository clientDetailsRepository;

    protected UserInfoRepository userInfoRepository;

    protected TokenCacheService tokenCacheService;

    protected JwtTokenUtils jwtTokenUtils;

    /**
     * 获取 配置信息
     *
     * @author Wang Chen Chen
     * @date 2021/7/12 14:23
     */
    protected OAuth2ServerProperties getProps() {
        return jwtTokenUtils.getProps();
    }

    /**
     * 获取客户端信息
     *
     * @param clientId
     * @author Wang Chen Chen
     * @date 2021/7/12 14:23
     */
    protected ClientDetails getClient(String clientId) {
        return clientDetailsRepository.selectById(clientId);
    }

    /**
     * 获取用户信息
     *
     * @param account
     * @author Wang Chen Chen
     * @date 2021/7/12 14:23
     */
    protected UserInfo getUser(String account) {
        return userInfoRepository.selectByAccount(account);
    }

    /**
     * 校验 用户信息
     *
     * @param account
     * @param password
     * @return UserInfo
     * @author Wang Chen Chen
     * @date 2021/7/12 14:23
     */
    protected UserInfo validateUserInfo(String account, String password) throws OAuth2Exception {
        UserInfo userInfo = getUser(account);
        if (userInfo == null) {
            throw new OAuth2Exception(OAuth2Error.USER_INVALID);
        }
        if (UserStatus.DISABLE == userInfo.getStatus()) {
            throw new OAuth2Exception(OAuth2Error.USER_DISABLE);
        }
        if (UserStatus.LOCKING == userInfo.getStatus()) {
            throw new OAuth2Exception(OAuth2Error.USER_LOCKING);
        }
        if (!SecurityUtils.matchesPassword(password, userInfo.getPassword())) {
            throw new OAuth2Exception(OAuth2Error.USER_PASSWORD_ERROR);
        }
        return userInfo;
    }


    /**
     * 校验客户端
     *
     * @param clientId
     * @author Wang Chen Chen
     * @date 2021/7/12 14:23
     */
    protected ClientDetails validateClient(String clientId,
                                           String secret,
                                           GrantType serverGrantType,
                                           String grantType) throws OAuth2Exception {
        ClientDetails client = getClient(clientId);
        if (client == null) {
            throw new OAuth2Exception(OAuth2Error.CLIENT_INVALID);
        }
        if (!SecurityUtils.matchesPassword(secret, client.getSecret())) {
            throw new OAuth2Exception(OAuth2Error.CLIENT_SECRET_ERROR);
        }
        // 校验授权方式
        if (!StringUtils.equalsIgnoreCase(serverGrantType.getCode(), grantType)) {
            throw new OAuth2Exception(OAuth2Error.AUTHORIZATION_GRANT_TYPE);
        }
        // 校验客户端的授权方式
        if (!client.containsGrantType(grantType)) {
            throw new OAuth2Exception(OAuth2Error.AUTHORIZATION_GRANT_TYPE);
        }
        return client;
    }


    /**
     * 将 token 保存在 缓存中
     *
     * @param tokenId
     * @param tokenValue
     * @return OAuth2Token
     * @throws OAuth2Exception
     * @author Wang Chen Chen<932560435@qq.com>
     * @date 2020/7/23 16:48
     */
    protected void setTokenCache(String tokenId, TokenValue tokenValue) {
        var props = jwtTokenUtils.getProps();
        // Token 保存到 服务端。
        tokenCacheService.setToken(tokenId, tokenValue, props.getTokenExpired(), TimeUnit.SECONDS);
        // RefreshToken 保存到 服务端。
        tokenCacheService.setRefreshToken(tokenId, tokenValue, props.getRefreshTokenExpired(), TimeUnit.SECONDS);
    }

    /**
     * 构建 token
     *
     * @param tokenId
     * @return OAuth2Token
     * @throws OAuth2Exception
     * @author Wang Chen Chen<932560435@qq.com>
     * @date 2020/7/23 16:48
     */
    protected OAuth2Token buildToken(String tokenId) {
        var props = jwtTokenUtils.getProps();
        return OAuth2Token
                .builder()
                .accessToken(jwtTokenUtils.createAccessToken(tokenId))
                .expiresIn(props.getTokenExpired())
                .refreshToken(jwtTokenUtils.createRefreshToken(tokenId))
                .tokenType(props.getTokenType())
                .scope(props.getScope())
                .build();
    }

}
