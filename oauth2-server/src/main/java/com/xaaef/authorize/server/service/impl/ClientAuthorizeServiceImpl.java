package com.xaaef.authorize.server.service.impl;

import com.xaaef.authorize.common.domain.ClientDetails;
import com.xaaef.authorize.common.domain.TokenValue;
import com.xaaef.authorize.common.enums.GrantType;
import com.xaaef.authorize.common.exception.OAuth2Exception;
import com.xaaef.authorize.common.token.OAuth2Token;
import com.xaaef.authorize.server.params.ClientModeParam;
import com.xaaef.authorize.server.repository.ClientDetailsRepository;
import com.xaaef.authorize.server.repository.UserInfoRepository;
import com.xaaef.authorize.server.service.ClientAuthorizeService;
import com.xaaef.authorize.server.service.TokenCacheService;
import com.xaaef.authorize.server.util.JwtTokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * All rights Reserved, Designed By www.xaaef.com
 * <p>
 * </p>
 *
 * @author Wang Chen Chen
 * @version 1.0.1
 * @date 2021/7/12 14:26
 * @copyright 2021 http://www.xaaef.com Inc. All rights reserved.
 */

@Slf4j
@Service
public class ClientAuthorizeServiceImpl extends DefaultAuthorizeService implements ClientAuthorizeService {

    public ClientAuthorizeServiceImpl(ClientDetailsRepository clientDetailsRepository,
                                      UserInfoRepository userInfoRepository,
                                      TokenCacheService tokenCacheService,
                                      JwtTokenUtils jwtTokenUtils) {
        super(clientDetailsRepository, userInfoRepository, tokenCacheService, jwtTokenUtils);
    }


    @Override
    public OAuth2Token authorize(ClientModeParam param) throws OAuth2Exception {
        ClientDetails client = validateClient(
                param.getClientId(),
                param.getClientSecret(),
                GrantType.CLIENT_CREDENTIALS,
                param.getGrantType()
        );

        String tokenId = jwtTokenUtils.createTokenId();

        // 服务端 token
        TokenValue tokenValue = TokenValue.builder()
                .tokenId(tokenId)
                .client(client)
                .grantType(GrantType.CLIENT_CREDENTIALS)
                .build();

        setTokenCache(tokenId, tokenValue);

        return buildToken(tokenId);
    }


}
