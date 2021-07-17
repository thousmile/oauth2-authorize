package com.xaaef.authorize.common.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.ToString;

/**
 * All rights Reserved, Designed By 深圳市铭灏天智能照明设备有限公司
 * <p>
 * code : 授权码模式: 第一步
 * authorization_code : 授权码模式
 * password : 密码模式
 * client_credentials : 客户端模式
 * refresh_token : 刷新token
 * we_chat : 微信登陆模式
 * tencent_qq : 腾讯 QQ 登陆模式
 * sms : 手机短信模式
 * </p>
 *
 * @author Wang Chen Chen
 * @version 1.0.1
 * @date 2021/7/5 9:31
 * @copyright 2021 http://www.mhtled.com Inc. All rights reserved.
 */

@Getter
@ToString
public enum GrantType {

    CODE("code", "授权码模式，1.获取code"),

    AUTHORIZATION_CODE("authorization_code", "授权码模式，2.通过code获取access_token"),

    PASSWORD("password", "密码模式"),

    CLIENT_CREDENTIALS("client_credentials", "客户端模式"),

    WE_CHAT("we_chat", "微信登陆模式"),

    TENCENT_QQ("tencent_qq", "腾讯 QQ 登陆模式"),

    SMS("sms", "手机短信模式");

    GrantType(String code, String description) {
        this.code = code;
        this.description = description;
    }

    @JsonValue
    private final String code;

    private final String description;

}
