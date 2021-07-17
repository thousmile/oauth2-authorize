package com.xaaef.authorize.client;

import com.xaaef.authorize.client.props.OAuth2ClientProperties;
import com.xaaef.authorize.common.domain.TokenValue;
import com.xaaef.authorize.common.exception.OAuth2Exception;
import com.xaaef.authorize.common.util.JsonResult;
import com.xaaef.authorize.common.util.JsonUtils;
import com.xaaef.authorize.common.util.SecurityUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.*;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.concurrent.TimeUnit;


/**
 * All rights Reserved, Designed By www.xaaef.com
 * <p>
 * </p>
 *
 * @author Wang Chen Chen
 * @version 1.0.1
 * @date 2021/7/13 13:52
 * @copyright 2021 http://www.xaaef.com Inc. All rights reserved.
 */


@Slf4j
@AllArgsConstructor
public class AuthorizeServiceImpl implements AuthorizeService {

    private OAuth2ClientProperties props;

    private static final String TOKEN_CACHE_KEY = "client_token_cache:";

    private static final String TOKEN_ID_CACHE_KEY = "client_token_id_cache:";

    private RestTemplate restTemplate;

    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public TokenValue validate(String jwtToken) throws OAuth2Exception {
        // 判断缓存里是有 tokenId
        String tokenId = (String) redisTemplate.opsForValue().get(TOKEN_ID_CACHE_KEY + jwtToken);
        TokenValue result = null;
        if (StringUtils.hasText(tokenId)) {
            result = get(tokenId);
            if (result != null) {
                log.debug("校验token 从redis中获取到TokenValue！");
                return result;
            }
        }
        try {
            TokenValue tokenValue = get(props.getServerUrl(), jwtToken);
            log.debug("校验token 从 OAuth2.0 Server 中获取到 TokenValue ！");
            // 将此token 缓存到 redis 中
            redisTemplate.opsForValue().set(
                    TOKEN_ID_CACHE_KEY + jwtToken,
                    tokenValue.getTokenId(),
                    props.getTokenCacheExpired(),
                    TimeUnit.SECONDS);

            // 将 tokenid 和 tokenValue 缓存起来
            redisTemplate.opsForValue().set(
                    TOKEN_CACHE_KEY + tokenValue.getTokenId(),
                    tokenValue,
                    props.getTokenCacheExpired(),
                    TimeUnit.SECONDS);

            log.debug("将 TokenValue 保存到 redis 缓存中 ！");

            return tokenValue;
        } catch (IOException | InterruptedException e) {
            log.warn("http GET 请求错误 : ", e.getMessage());
        }
        return null;
    }

    @Override
    public TokenValue get(String tokenId) {
        TokenValue token = (TokenValue) redisTemplate.opsForValue().get(TOKEN_CACHE_KEY + tokenId);
        return token;
    }

    @Override
    public boolean urlIgnore(String url) {
        if (props.getExcludePath() == null || props.getExcludePath().size() < 1) {
            return false;
        }
        return SecurityUtils.urlIgnore(props.getExcludePath(), url);
    }

    @Override
    public OAuth2ClientProperties getProps() {
        return props;
    }

    private TokenValue get(String serverUrl, String jwtToken) throws OAuth2Exception, IOException, InterruptedException {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        headers.add(props.getTokenHeader(), jwtToken);
        ResponseEntity<JsonResult> response = restTemplate.exchange(
                serverUrl,
                HttpMethod.GET,
                new HttpEntity<String>(headers),
                JsonResult.class
        );
        JsonResult result = response.getBody();
        if (result.getStatus() == HttpStatus.OK.value()) {
            String dataStr = JsonUtils.toJson(result.getData());
            TokenValue tokenValue = JsonUtils.toPojo(dataStr, TokenValue.class);
            return tokenValue;
        }
        throw new OAuth2Exception(result.getStatus(), result.getMessage());
    }

}
