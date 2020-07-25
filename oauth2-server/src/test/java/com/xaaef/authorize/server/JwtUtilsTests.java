package com.xaaef.authorize.server;

import com.xaaef.authorize.common.exception.Oauth2Exception;
import com.xaaef.authorize.server.util.JwtUtils;

/**
 * All rights Reserved, Designed By www.xaaef.com
 * <p>
 * </p>
 *
 * @author Wang Chen Chen<932560435@qq.com>
 * @version 1.0.0
 * @date 2020/7/2516:25
 */
public class JwtUtilsTests {

    public static void main(String[] args) throws Oauth2Exception {
        String token = JwtUtils.generate("dwwwwwwwwwww");
        System.out.println(token);
        String id = JwtUtils.getIdFromToken(token);
        System.out.println(id);
    }

}
