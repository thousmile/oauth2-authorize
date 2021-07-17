package com.xaaef.authorize.client;

import com.xaaef.authorize.client.props.OAuth2ClientProperties;
import com.xaaef.authorize.common.domain.TokenValue;
import com.xaaef.authorize.common.enums.GrantType;
import com.xaaef.authorize.common.enums.OAuth2Error;
import com.xaaef.authorize.common.exception.OAuth2Exception;
import com.xaaef.authorize.common.util.JsonResult;
import com.xaaef.authorize.common.util.JsonUtils;
import com.xaaef.authorize.common.util.SecurityUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * All rights Reserved, Designed By www.xaaef.com
 * <p>
 * 只有 使用了 spring mvc 才会注入，这个类
 * </p>
 *
 * @author Wang Chen Chen
 * @version 1.0.1
 * @date 2021/7/13 17:29
 * @copyright 2021 http://www.xaaef.com Inc. All rights reserved.
 */

@Slf4j
@Configuration
@AllArgsConstructor
@ConditionalOnClass(org.springframework.web.servlet.HandlerInterceptor.class)
public class OAuth2ClientInterceptor implements HandlerInterceptor, WebMvcConfigurer {

    private AuthorizeService authorizeService;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(this)
                .addPathPatterns("/**");
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 此路径忽略
        if (authorizeService.urlIgnore(request.getRequestURI())) {
            return true;
        } else {
            OAuth2ClientProperties props = authorizeService.getProps();
            String bearerToken = request.getHeader(props.getTokenHeader());
            if (StringUtils.hasText(bearerToken)) {
                try {
                    // 校验 token
                    TokenValue tokenValue = authorizeService.validate(bearerToken);
                    // 判断 token 的认证方式，如果是 客户端模式，那么就没有用户信息。
                    if (props.isUserAuth() && tokenValue.getGrantType() == GrantType.CLIENT_CREDENTIALS) {
                        error(response, new OAuth2Exception(OAuth2Error.TOKEN_USER_INVALID));
                        return false;
                    }
                    // 将 TokenValue 设置到 ThreadLocal 中！
                    SecurityUtils.setTokenValue(tokenValue);
                    return true;
                } catch (OAuth2Exception e) {
                    error(response, e);
                }
            } else {
                error(response, new OAuth2Exception(OAuth2Error.ACCESS_TOKEN_INVALID));
            }
        }
        return false;
    }

    /**
     * 将字符串渲染到客户端
     *
     * @param response 渲染对象
     * @param ex       待渲染的字符串
     * @return null
     */
    private static String error(HttpServletResponse response, OAuth2Exception ex) {
        try {
            response.setStatus(HttpStatus.OK.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());
            String json = JsonUtils.toJson(JsonResult.error(ex.getStatus(), ex.getMessage()));
            response.getWriter().print(json);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return null;
    }

}
