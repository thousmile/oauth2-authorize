package com.xaaef.authorize.common.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.ToString;

/**
 * All rights Reserved, Designed By www.xaaef.com
 * <p>
 * 状态 【0.禁用 1.正常 2.锁定 】
 * </p>
 *
 * @author Wang Chen Chen
 * @version 1.0.1
 * @date 2021/7/12 13:53
 * @copyright 2021 http://www.xaaef.com Inc. All rights reserved.
 */

@Getter
@ToString
public enum UserStatus {

    DISABLE(0, "禁用"),

    LOCKING(2, "锁定"),

    NORMAL(1, "正常");

    UserStatus(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public static UserStatus getUserStatus(int code) {
        if (DISABLE.code == code) {
            return DISABLE;
        } else if (NORMAL.code == code) {
            return NORMAL;
        } else {
            return LOCKING;
        }

    }

    @JsonValue
    private final int code;

    private final String description;

}
