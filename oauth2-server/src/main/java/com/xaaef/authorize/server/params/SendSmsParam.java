package com.xaaef.authorize.server.params;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * All rights Reserved, Designed By www.xaaef.com
 * <p>
 * 发送手机短信验证码 参数
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
@EqualsAndHashCode
public class SendSmsParam implements java.io.Serializable {

    /**
     * 客户端ID
     *
     * @date 2019/1/12 14:03
     */
    @NotBlank(message = "client_id 必须填写！")
    @JsonProperty("client_id")
    private String clientId;

    /**
     * 登陆的用户手机号
     *
     * @date 2020/7/23 13:54
     */
    @NotBlank(message = "手机号必须填写！")
    @Pattern(regexp = "^[1][3,4,5,7,8,9][0-9]{9}$", message = "手机号格式不正确！")
    private String mobile;

}
