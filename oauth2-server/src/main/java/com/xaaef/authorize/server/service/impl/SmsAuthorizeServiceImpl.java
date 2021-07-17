package com.xaaef.authorize.server.service.impl;

import com.xaaef.authorize.common.domain.ClientDetails;
import com.xaaef.authorize.common.domain.TokenValue;
import com.xaaef.authorize.common.domain.UserInfo;
import com.xaaef.authorize.common.enums.GrantType;
import com.xaaef.authorize.common.enums.OAuth2Error;
import com.xaaef.authorize.common.exception.OAuth2Exception;
import com.xaaef.authorize.common.token.OAuth2Token;
import com.xaaef.authorize.server.constant.TokenConstant;
import com.xaaef.authorize.server.params.SmsModeParam;
import com.xaaef.authorize.server.repository.ClientDetailsRepository;
import com.xaaef.authorize.server.repository.UserInfoRepository;
import com.xaaef.authorize.server.service.SmsAuthorizeService;
import com.xaaef.authorize.server.service.TokenCacheService;
import com.xaaef.authorize.server.util.JwtTokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * All rights Reserved, Designed By www.xaaef.com
 * <p>
 * </p>
 *
 * @author Wang Chen Chen
 * @version 1.0.1
 * @date 2021/7/12 17:07
 * @copyright 2021 http://www.xaaef.com Inc. All rights reserved.
 */

@Slf4j
@Service
public class SmsAuthorizeServiceImpl extends DefaultAuthorizeService implements SmsAuthorizeService {

    public SmsAuthorizeServiceImpl(ClientDetailsRepository clientDetailsRepository,
                                   UserInfoRepository userInfoRepository,
                                   TokenCacheService tokenCacheService,
                                   JwtTokenUtils jwtTokenUtils) {
        super(clientDetailsRepository, userInfoRepository, tokenCacheService, jwtTokenUtils);
    }

    /**
     * 阿里云短信验证码
     *
     * @throws
     * @author Wang Chen Chen<932560435@qq.com>
     * @create 2021/7/17 14:06
     */
    private void sendAliyunSms(String mobile, String code) {
        log.info("阿里云短信 手机号: {}  验证码: {} ", mobile, code);
    }

    @Override
    public String sendSms(String clientId, String mobile) throws OAuth2Exception {
        ClientDetails client = getClient(clientId);
        if (client == null) {
            throw new OAuth2Exception(OAuth2Error.CLIENT_INVALID);
        }
        // 校验客户端，是否有 短信验证码的 授权方式
        if (!client.containsGrantType(GrantType.SMS.getCode())) {
            throw new OAuth2Exception(OAuth2Error.AUTHORIZATION_GRANT_TYPE);
        }
        UserInfo userInfo = userInfoRepository.selectByMobile(mobile);
        if (userInfo == null) {
            throw new OAuth2Exception(OAuth2Error.USER_MOBILE_INVALID);
        }
        String code = String.valueOf(Math.round((Math.random() + 1) * 1000));

        // 发送短信验证码
        sendAliyunSms(mobile, code);

        String smsKey = TokenConstant.SMS_KEY + mobile;
        // 短信验证码 有效期
        tokenCacheService.setString(
                smsKey,
                code,
                getProps().getSmsCodeExpired(),
                TimeUnit.SECONDS
        );
        String clientKey = TokenConstant.CLIENT_KEY + mobile;
        // 短信验证码 授权的客户端ID
        tokenCacheService.setString(
                clientKey,
                clientId,
                getProps().getSmsCodeExpired(),
                TimeUnit.SECONDS
        );
        return code;
    }

    @Override
    public UserInfo validateSmsCode(String mobile, String code) throws OAuth2Exception {
        String smsKey = TokenConstant.SMS_KEY + mobile;
        // 获取短信验证码；
        String serverCode = tokenCacheService.getString(smsKey);
        // 短信验证码错误！
        if (!StringUtils.equalsIgnoreCase(code, serverCode)) {
            throw new OAuth2Exception(OAuth2Error.VERIFICATION_CODE_ERROR);
        }
        return userInfoRepository.selectByMobile(mobile);
    }

    @Override
    public OAuth2Token authorize(SmsModeParam param) throws OAuth2Exception {
        String clientKey = TokenConstant.CLIENT_KEY + param.getMobile();
        String serverClientId = tokenCacheService.getString(clientKey);
        // 判断发送验证码使用的客户端id 和 本次客户端id 是否一致
        if (!StringUtils.equals(serverClientId, param.getClientId())) {
            throw new OAuth2Exception(OAuth2Error.CLIENT_IS_DIFFERENT);
        }
        ClientDetails client = validateClient(
                param.getClientId(),
                param.getClientSecret(),
                GrantType.SMS,
                param.getGrantType()
        );
        // 校验 短信验证码
        UserInfo userInfo = validateSmsCode(param.getMobile(), param.getCode());

        // 生成 唯一的 认证id
        String tokenId = jwtTokenUtils.createTokenId();

        // 服务端 token
        TokenValue tokenValue = TokenValue.builder()
                .tokenId(tokenId)
                .client(client)
                .user(userInfo)
                .grantType(GrantType.SMS)
                .build();

        setTokenCache(tokenId, tokenValue);

        // 移除短信验证码
        String smsKey = TokenConstant.SMS_KEY + param.getMobile();
        tokenCacheService.remove(smsKey);

        return buildToken(tokenId);
    }

}
