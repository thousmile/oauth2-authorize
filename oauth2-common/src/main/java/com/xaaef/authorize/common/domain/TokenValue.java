package com.xaaef.authorize.common.domain;

import com.xaaef.authorize.common.enums.GrantType;
import lombok.*;

import java.io.Serializable;

/**
 * All rights Reserved, Designed By 深圳市铭灏天智能照明设备有限公司
 * <p>
 * 登录成功的客户端，用户信息
 * </p>
 *
 * @author Wang Chen Chen
 * @version 1.0.1
 * @date 2021/7/12 12:08
 * @copyright 2021 http://www.mhtled.com Inc. All rights reserved.
 */

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TokenValue implements Serializable {

    /**
     * 全局唯一认证 Id
     *
     * @author Wang Chen Chen
     * @date 2021/7/12 13:41
     */
    private String tokenId;

    /**
     * 认证授权方式
     *
     * @author Wang Chen Chen
     * @date 2021/7/12 13:41
     */
    private GrantType grantType;

    /**
     * 用户信息
     *
     * @author Wang Chen Chen
     * @date 2021/7/12 13:41
     */
    private UserInfo user;

    /**
     * 客户端信息
     *
     * @author Wang Chen Chen
     * @date 2021/7/12 13:41
     */
    private ClientDetails client;

}
