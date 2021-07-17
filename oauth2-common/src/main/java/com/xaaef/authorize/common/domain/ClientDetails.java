package com.xaaef.authorize.common.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xaaef.authorize.common.enums.ClientType;
import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
 * All rights Reserved, Designed By www.xaaef.com
 * <p>
 * 客户端 详情
 * </p>
 *
 * @author Wang Chen Chen
 * @version 1.0.1
 * @date 2021/7/12 12:08
 * @copyright 2021 http://www.xaaef.com Inc. All rights reserved.
 */

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClientDetails implements Serializable {

    /**
     * 客户端 唯一认证 Id
     *
     * @author Wang Chen Chen
     * @date 2021/7/12 13:41
     */
    private String clientId;

    /**
     * 客户端 密钥
     *
     * @author Wang Chen Chen
     * @date 2021/7/12 13:45
     */
    @JsonIgnore
    private String secret;

    /**
     * '客户端名称'
     *
     * @author Wang Chen Chen
     * @date 2021/7/12 13:45
     */
    private String name;

    /**
     * 客户端 图标
     *
     * @author Wang Chen Chen
     * @date 2021/7/12 13:45
     */
    private String logo;

    /**
     * 客户端 描述
     *
     * @author Wang Chen Chen
     * @date 2021/7/12 13:45
     */
    private String description;

    /**
     * 客户端类型
     *
     * @author Wang Chen Chen
     * @date 2021/7/12 13:45
     */
    private ClientType clientType;

    /**
     * 授权类型 数组格式
     *
     * @author Wang Chen Chen
     * @date 2021/7/12 13:45
     */
    private List<String> grantTypes;

    /**
     * 域名地址，如果是 授权码模式，
     * 必须校验回调地址是否属于此域名之下
     *
     * @author Wang Chen Chen
     * @date 2021/7/12 13:45
     */
    private String domainName;

    /**
     * 授权作用域
     *
     * @author Wang Chen Chen
     * @date 2021/7/12 13:45
     */
    private String scope;

    public boolean containsGrantType(String grantType) {
        if (getGrantTypes() == null || getGrantTypes().size() < 1) {
            return false;
        }
        // 如果 包含 * 。说明拥有所有的认证类型
        if (getGrantTypes().contains("*")) {
            return true;
        }
        // 判断 GrantTypes 是否包含 grantType 认证类型
        if (getGrantTypes().contains(grantType)) {
            return true;
        }
        return false;
    }

}
