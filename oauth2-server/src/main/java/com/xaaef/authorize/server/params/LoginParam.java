package com.xaaef.authorize.server.params;

import lombok.*;

import javax.validation.constraints.NotBlank;

/**
 * All rights Reserved, Designed By www.xaaef.com
 * <p>
 * 用户登录参数
 * </p>
 *
 * @author Wang Chen Chen<932560435@qq.com>
 * @version 1.0.0
 * @date 2020/7/2316:40
 */

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class LoginParam {

    /**
     * 一个随机的 授权ID，由系统随机生成，绑定给每个前来授权的第三方应用
     *
     * @date 2020/7/23 13:54
     */
    private String codeId;

    /**
     * 用户名
     *
     * @date 2020/7/23 13:54
     */
    @NotBlank(message = "username 必须填写！")
    private String username;

    /**
     * 密码
     *
     * @date 2020/7/23 13:54
     */
    @NotBlank(message = "password 必须填写！")
    private String password;

    /**
     * 验证码 key
     *
     * @date 2020/7/23 13:54
     */
    @NotBlank(message = "codeKey 必须填写！")
    private String codeKey;

    /**
     * 验证码 值
     *
     * @date 2020/7/23 13:54
     */
    @NotBlank(message = "codeText 必须填写！")
    private String codeText;

}
