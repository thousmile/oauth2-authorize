package com.xaaef.authorize.common.constant;

/**
 * All rights Reserved, Designed By www.xaaef.com
 * <p>
 * 认证类型
 * </p>
 *
 * @author Wang Chen Chen<932560435@qq.com>
 * @version 1.0.0
 * @date 2020/7/2311:45
 */

public class GrantTypeConst {

    /**
     * 授权码模式: 第一步
     *
     * @date 2019/1/5 11:18
     */
    public final static String CODE = "code";


    /**
     * 授权码模式
     *
     * @date 2019/1/5 11:18
     */
    public final static String AUTHORIZATION_CODE = "authorization_code";


    /**
     * 密码模式
     *
     * @date 2019/1/5 11:18
     */
    public final static String PASSWORD = "password";


    /**
     * 简化模式
     *
     * @date 2019/1/5 11:18
     */
    public static final String IMPLICIT = "implicit";


    /**
     * 客户端模式
     *
     * @date 2019/1/5 11:18
     */
    public static final String CLIENT_CREDENTIALS = "client_credentials";


    /**
     * 刷新token
     *
     * @date 2019/1/5 11:18
     */
    public final static String REFRESH_TOKEN = "refresh_token";

    /**
     * 微信登陆模式
     *
     * @date 2019/1/5 11:18
     */
    public static final String WE_CHAT = "we_chat";

    /**
     * 腾讯 QQ 登陆模式
     *
     * @date 2019/1/5 11:18
     */
    public static final String TENCENT_QQ = "tencent_qq";


    /**
     * 手机验证码登陆模式
     *
     * @date 2019/1/5 11:18
     */
    public static final String MOBILE = "mobile";

}
