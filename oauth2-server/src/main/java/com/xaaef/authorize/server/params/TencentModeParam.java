package com.xaaef.authorize.server.params;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;


/**
 * All rights Reserved, Designed By www.xaaef.com
 * <p>
 * 密码模式 授权服务请求 参数
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
public class TencentModeParam extends DefaultModeParam {

    /**
     * 授权成功后，回调URL
     *
     * @date 2020/7/23 13:54
     */
    @NotBlank(message = "授权成功后，回调URL！")
    private String callbackUrl;


}
