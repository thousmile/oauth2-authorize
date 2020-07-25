package com.xaaef.authorize.server.util;

import com.xaaef.authorize.common.constant.DateTimeFormatConst;
import com.xaaef.authorize.common.constant.Oauth2ReturnStatus;
import com.xaaef.authorize.common.exception.Oauth2Exception;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

import java.security.Key;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * All rights Reserved, Designed By www.xaaef.com
 * <p>
 * </p>
 *
 * @author Wang Chen Chen<932560435@qq.com>
 * @version 1.0.0
 * @date 2020/7/259:20
 */


@Slf4j
public class JwtUtils {

    private static Key KEY = null;

    static {
        KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }

    public static String generate(String id, Integer validPeriod) {
        // 如果有效期时间，为空，就设置默认为 30 分钟
        if (validPeriod == null || validPeriod >= 0) {
            validPeriod = 1800;
        }
        var issuedAt = new Date();
        var expiration = new Date(issuedAt.getTime() + validPeriod * 1000);
        var format = new SimpleDateFormat(DateTimeFormatConst.DEFAULT_DATE_TIME_PATTERN);
        log.info("issuedAt:  {}   expiration:  {} ", format.format(issuedAt), format.format(expiration));
        return Jwts.builder()
                .setId(id)
                .setIssuedAt(issuedAt)
                .setExpiration(expiration)
                .signWith(KEY)
                .compact();
    }

    public static String generate(String id) {
        return generate(id, 1800);
    }

    private static Claims getClaimsToken(String token) {
        return Jwts.parserBuilder().setSigningKey(KEY).build().parseClaimsJws(token).getBody();
    }

    public static String getIdFromToken(String token) throws Oauth2Exception {
        Claims claims = getClaimsToken(token);
        if (claims.getExpiration().before(new Date())) {
            throw new Oauth2Exception(Oauth2ReturnStatus.ACCESS_TOKEN_EXPIRED);
        } else {
            return claims.getId();
        }
    }

}
