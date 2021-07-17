package com.xaaef.authorize.server.service;

import com.xaaef.authorize.common.domain.ClientDetails;
import com.xaaef.authorize.common.domain.TokenValue;
import com.xaaef.authorize.common.domain.UserInfo;
import com.xaaef.authorize.server.params.GetCodeModeParam;

import java.util.concurrent.TimeUnit;

/**
 * All rights Reserved, Designed By www.xaaef.com
 * <p>
 * 服务端 token 缓存
 * </p>
 *
 * @author Wang Chen Chen
 * @version 1.0.1
 * @date 2021/7/12 16:28
 * @copyright 2021 http://www.xaaef.com Inc. All rights reserved.
 */

public interface TokenCacheService {

    /**
     * 根据 tokenId 获取 token
     *
     * @param tokenId
     * @return TokenValue
     * @author Wang Chen Chen
     * @date 2021/7/12 16:29
     */
    TokenValue getToken(String tokenId);

    /**
     * 设置 token
     *
     * @param tokenValue
     * @author Wang Chen Chen
     * @date 2021/7/12 16:29
     */
    void setToken(String tokenId, TokenValue tokenValue, long timeout, TimeUnit unit);

    /**
     * 根据 tokenId 获取 refresh token
     *
     * @param tokenId
     * @return TokenValue
     * @author Wang Chen Chen
     * @date 2021/7/12 16:29
     */
    TokenValue getRefreshToken(String tokenId);

    /**
     * 设置 refresh token
     *
     * @param tokenValue
     * @author Wang Chen Chen
     * @date 2021/7/12 16:29
     */
    void setRefreshToken(String tokenId, TokenValue tokenValue, long timeout, TimeUnit unit);

    /**
     * 移除 token
     *
     * @param tokenId
     * @author Wang Chen Chen
     * @date 2021/7/12 16:29
     */
    void remove(String tokenId);

    /**
     * 根据 key 获取 string 值
     *
     * @param key
     * @return TokenValue
     * @author Wang Chen Chen
     * @date 2021/7/12 16:29
     */
    String getString(String key);

    /**
     * 设置 string 值
     *
     * @param value
     * @author Wang Chen Chen
     * @date 2021/7/12 16:29
     */
    void setString(String key, String value, long timeout, TimeUnit unit);


    /**
     * 根据 key 获取 ClientDetails 值
     *
     * @param key
     * @return TokenValue
     * @author Wang Chen Chen
     * @date 2021/7/12 16:29
     */
    ClientDetails getClient(String key);


    /**
     * 设置 ClientDetails 值
     *
     * @param client
     * @author Wang Chen Chen
     * @date 2021/7/12 16:29
     */
    void setClient(String key, ClientDetails client, long timeout, TimeUnit unit);


    /**
     * 根据 key 获取 GetCodeModeParam 值
     *
     * @param key
     * @return TokenValue
     * @author Wang Chen Chen
     * @date 2021/7/12 16:29
     */
    GetCodeModeParam getCodeMode(String key);


    /**
     * 设置 GetCodeModeParam 值
     *
     * @param param
     * @author Wang Chen Chen
     * @date 2021/7/12 16:29
     */
    void setCodeMode(String key, GetCodeModeParam param, long timeout, TimeUnit unit);

    /**
     * 根据 key 获取 UserInfo 值
     *
     * @param key
     * @return TokenValue
     * @author Wang Chen Chen
     * @date 2021/7/12 16:29
     */
    UserInfo getUserInfo(String key);

    /**
     * 设置 GetCodeModeParam 值
     *
     * @param userInfo
     * @author Wang Chen Chen
     * @date 2021/7/12 16:29
     */
    void setUserInfo(String key, UserInfo userInfo, long timeout, TimeUnit unit);

}
