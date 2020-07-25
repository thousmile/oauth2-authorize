package com.xaaef.authorize.server.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * All rights Reserved, Designed By www.ifsaid.com
 * <p>
 * 后台系统角色
 * </p>
 *
 * @author Wang Chen Chen <932560435@qq.com>
 * @version 2.0
 * @date 2019/4/18 11:45
 * @copyright 2019 http://www.ifsaid.com/ Inc. All rights reserved.
 */

@Table(name = "client_details")
@Entity
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ClientDetails implements java.io.Serializable {

    /**
     * Client Id
     *
     * @date 2019/12/11 22:15
     */
    @Id
    private String clientId;

    /**
     * 客户端 密钥
     *
     * @date 2019/12/11 22:15
     */
    private String secret;

    /**
     * 客户端名称
     *
     * @date 2019/12/11 22:15
     */
    private String name;

    /**
     * 客户端 图标
     *
     * @date 2019/12/11 22:15
     */
    private String logoUrl;

    /**
     * 描述
     *
     * @date 2019/12/11 22:15
     */
    private String description;

    /**
     * 客户端 类型
     *
     * @date 2019/12/11 22:15
     */
    private String clientType;

    /**
     * 授权类型 数组格式 []
     *
     * @date 2019/12/11 22:15
     */
    private String grantTypes;

    /**
     * 域名地址
     *
     * @date 2019/12/11 22:15
     */
    private String domainName;

    /**
     * 授权作用域
     *
     * @date 2019/12/11 22:15
     */
    private String scope;

    /**
     * 状态： 0.禁用  1.正常  2.被删除
     *
     * @date 2019/12/11 22:15
     */
    private Integer status;

    /**
     * 创建时间
     *
     * @date 2019/12/11 21:12
     */
    private LocalDateTime createTime;

    /**
     * 最后一次修改时间
     *
     * @date 2019/12/11 21:12
     */
    private LocalDateTime lastUpdateTime;

}
