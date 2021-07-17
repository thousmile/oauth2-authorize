package com.xaaef.authorize.common.token;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

/**
 * All rights Reserved, Designed By 深圳市铭灏天智能照明设备有限公司
 * <p>
 * OAuth2.0 token 返回
 * </p>
 *
 * @author Wang Chen Chen
 * @version 1.0.1
 * @date 2021/7/5 9:31
 * @copyright 2021 http://www.mhtled.com Inc. All rights reserved.
 */

@Getter
@Setter
@ToString
@Builder
@EqualsAndHashCode
public class OAuth2Token {

    /**
     * 表示访问令牌，必选项。
     *
     * @author Wang Chen Chen<932560435@qq.com>
     * @date 2020/7/25 15:02
     */
    @JsonProperty("access_token")
    private String accessToken;

    /**
     * 表示令牌类型，该值大小写不敏感，必选项，可以是bearer类型或mac类型。
     *
     * @author Wang Chen Chen<932560435@qq.com>
     * @date 2020/7/25 15:02
     */
    @JsonProperty("token_type")
    private String tokenType;

    /**
     * 表示过期时间，单位为秒。如果省略该参数，必须其他方式设置过期时间。
     *
     * @author Wang Chen Chen<932560435@qq.com>
     * @date 2020/7/25 15:02
     */
    @JsonProperty("expires_in")
    private Integer expiresIn;

    /**
     * 表示更新令牌，用来获取下一次的访问令牌，可选项。
     *
     * @author Wang Chen Chen<932560435@qq.com>
     * @date 2020/7/25 15:02
     */
    @JsonProperty("refresh_token")
    private String refreshToken;

    /**
     * 表示权限范围，如果与客户端申请的范围一致，此项可省略。
     *
     * @author Wang Chen Chen<932560435@qq.com>
     * @date 2020/7/25 15:02
     */
    @JsonProperty("scope")
    private String scope;

}
