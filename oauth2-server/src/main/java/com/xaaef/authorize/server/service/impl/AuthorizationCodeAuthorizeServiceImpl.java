package com.xaaef.authorize.server.service.impl;

import com.xaaef.authorize.common.domain.ClientDetails;
import com.xaaef.authorize.common.domain.TokenValue;
import com.xaaef.authorize.common.domain.UserInfo;
import com.xaaef.authorize.common.enums.GrantType;
import com.xaaef.authorize.common.enums.OAuth2Error;
import com.xaaef.authorize.common.exception.OAuth2Exception;
import com.xaaef.authorize.common.token.OAuth2Token;
import com.xaaef.authorize.server.params.AuthorizationCodeModeParam;
import com.xaaef.authorize.server.params.GetCodeModeParam;
import com.xaaef.authorize.server.params.LoginParam;
import com.xaaef.authorize.server.props.OAuth2ServerProperties;
import com.xaaef.authorize.server.repository.ClientDetailsRepository;
import com.xaaef.authorize.server.repository.UserInfoRepository;
import com.xaaef.authorize.server.service.AuthorizationCodeAuthorizeService;
import com.xaaef.authorize.server.service.TokenCacheService;
import com.xaaef.authorize.server.util.JwtTokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

import static com.xaaef.authorize.server.constant.TokenConstant.*;

/**
 * All rights Reserved, Designed By www.xaaef.com
 * <p>
 * </p>
 *
 * @author Wang Chen Chen<932560435@qq.com>
 * @version 1.0.1
 * @date 2021/7/17 16:49
 * @copyright 2021 http://www.xaaef.com Inc. All rights reserved.
 */

@Slf4j
@Service
public class AuthorizationCodeAuthorizeServiceImpl extends DefaultAuthorizeService implements AuthorizationCodeAuthorizeService {

    public AuthorizationCodeAuthorizeServiceImpl(
            ClientDetailsRepository clientDetailsRepository,
            UserInfoRepository userInfoRepository,
            TokenCacheService tokenCacheService,
            JwtTokenUtils jwtTokenUtils) {
        super(clientDetailsRepository, userInfoRepository, tokenCacheService, jwtTokenUtils);
    }


    @Override
    public String validateClient(GetCodeModeParam param) throws OAuth2Exception {
        ClientDetails client = getClient(param.getClientId());
        // 判断 客户端 是否存在
        if (client == null) {
            throw new OAuth2Exception(OAuth2Error.CLIENT_INVALID);
        }
        // 校验授权方式,是否 code
        if (!StringUtils.equalsIgnoreCase(GrantType.CODE.getCode(), param.getResponseType())) {
            throw new OAuth2Exception(OAuth2Error.AUTHORIZATION_GRANT_TYPE);
        }
        // 校验此客户端，是否包含 authorization_code 模式
        if (!client.containsGrantType(GrantType.AUTHORIZATION_CODE.getCode())) {
            throw new OAuth2Exception(OAuth2Error.AUTHORIZATION_GRANT_TYPE);
        }
        // 将 第三方 传入的 redirectUri 进行解码
        var decodeRedirectUri = URLDecoder.decode(param.getRedirectUri(), StandardCharsets.UTF_8);
        var redirectUri = URI.create(decodeRedirectUri);
        // 比较 第三方传入的 redirectUri 域名部分 和 数据的 域名部分 是否一致
        if (!StringUtils.equalsIgnoreCase(redirectUri.getHost(), client.getDomainName())) {
            throw new OAuth2Exception(OAuth2Error.DOMAIN_NAME_ILLEGAL);
        }
        param.setRedirectUri(decodeRedirectUri);

        OAuth2ServerProperties props = jwtTokenUtils.getProps();

        // 一个随机的 授权ID，由系统随机生成，绑定给每个前来授权的第三方应用，
        // 主要用来连贯用户一些列操作！
        String codeId = jwtTokenUtils.createUUID();

        // 先将授权码的参数，保存到缓存中，以便后面使用
        tokenCacheService.setCodeMode(
                CODE_MODE_KEY + codeId,
                param,
                props.getCodeExpired(),
                TimeUnit.SECONDS
        );
        return codeId;
    }

    @Override
    public String login(LoginParam param) throws OAuth2Exception {
        String codeId = CODE_MODE_KEY + param.getCodeId();
        GetCodeModeParam codeMode = tokenCacheService.getCodeMode(codeId);
        if (codeMode == null) {
            throw new OAuth2Exception(OAuth2Error.CLIENT_INVALID);
        }
        // 校验用户信息，
        UserInfo userInfo = validateUserInfo(param.getUsername(), param.getPassword());
        // 生成随机 code 返回给第三方应用，就可以通过 code 来获取 access_token！
        String code = jwtTokenUtils.createUUID();
        OAuth2ServerProperties props = jwtTokenUtils.getProps();
        // 将 code 关联 登录的用户信息，保存起来。
        tokenCacheService.setUserInfo(
                CODE_KEY + code,
                userInfo,
                props.getCodeExpired(),
                TimeUnit.SECONDS
        );

        // 删除上一步保存的 codeId 信息 GetCodeModeParam 参数
        tokenCacheService.remove(codeId);

        // 将客户端id 跟 code 绑定。第三方应用通过 code 来换取 access_token，可以校验两个客户端id 是否一致！
        tokenCacheService.setString(
                CODE_CLIENT_ID_KEY + code,
                codeMode.getClientId(),
                props.getCodeExpired(),
                TimeUnit.SECONDS
        );

        // 构建回调 url 。跳转到第三方应用
        return builderRedirectUri(codeMode, code);
    }

    /**
     * 拼接 回调 url
     *
     * @author Wang Chen Chen<932560435@qq.com>
     * @create 2021/7/17 17:24
     */
    public String builderRedirectUri(GetCodeModeParam codeMode, String code) {
        /**
         * 判断url是否已经携带参数了
         * 如：https://xaaef.com/authorize/callback?pid=2135543
         * 就只能在后面继续加 &
         * 如：https://xaaef.com/authorize/callback
         * 就需要加 ?
         * */
        var prefix = "?";
        if (StringUtils.contains(codeMode.getRedirectUri(), prefix)) {
            prefix = "&";
        }
        // 构建回调 url
        return new StringBuilder(codeMode.getRedirectUri())
                .append(prefix)
                .append("code=").append(code)
                .append("&state=").append(StringUtils.trimToEmpty(codeMode.getState()))
                .toString();
    }


    @Override
    public OAuth2Token authorize(AuthorizationCodeModeParam param) throws OAuth2Exception {
        ClientDetails client = validateClient(
                param.getClientId(),
                param.getClientSecret(),
                GrantType.AUTHORIZATION_CODE,
                param.getGrantType()
        );
        // 获取 getCode 时使用的 客户端id
        String getCodeClientIdKey = CODE_CLIENT_ID_KEY + param.getCode();
        String getCodeClientId = tokenCacheService.getString(getCodeClientIdKey);
        if (!StringUtils.equals(getCodeClientId, param.getClientId())) {
            throw new OAuth2Exception(OAuth2Error.CLIENT_IS_DIFFERENT);
        }

        String codeUserInfoKey = CODE_KEY + param.getCode();

        // 通过 code 获取 前面保存的 登录用户的信息
        UserInfo userInfo = tokenCacheService.getUserInfo(codeUserInfoKey);
        if (userInfo == null) {
            throw new OAuth2Exception(OAuth2Error.USER_INVALID);
        }
        // 生成 唯一的 认证id
        String tokenId = jwtTokenUtils.createTokenId();
        // 服务端 token
        TokenValue tokenValue = TokenValue.builder()
                .tokenId(tokenId)
                .client(client)
                .user(userInfo)
                .grantType(GrantType.AUTHORIZATION_CODE)
                .build();

        setTokenCache(tokenId, tokenValue);

        // 删除存储的信息
        tokenCacheService.remove(getCodeClientIdKey);
        tokenCacheService.remove(codeUserInfoKey);

        return buildToken(tokenId);
    }

}
