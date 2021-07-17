package com.xaaef.authorize.server;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * All rights Reserved, Designed By www.xaaef.com
 * <p>
 * </p>
 *
 * @author Wang Chen Chen<932560435@qq.com>
 * @version 1.0.0
 * @date 2020/7/2317:17
 */

public class UriTests {

    public static final PasswordEncoder be = new BCryptPasswordEncoder();

    public static void main(String[] args) {
        System.out.println(be.encode("admin"));
    }

}
