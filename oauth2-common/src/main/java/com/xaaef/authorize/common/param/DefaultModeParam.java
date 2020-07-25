package com.xaaef.authorize.common.param;

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
 * @author Wang Chen Chen<932560435@qq.com>
 * @version 1.0.0
 * @date 2020/7/2311:43
 */

@Getter
@Setter
@ToString
public class DefaultModeParam {

    /**
     * 授权类型
     *
     * @date 2019/1/12 14:03
     */
    @Pattern(regexp = "(authorization_code|password|implicit|client_credentials|tencent_qq|we_chat|mobile)",
            message = "授权类型，必须是[ authorization_code | password | implicit |" +
                    "client_credentials | tencent_qq | we_chat | mobile ]之一！")
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
