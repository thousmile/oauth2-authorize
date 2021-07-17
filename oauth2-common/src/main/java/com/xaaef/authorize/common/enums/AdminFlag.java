package com.xaaef.authorize.common.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.ToString;

/**
 * All rights Reserved, Designed By 深圳市铭灏天智能照明设备有限公司
 * <p>
 * 0.租户用户
 * 1. 系统用户
 * </p>
 *
 * @author Wang Chen Chen
 * @version 1.0.1
 * @date 2021/7/12 13:53
 * @copyright 2021 http://www.mhtled.com Inc. All rights reserved.
 */

@Getter
@ToString
public enum AdminFlag {

    NO(0, "非管理员"),

    YES(1, "是管理员");

    AdminFlag(int code, String description) {
        this.code = code;
        this.description = description;
    }

    @JsonValue
    private final int code;

    private final String description;

}
