package com.xaaef.authorize.client;

import com.xaaef.authorize.client.props.OAuth2ClientProperties;
import com.xaaef.authorize.common.domain.TokenValue;
import com.xaaef.authorize.common.exception.OAuth2Exception;

/**
 * All rights Reserved, Designed By www.xaaef.com
 * <p>
 * 授权检查
 * </p>
 *
 * @author Wang Chen Chen
 * @version 1.0.1
 * @date 2021/7/13 13:41
 * @copyright 2021 http://www.xaaef.com Inc. All rights reserved.
 */

public interface AuthorizeService {

    /**
     * 校验 token
     * <p>
     * token 格式
     * Bearer eyJhbGciOiJIUzUxMiJ9.eyJqdGkiOiI4NDRkODg4Njky6IjTIifQ.ihVWv89d9zdFHbBG1lf6fA
     *
     * @param jwtToken
     * @author Wang Chen Chen
     * @date 2021/7/12 16:29
     */
    TokenValue validate(String jwtToken) throws OAuth2Exception;

    /**
     * 根据 tokenId 获取 TokenValue
     * <p>
     *
     * @param tokenId
     * @author Wang Chen Chen
     * @date 2021/7/12 16:29
     */
    TokenValue get(String tokenId);

    /**
     * 判断 url 是否过滤，根据 oauth2.client.exclude-path
     *
     * @param url
     * @param method
     * @return boolean
     * @author Wang Chen Chen
     * @date 2021/7/13 10:30
     */
    boolean urlIgnore(String url);

    /**
     * 获取客户端配置
     *
     * @return boolean
     * @author Wang Chen Chen
     * @date 2021/7/13 10:30
     */
    OAuth2ClientProperties getProps();

}
