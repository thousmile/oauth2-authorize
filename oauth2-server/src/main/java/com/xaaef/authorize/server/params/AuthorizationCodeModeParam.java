package com.xaaef.authorize.server.params;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

/**
 * All rights Reserved, Designed By www.xaaef.com
 * <p>
 * 授权码模式 授权服务请求 参数
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
public class AuthorizationCodeModeParam extends DefaultModeParam {

    /**
     * 授权码 的值
     *
     * @date 2020/7/23 13:54
     */
    @NotBlank(message = "code 必须填写！")
    private String code;

}
