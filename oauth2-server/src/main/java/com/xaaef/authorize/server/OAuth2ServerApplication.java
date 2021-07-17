package com.xaaef.authorize.server;

import com.xaaef.authorize.common.redis.RedisTemplateConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

/**
 * All rights Reserved, Designed By www.xaaef.com
 * <p>
 * Spring Boot 启动类
 * </p>
 *
 * @author Wang Chen Chen <932560435@qq.com>
 * @version 1.0.0
 * @date 2019/4/18 11:45
 */

@SpringBootApplication
@Import(RedisTemplateConfiguration.class)
public class OAuth2ServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(OAuth2ServerApplication.class, args);
    }


}
