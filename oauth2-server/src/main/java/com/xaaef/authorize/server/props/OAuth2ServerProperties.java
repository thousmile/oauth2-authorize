package com.xaaef.authorize.server.props;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * All rights Reserved, Designed By www.xaaef.com
 * <p>
 * </p>
 *
 * @author Wang Chen Chen<932560435@qq.com>
 * @version 1.0.0
 * @date 2020/7/2515:30
 */


@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ConfigurationProperties(prefix = "oauth2.server")
public class OAuth2ServerProperties {

    /**
     * token 过期时间，单位(秒)，默认 1 小时
     */
    private Integer tokenExpired = 3600;

    /**
     * refreshToken 过期时间，单位(秒)， 默认 3 小时
     */
    private Integer refreshTokenExpired = tokenExpired * 2;

    /**
     * 短信验证码过期时间 单位(秒)
     */
    private Integer smsCodeExpired = 600;

    /**
     * 用户被挤下线，提示的过期时间 单位(秒)
     */
    private Integer promptExpired = 600;

    /**
     * 授权码的 code 过期时间 单位(秒)
     */
    private Integer codeExpired = 600;

    /**
     * token 类型
     */
    private String tokenType = "Bearer ";

    /**
     * scope 类型
     */
    private String scope = "read,write";

    /**
     * token 在请求头中的名称
     */
    private String tokenHeader = "Authorization";

    /**
     * 秘钥
     */
    private String secret = "2N321lIkh$*!IfNt4&5!YZykD$7@ApaM8r@b@r@&4CZ7eqKe!s";

    /**
     * 单点登录，是否启用
     */
    private Boolean sso = Boolean.TRUE;

    /**
     * 需要排除的URL
     */
    private List<String> excludePath;

    /**
     * 腾讯QQ 开放平台 appId
     */
    private String qqAppId;

    /**
     * 腾讯QQ 开放平台 AppSecret
     */
    private String qqAppSecret;

    /**
     * 腾讯QQ 回调地址。如: http://authorize.xaaef.com/auth/tencent/qq/callback
     */
    private String qqCallback;

    /**
     * 微信 开放平台 appId
     */
    private String weChatAppId;

    /**
     * 微信 开放平台 AppSecret
     */
    private String weChatAppSecret;

    /**
     * 微信 回调地址。如: http://authorize.xaaef.com/auth/tencent/wechat/callback
     */
    private String wechatCallback;


}
