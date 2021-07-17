package com.xaaef.authorize.common.util;

import com.xaaef.authorize.common.domain.ClientDetails;
import com.xaaef.authorize.common.domain.TokenValue;
import com.xaaef.authorize.common.domain.UserInfo;
import com.xaaef.authorize.common.enums.GrantType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import java.util.List;

/**
 * All rights Reserved, Designed By www.xaaef.com
 * <p>
 * 安全服务工具类
 * </p>
 *
 * @author Wang Chen Chen
 * @version 1.0
 * @date 2021/7/5 10:50
 * @copyright 2021 http://www.xaaef.com Inc. All rights reserved.
 */

public class SecurityUtils {

    private final static PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();

    private final static ThreadLocal<TokenValue> TOKEN_THREAD_LOCAL = new InheritableThreadLocal<>();

    public static PasswordEncoder getPasswordEncoder() {
        return PASSWORD_ENCODER;
    }

    /**
     * 获取 tokenId
     **/
    public static String getTokenId() {
        return getTokenValue().getTokenId();
    }

    /**
     * 获取 客户端详情
     **/
    public static ClientDetails getClient() {
        return getTokenValue().getClient();
    }

    /**
     * 获取 用户信息
     **/
    public static UserInfo getUserInfo() {
        return getTokenValue().getUser();
    }

    /**
     * 获取 token
     **/
    public static TokenValue getTokenValue() {
        try {
            return TOKEN_THREAD_LOCAL.get();
        } catch (Exception e) {
            throw new RuntimeException("获取用户信息异常");
        }
    }

    /**
     * 设置登录成功的 token
     **/
    public static void setTokenValue(TokenValue token) {
        TOKEN_THREAD_LOCAL.set(token);
    }

    /**
     * 生成BCryptPasswordEncoder密码
     *
     * @param password 密码
     * @return 加密字符串
     */
    public static String encryptPassword(String password) {
        return PASSWORD_ENCODER.encode(password);
    }

    /**
     * 判断密码是否相同
     *
     * @param rawPassword     真实密码
     * @param encodedPassword 加密后字符
     * @return 结果
     */
    public static boolean matchesPassword(String rawPassword, String encodedPassword) {
        return PASSWORD_ENCODER.matches(rawPassword, encodedPassword);
    }

    // spring 路径匹配工具
    private static final PathMatcher PATH_MATCHER = new AntPathMatcher();

    /**
     * @param ignoreUrls 被忽略的url列表
     * @param url        需要验证的 url 地址
     * @author Wang Chen Chen
     * @date 2021/7/9 10:14
     */
    public static boolean urlIgnore(List<String> ignoreUrls, String url) {
        for (String pattern : ignoreUrls) {
            if (PATH_MATCHER.match(pattern, url)) {
                return true;
            }
        }
        return false;
    }


}
