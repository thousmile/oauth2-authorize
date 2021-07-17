package com.xaaef.authorize.server.service.impl;

import com.xaaef.authorize.common.domain.ClientDetails;
import com.xaaef.authorize.common.domain.TokenValue;
import com.xaaef.authorize.common.domain.UserInfo;
import com.xaaef.authorize.common.enums.GrantType;
import com.xaaef.authorize.server.params.GetCodeModeParam;
import com.xaaef.authorize.server.props.OAuth2ServerProperties;
import com.xaaef.authorize.server.service.TokenCacheService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

import static com.xaaef.authorize.common.util.JsonUtils.DEFAULT_DATE_TIME_PATTERN;
import static com.xaaef.authorize.server.constant.TokenConstant.*;


/**
 * All rights Reserved, Designed By www.xaaef.com
 * <p>
 * </p>
 *
 * @author Wang Chen Chen
 * @version 1.0.1
 * @date 2021/7/12 16:33
 * @copyright 2021 http://www.xaaef.com Inc. All rights reserved.
 */


@Slf4j
@Service
@AllArgsConstructor
public class RedisTokenCacheServiceImpl implements TokenCacheService {

    private RedisTemplate<String, Object> redisTemplate;

    private OAuth2ServerProperties props;

    @Override
    public TokenValue getToken(String tokenId) {
        TokenValue token = (TokenValue) redisTemplate
                .opsForValue().get(LOGIN_TOKEN_KEY + tokenId);
        return token;
    }

    @Override
    public void setToken(String tokenId, TokenValue tokenValue, long timeout, TimeUnit unit) {
        tokenValue.setTokenId(tokenId);
        // 将随机id 跟 当前登录的用户关联，在一起！
        redisTemplate.opsForValue().set(LOGIN_TOKEN_KEY + tokenId, tokenValue, timeout, unit);

        // 如果是 客户端模式，那么 就没有 单点登录
        if (tokenValue.getGrantType() == GrantType.CLIENT_CREDENTIALS) {
            return;
        }
        // 其他模式，判断是否开启 单点登录
        if (props.getSso()) {
            UserInfo loginUser = tokenValue.getUser();
            // 在线用户，
            String onlineUserKey = ONLINE_USER_KEY + loginUser.getUsername();

            // 获取使用 此用户登录的 上个用户的 tokenId
            String oldLoginKey = getString(onlineUserKey);

            // 判断用户名。是否已经登录了！
            if (StringUtils.hasText(oldLoginKey)) {
                // 移除 之前登录的 用户 token 以及 refresh_token
                remove(LOGIN_TOKEN_KEY + oldLoginKey);
                remove(REFRESH_TOKEN_KEY + oldLoginKey);

                // 移除 之前登录的在线用户
                remove(onlineUserKey);

                // 获取当前时间
                String milli = LocalDateTime.now().format(DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_PATTERN));

                // 将 被强制挤下线的用户，以及时间，保存到 redis中，提示给前端用户！
                setString(FORCED_OFFLINE_KEY + oldLoginKey, milli, props.getPromptExpired(), TimeUnit.SECONDS);
            }

            // 设置 在线用户，为新用户
            setString(onlineUserKey, tokenId, timeout, unit);
        }
    }

    @Override
    public TokenValue getRefreshToken(String tokenId) {
        TokenValue token = (TokenValue) redisTemplate
                .opsForValue().get(REFRESH_TOKEN_KEY + tokenId);
        return token;
    }

    @Override
    public void setRefreshToken(String tokenId, TokenValue tokenValue, long timeout, TimeUnit unit) {
        tokenValue.setTokenId(tokenId);
        // 将随机id 跟 当前登录的用户关联，在一起！
        redisTemplate.opsForValue().set(REFRESH_TOKEN_KEY + tokenId, tokenValue, timeout, unit);
    }

    @Override
    public void remove(String tokenId) {
        redisTemplate.delete(tokenId);
    }

    @Override
    public String getString(String key) {
        String str = (String) redisTemplate.opsForValue().get(key);
        return str;
    }

    @Override
    public void setString(String key, String value, long timeout, TimeUnit unit) {
        redisTemplate.opsForValue().set(key, value, timeout, unit);
    }

    @Override
    public ClientDetails getClient(String key) {
        ClientDetails client = (ClientDetails) redisTemplate.opsForValue().get(key);
        return client;
    }

    @Override
    public void setClient(String key, ClientDetails client, long timeout, TimeUnit unit) {
        redisTemplate.opsForValue().set(key, client, timeout, unit);
    }

    @Override
    public GetCodeModeParam getCodeMode(String key) {
        GetCodeModeParam param = (GetCodeModeParam) redisTemplate.opsForValue().get(key);
        return param;
    }

    @Override
    public void setCodeMode(String key, GetCodeModeParam param, long timeout, TimeUnit unit) {
        redisTemplate.opsForValue().set(key, param, timeout, unit);
    }

    @Override
    public UserInfo getUserInfo(String key) {
        UserInfo userInfo = (UserInfo) redisTemplate.opsForValue().get(key);
        return userInfo;
    }

    @Override
    public void setUserInfo(String key, UserInfo userInfo, long timeout, TimeUnit unit) {
        redisTemplate.opsForValue().set(key, userInfo, timeout, unit);
    }

}
