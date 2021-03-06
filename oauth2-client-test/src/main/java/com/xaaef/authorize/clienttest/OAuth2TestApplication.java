package com.xaaef.authorize.clienttest;

import com.xaaef.authorize.client.EnableOAuth2Client;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * All rights Reserved, Designed By www.xaaef.com
 * <p>
 * </p>
 *
 * @author Wang Chen Chen
 * @version 1.0.1
 * @date 2021/7/13 14:51
 * @copyright 2021 http://www.xaaef.com Inc. All rights reserved.
 */

@EnableOAuth2Client
@SpringBootApplication
public class OAuth2TestApplication {

    public static void main(String[] args) {
        SpringApplication.run(OAuth2TestApplication.class, args);
    }

}
