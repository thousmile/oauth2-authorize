package com.xaaef.authorize.common.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xaaef.authorize.common.enums.AdminFlag;
import com.xaaef.authorize.common.enums.GenderType;
import com.xaaef.authorize.common.enums.UserStatus;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * All rights Reserved, Designed By 深圳市铭灏天智能照明设备有限公司
 * <p>
 * 用户信息 详情
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
public class UserInfo implements Serializable {

    /**
     * 用户ID
     *
     * @author Wang Chen Chen
     * @date 2021/7/12 13:41
     */
    private Long userId;

    /**
     * 头像
     *
     * @author Wang Chen Chen
     * @date 2021/7/12 13:41
     */
    private String avatar;

    /**
     * 用户名
     *
     * @author Wang Chen Chen
     * @date 2021/7/12 13:41
     */
    private String username;

    /**
     * 手机号
     *
     * @author Wang Chen Chen
     * @date 2021/7/12 13:41
     */
    private String mobile;

    /**
     * 邮箱
     *
     * @author Wang Chen Chen
     * @date 2021/7/12 13:41
     */
    private String email;

    /**
     * 用户名称
     *
     * @author Wang Chen Chen
     * @date 2021/7/12 13:41
     */
    private String nickname;

    /**
     * 密码
     *
     * @author Wang Chen Chen
     * @date 2021/7/12 13:41
     */
    @JsonIgnore
    private String password;

    /**
     * 性别[ 0.女  1.男  2.未知]
     *
     * @author Wang Chen Chen
     * @date 2021/7/12 13:41
     */
    private GenderType gender;

    /**
     * 生日
     *
     * @author Wang Chen Chen
     * @date 2021/7/12 13:41
     */
    private LocalDate birthday;

    /**
     * 状态
     *
     * @author Wang Chen Chen
     * @date 2021/7/12 13:41
     */
    private UserStatus status;

    /**
     * 当前用户是不是管理员
     *
     * @author Wang Chen Chen
     * @date 2021/7/12 13:41
     */
    private AdminFlag adminFlag;


}
