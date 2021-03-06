package com.xaaef.authorize.common.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.ToString;

/**
 * All rights Reserved, Designed By www.xaaef.com
 * <p>
 * 0. 租户用户
 * 1. 系统用户
 * </p>
 *
 * @author Wang Chen Chen
 * @version 1.0.1
 * @date 2021/7/12 13:53
 * @copyright 2021 http://www.xaaef.com Inc. All rights reserved.
 */

@Getter
@ToString
public enum GenderType {

    FEMALE(0, "女"),

    MALE(1, "男"),

    UNKNOWN(2, "未知");

    GenderType(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public static GenderType genderType(int code) {
        if (FEMALE.code == code) {
            return FEMALE;
        } else if (MALE.code == code) {
            return MALE;
        } else {
            return UNKNOWN;
        }
    }

    @JsonValue
    private final int code;

    private final String description;

}
