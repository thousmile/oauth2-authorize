package com.xaaef.authorize.server.params;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * All rights Reserved, Designed By www.xaaef.com
 * <p>
 * 默认授权服务请求 参数
 * </p>
 *
 * @author Wang Chen Chen
 * @version 1.0.1
 * @date 2021/7/5 9:31
 * @copyright 2021 http://www.xaaef.com Inc. All rights reserved.
 */

@Getter
@Setter
@ToString
public class DefaultModeParam implements java.io.Serializable {

    /**
     * 授权类型
     *
     * @date 2019/1/12 14:03
     */
    @Pattern(regexp = "(authorization_code|password|client_credentials|tencent_qq|we_chat|sms)",
            message = "授权类型，必须是[ authorization_code | password | client_credentials | tencent_qq | we_chat | sms ]之一！")
    @NotBlank(message = "grant_type 必须填写！")
    @JsonProperty("grant_type")
    private String grantType;

    /**
     * 客户端ID
     *
     * @date 2019/1/12 14:03
     */
    @NotBlank(message = "client_id 必须填写！")
    @JsonProperty("client_id")
    private String clientId;

    /**
     * 客户端密钥
     *
     * @date 2020/7/23 13:54
     */
    @NotBlank(message = "client_secret 必须填写！")
    @JsonProperty("client_secret")
    private String clientSecret;

}
