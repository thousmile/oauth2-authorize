package com.xaaef.authorize.server.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * All rights Reserved, Designed By www.ifsaid.com
 * <p>
 * 用户
 * </p>
 *
 * @author Wang Chen Chen <932560435@qq.com>
 * @version 2.0
 * @date 2019/4/18 11:45
 * @copyright 2019 http://www.ifsaid.com/ Inc. All rights reserved.
 */

@Table(name = "user_info")
@Entity
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class UserInfo implements java.io.Serializable {

    /**
     * 用户唯一id
     *
     * @date: 2019/12/11 22:15
     */
    @Id
    private String userId;

    /**
     * 用户名唯一
     */
    private String username;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 昵称
     *
     * @date: 2019/12/11 22:15
     */
    private String nickname;

    /**
     * 密码
     *
     * @date: 2019/12/11 22:15
     */
    private String password;

    /**
     * 头像
     *
     * @date: 2019/12/11 22:15
     */
    private String avatar;

    /**
     * 性别。0.女，1.男性，2.未知
     *
     * @date: 2019/12/11 22:15
     */
    private Integer gender;

    /**
     * 地址
     */
    private String address;

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
