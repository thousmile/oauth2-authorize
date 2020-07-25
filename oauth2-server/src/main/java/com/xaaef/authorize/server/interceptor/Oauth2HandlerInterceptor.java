package com.xaaef.authorize.server.interceptor;

import com.xaaef.authorize.common.constant.Oauth2ReturnStatus;
import com.xaaef.authorize.common.exception.Oauth2Exception;
import com.xaaef.authorize.common.util.JsonResult;
import com.xaaef.authorize.common.util.JsonUtils;
import com.xaaef.authorize.server.props.Oauth2Properties;
import com.xaaef.authorize.server.util.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 *
 * </p>
 *
 * @author Wang Chen Chen<932560435@qq.com>
 * @version 1.0
 * @createTime 2020/5/30 0030 17:25
 */

@Slf4j
public class Oauth2HandlerInterceptor extends HandlerInterceptorAdapter {

    private Oauth2Properties props;

    public Oauth2HandlerInterceptor(Oauth2Properties props) {
        this.props = props;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String headerValue = request.getHeader(props.getTokenHeader());
        response.setCharacterEncoding("utf-8");
        response.setHeader("content-type", "application/json;charset=utf-8");
        if (StringUtils.isNotEmpty(headerValue)) {
            // 去除前缀，获取 token 的值
            String tokenValue = headerValue.substring(props.getTokenType().length());
            try {
                String userId = JwtUtils.getIdFromToken(tokenValue);
                request.setAttribute("userId", userId);
                return super.preHandle(request, response, handler);
            } catch (Oauth2Exception e) {
                response.getWriter().write(
                        JsonUtils.toJson(JsonResult.result(e.getStatus(), e.getMessage()))
                );
            } catch (Exception e) {
                response.getWriter().write(
                        JsonUtils.toJson(
                                JsonResult.result(Oauth2ReturnStatus.ACCESS_TOKEN_INVALID.getStatus(), e.getMessage())
                        )
                );
                return false;
            }
        }
        response.getWriter().write(
                JsonUtils.toJson(
                        JsonResult.result(
                                Oauth2ReturnStatus.ACCESS_TOKEN_INVALID.getStatus(),
                                Oauth2ReturnStatus.ACCESS_TOKEN_INVALID.getError()
                        )
                )
        );
        return false;
    }


}
