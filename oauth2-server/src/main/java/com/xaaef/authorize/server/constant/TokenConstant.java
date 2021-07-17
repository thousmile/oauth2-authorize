package com.xaaef.authorize.server.constant;

/**
 * All rights Reserved, Designed By www.xaaef.com
 * <p>
 * 登录字段
 * </p>
 *
 * @author Wang Chen Chen
 * @version 1.0
 * @date 2021/7/5 9:31
 * @copyright 2021 http://www.xaaef.com Inc. All rights reserved.
 */

public class TokenConstant {

    /**
     * 验证码 redis key
     */
    public static final String CAPTCHA_CODE_KEY = "captcha_codes:";

    /**
     * 登录用户 redis key
     */
    public static final String LOGIN_TOKEN_KEY = "login_tokens:";

    /**
     * refresh tokens
     */
    public static final String REFRESH_TOKEN_KEY = "refresh_tokens:";

    /**
     * 在线用户，令牌前缀
     */
    public static final String ONLINE_USER_KEY = "online_user:";

    /**
     * 强制下线，令牌前缀
     */
    public static final String FORCED_OFFLINE_KEY = "forced_offline_user:";

    /**
     * 短信验证码， 令牌前缀
     */
    public static final String SMS_KEY = "sms_codes:";

    /**
     * 腾讯qq和微信 state字段 令牌前缀
     */
    public static final String TENCENT_KEY = "tencent_state:";

    /**
     * 客户端
     */
    public static final String CLIENT_KEY = "clients:";

    /**
     * 授权码模式 code 参数
     */
    public static final String CODE_MODE_KEY = "code_mode:";

    /**
     * 授权码模式 返回给第三方应用的 code 关联登录用户的信息
     */
    public static final String CODE_KEY = "code:";

    /**
     * 授权码模式 当用户登录成功后
     */
    public static final String CODE_CLIENT_ID_KEY = "code_clientId:";


}
