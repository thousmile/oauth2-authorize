package com.xaaef.authorize.common.param;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.URL;

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
public class GetCodeModeParam {

    /**
     * 表示授权类型，必选项，此处的值固定为"code"
     *
     * @date 2019/1/12 14:03
     */
    @Pattern(regexp = "(code)", message = "授权类型，必须是[ code ]")
    @NotBlank(message = "response_type 必须填写！")
    private String responseType;

    /**
     * 表示客户端的ID，必选项
     *
     * @date 2019/1/12 14:03
     */
    @NotBlank(message = "client_id 必须填写！")
    private String clientId;

    /**
     * 表示重定向URI
     *
     * @date 2020/7/23 13:49
     */
    @NotBlank(message = "redirect_uri 必须填写！")
    @URL(message = "回调URL格式不正确！")
    private String redirectUri;

    /**
     * 表示申请的权限范围，可选项，
     * user_base : 只可以获取用户的 基本信息
     * user_info  ：获取用户的全部信息
     *
     * @date 2020/7/23 13:49
     */
    private String scope = "user_base";

    /**
     * 表示客户端的当前状态，可以指定任意值，认证服务器会原封不动地返回这个值。
     *
     * @date 2020/7/23 13:49
     */
    private String state;

}
