package com.xaaef.authorize.client;

import com.xaaef.authorize.client.props.OAuth2ClientProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.client.RestTemplate;

/**
 * All rights Reserved, Designed By 深圳市铭灏天智能照明设备有限公司
 * <p>
 * </p>
 *
 * @author Wang Chen Chen
 * @version 1.0
 * @date 2021/7/8 16:53
 * @copyright 2021 http://www.mhtled.com Inc. All rights reserved.
 */

@Slf4j
@Configuration
@EnableConfigurationProperties(OAuth2ClientProperties.class)
public class OAuth2ClientAutoConfiguration {

    @ConditionalOnMissingBean(RestTemplate.class)
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public AuthorizeService authorizeService(
            RestTemplate restTemplate,
            OAuth2ClientProperties props,
            RedisTemplate<String, Object> redisTemplate
    ) {
        return new AuthorizeServiceImpl(props, restTemplate, redisTemplate);
    }

}
