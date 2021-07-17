package com.xaaef.authorize.common.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.ToString;

/**
 * All rights Reserved, Designed By www.xaaef.com
 * <p>
 * 0.第三方应用
 * 1.系统内部应用
 * </p>
 *
 * @author Wang Chen Chen
 * @version 1.0.1
 * @date 2021/7/5 9:31
 * @copyright 2021 http://www.xaaef.com Inc. All rights reserved.
 */

@Getter
@ToString
public enum ClientType {

    OTHER(0, "第三方应用"),

    SYSTEM(1, "系统内部应用");

    ClientType(int code, String description) {
        this.code = code;
        this.description = description;
    }

    @JsonValue
    private final int code;

    private final String description;

}
