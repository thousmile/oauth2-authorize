package com.xaaef.authorize.client.props;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * All rights Reserved, Designed By www.xaaef.com
 * <p>
 * </p>
 *
 * @author Wang Chen Chen<932560435@qq.com>
 * @version 1.0.0
 * @date 2020/7/2515:30
 */


@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ConfigurationProperties(prefix = "oauth2.client")
public class OAuth2ClientProperties {

    /**
     * 客户端 token 缓存 过期时间  单位(秒)
     */
    private Integer tokenCacheExpired = 60;

    /**
     * token 在请求头中的名称
     */
    private String tokenHeader = "Authorization";

    /**
     * token 在请求头中的名称
     */
    private String tokenIdHeader = "TokenId";

    /**
     * token 类型
     */
    private String tokenType = "Bearer ";

    /**
     * 是否需要，验证用户。
     */
    private boolean userAuth = true;

    /**
     * 认证 服务器 地址
     */
    private String serverUrl = "http://localhost:8098/loginInfo";

    /**
     * 需要排除的URL
     */
    private List<String> excludePath;

}
