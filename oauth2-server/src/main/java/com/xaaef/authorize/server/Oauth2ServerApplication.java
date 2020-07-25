package com.xaaef.authorize.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

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
@MapperScan(basePackages = "com.xaaef.authorize.server.mapper")
public class Oauth2ServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(Oauth2ServerApplication.class, args);
    }


}
