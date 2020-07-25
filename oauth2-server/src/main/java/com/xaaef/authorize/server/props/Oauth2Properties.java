package com.xaaef.authorize.server.props;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

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
@Component
@ConfigurationProperties(prefix = "oauth2")
public class Oauth2Properties {

    /**
     * token 过期时间，单位 秒 ，默认 2 小时
     */
    private Integer tokenExpired = 7200;


    /**
     * refreshToken 过期时间，单位 秒 , 默认 1 天
     */
    private Integer refreshTokenExpired = 86400;


    /**
     * token 类型
     */
    private String tokenType = "Bearer ";

    /**
     * token 在请求头中的名称
     */
    private String tokenHeader = "Authorization";

    /**
     * 需要排除的URL
     */
    private List<String> excludePath;

}
