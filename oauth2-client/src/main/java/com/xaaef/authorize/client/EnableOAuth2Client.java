package com.xaaef.authorize.client;

import com.xaaef.authorize.common.redis.RedisTemplateConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * All rights Reserved, Designed By www.xaaef.com
 * <p>
 * 启动 OAuth2.0 Client
 * </p>
 *
 * @author Wang Chen Chen
 * @version 1.0.1
 * @date 2021/7/13 11:54
 * @copyright 2021 http://www.xaaef.com Inc. All rights reserved.
 */


@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({
        OAuth2ClientAutoConfiguration.class,
        OAuth2ClientInterceptor.class,
        RedisTemplateConfiguration.class
})
public @interface EnableOAuth2Client {

}
