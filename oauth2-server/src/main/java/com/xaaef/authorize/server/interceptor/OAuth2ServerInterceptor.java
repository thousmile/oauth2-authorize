package com.xaaef.authorize.server.interceptor;

import com.xaaef.authorize.common.domain.TokenValue;
import com.xaaef.authorize.common.enums.OAuth2Error;
import com.xaaef.authorize.common.exception.OAuth2Exception;
import com.xaaef.authorize.common.util.JsonResult;
import com.xaaef.authorize.common.util.JsonUtils;
import com.xaaef.authorize.common.util.SecurityUtils;
import com.xaaef.authorize.server.props.OAuth2ServerProperties;
import com.xaaef.authorize.server.service.TokenService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * <p>
 * OAuth2 认证拦截器
 * </p>
 *
 * @author Wang Chen Chen<932560435@qq.com>
 * @version 1.0
 * @createTime 2020/5/30 0030 17:25
 */

@Slf4j
@Component
@AllArgsConstructor
public class OAuth2ServerInterceptor implements HandlerInterceptor {

    private TokenService tokenService;

    private OAuth2ServerProperties props;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 此路径忽略
        if (tokenService.urlIgnore(request.getRequestURI())) {
            return true;
        } else {
            String bearerToken = request.getHeader(props.getTokenHeader());
            if (StringUtils.isNotEmpty(bearerToken)) {
                try {
                    // 校验 token
                    TokenValue tokenValue = tokenService.validate(bearerToken);
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
