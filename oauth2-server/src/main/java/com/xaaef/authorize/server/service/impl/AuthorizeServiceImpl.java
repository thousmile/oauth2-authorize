package com.xaaef.authorize.server.service.impl;

import com.xaaef.authorize.common.constant.GrantTypeConst;
import com.xaaef.authorize.common.constant.Oauth2ReturnStatus;
import com.xaaef.authorize.common.constant.StatusConst;
import com.xaaef.authorize.common.exception.Oauth2Exception;
import com.xaaef.authorize.common.param.AuthorizationCodeModeParam;
import com.xaaef.authorize.common.param.GetCodeModeParam;
import com.xaaef.authorize.common.token.OauthToken;
import com.xaaef.authorize.common.util.VerifyCodeUtils;
import com.xaaef.authorize.server.entity.ClientDetails;
import com.xaaef.authorize.server.entity.UserInfo;
import com.xaaef.authorize.server.mapper.ClientDetailsMapper;
import com.xaaef.authorize.server.mapper.UserInfoMapper;
import com.xaaef.authorize.server.param.LoginParam;
import com.xaaef.authorize.server.props.Oauth2Properties;
import com.xaaef.authorize.server.service.AuthorizeService;
import com.xaaef.authorize.server.util.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * All rights Reserved, Designed By www.xaaef.com
 * <p>
 * </p>
 *
 * @author Wang Chen Chen<932560435@qq.com>
 * @version 1.0.0
 * @date 2020/7/2316:44
 */

@Slf4j
@Service
public class AuthorizeServiceImpl implements AuthorizeService {

    private RedisTemplate<String, Object> redisTemplate;

    private ClientDetailsMapper clientDetailsMapper;

    private UserInfoMapper userInfoMapper;

    private PasswordEncoder passwordEncoder;

    private Oauth2Properties props;

    @Autowired
    public AuthorizeServiceImpl(RedisTemplate<String, Object> redisTemplate,
                                ClientDetailsMapper clientDetailsMapper,
                                UserInfoMapper userInfoMapper,
                                PasswordEncoder passwordEncoder,
                                Oauth2Properties props
    ) {
        this.redisTemplate = redisTemplate;
        this.clientDetailsMapper = clientDetailsMapper;
        this.userInfoMapper = userInfoMapper;
        this.passwordEncoder = passwordEncoder;
        this.props = props;
    }

    @Override
    public BufferedImage generateVerifyCode(String codeKey) {
        VerifyCodeUtils.ImageVerifyCode image = VerifyCodeUtils.getImage();
        // 将验证码的值，设置到 redis 中
        redisTemplate.opsForValue().set(codeKey, image.getCodeText(), 10, TimeUnit.MINUTES);
        return image.getImage();
    }

    @Override
    public boolean checkVerifyCode(String codeKey, String codeText) {
        var obj = redisTemplate.opsForValue().get(codeKey);
        if (obj instanceof String) {
            return StringUtils.equalsIgnoreCase(String.valueOf(obj), codeText);
        }
        return false;
    }

    private ClientDetails checkClientDetails(String clientId) throws Oauth2Exception {
        var clientDetails = clientDetailsMapper.selectByPrimaryKey(clientId);
        // 判断 客户端 是否存在
        if (clientDetails == null) {
            throw new Oauth2Exception(Oauth2ReturnStatus.CLIENT_INVALID);
        }
        // 判断 客户端 是否被禁用
        if (clientDetails.getStatus() != StatusConst.NORMAL) {
            throw new Oauth2Exception(Oauth2ReturnStatus.CLIENT_DISABLE);
        }
        return clientDetails;
    }

    /**
     * @param grantTypes 当前客户端 拥有的 授权类型，如:"authorization_code,client_credentials"
     * @return enterGrantType 传入的 授权类型 如："authorization_code"
     * @author Wang Chen Chen<932560435@qq.com>
     * @date 2020/7/25 17:20
     */
    private void checkGrantTypes(String grantTypes, String enterGrantType) throws Oauth2Exception {
        String[] gt = StringUtils.split(grantTypes, ",");
        boolean have = List.of(gt).stream().filter(a -> StringUtils.equals(a, enterGrantType)).count() >= 1;
        if (!have) {
            throw new Oauth2Exception(Oauth2ReturnStatus.AUTHORIZATION_GRANT_TYPE);
        }
    }

    @Override
    public void checkAuthorizationCode(GetCodeModeParam param) throws Oauth2Exception {
        var clientDetails = checkClientDetails(param.getClientId());
        // 判断当前 第三方 用户 是否拥有 授权码模式 的权限
        checkGrantTypes(clientDetails.getGrantTypes(), GrantTypeConst.AUTHORIZATION_CODE);

        // 将 第三方 传入的 redirectUri 进行解码
        var decodeRedirectUri = URLDecoder.decode(param.getRedirectUri(), StandardCharsets.UTF_8);
        var redirectUri = URI.create(decodeRedirectUri);
        // 比较 第三方传入的 redirectUri 域名部分 和 数据的 域名部分 是否一致
        if (!StringUtils.equalsIgnoreCase(redirectUri.getHost(), clientDetails.getDomainName())) {
            throw new Oauth2Exception(Oauth2ReturnStatus.DOMAIN_NAME_ILLEGAL);
        }
        param.setRedirectUri(decodeRedirectUri);
        // 信息存入redis中，等后面使用
        redisTemplate.opsForValue().set(param.getClientId(), param, 30, TimeUnit.MINUTES);
    }


    @Override
    public String login(LoginParam param) throws Oauth2Exception {
        if (!checkVerifyCode(param.getCodeKey(), param.getCodeText())) {
            throw new Oauth2Exception(Oauth2ReturnStatus.VERIFICATION_CODE_ERROR);
        }
        // 使用 username 查询，用户是否存在
        var userInfo = userInfoMapper.selectOne(UserInfo.builder().username(param.getUsername()).build());
        if (userInfo == null) {
            // 使用 mobile 查询，用户是否存在
            userInfo = userInfoMapper.selectOne(UserInfo.builder().mobile(param.getUsername()).build());
            if (userInfo == null) {
                throw new Oauth2Exception(Oauth2ReturnStatus.USER_INVALID);
            }
        }
        // 判断 当前用户是否被禁用
        if (userInfo.getStatus() != StatusConst.NORMAL) {
            throw new Oauth2Exception(Oauth2ReturnStatus.USER_DISABLE);
        }
        // 校验，用户输入的密码和数据库加密过的密码比较
        if (!passwordEncoder.matches(param.getPassword(), userInfo.getPassword())) {
            throw new Oauth2Exception(Oauth2ReturnStatus.USER_PASSWORD_ERROR);
        }
        // 生成一个随机的 code 返回给 客户端
        var code = UUID.randomUUID().toString().replaceAll("-", "");

        userInfo.setCreateTime(null);
        userInfo.setLastUpdateTime(null);
        userInfo.setPassword(null);
        // 将当前登录的用户，设置到 redis 中，有效期一般只有10分钟
        redisTemplate.opsForValue().set(code, userInfo, 10, TimeUnit.MINUTES);
        // 删除 redis中的 验证码
        redisTemplate.delete(param.getCodeKey());
        return code;
    }

    @Override
    public String builderRedirectUri(String clientId, String code) throws Oauth2Exception {
        var obj = redisTemplate.opsForValue().get(clientId);
        if (obj instanceof GetCodeModeParam) {
            var param = (GetCodeModeParam) obj;
            var prefix = "?";
            if (StringUtils.contains(param.getRedirectUri(), prefix)) {
                prefix = "&";
            }
            // 构建回调 url
            return new StringBuilder(param.getRedirectUri())
                    .append(prefix).append("code=").append(StringUtils.trimToNull(code))
                    .append("&state=").append(StringUtils.trimToNull(param.getState()))
                    .toString();
        }
        throw new Oauth2Exception();
    }

    @Override
    public OauthToken authorizationCode(AuthorizationCodeModeParam param) throws Oauth2Exception {
        var clientDetails = checkClientDetails(param.getClientId());
        // 校验，客户端传入的密钥 和 数据库的密钥是否相同
        if (!StringUtils.equals(param.getClientSecret(), clientDetails.getSecret())) {
            throw new Oauth2Exception(Oauth2ReturnStatus.CLIENT_SECRET_ERROR);
        }
        // 从 redis 中 获取到 第二步 中的 登录的用户信息
        var obj = redisTemplate.opsForValue().get(param.getCode());
        if (obj instanceof UserInfo) {
            var loginUser = (UserInfo) obj;
            // 根据用户的ID，生成 一个 token 值
            var tokenValue = JwtUtils.generate(loginUser.getUserId(), props.getTokenExpired());

            // 根据用户的ID，生成 一个 refreshToken 值
            var refreshToken = JwtUtils.generate(loginUser.getUserId(), props.getRefreshTokenExpired());

            // 构建 OauthToken 对象
            var token = OauthToken.builder()
                    .accessToken(tokenValue)
                    .refreshToken(refreshToken)
                    .tokenType(props.getTokenType())
                    .expiresIn(props.getTokenExpired())
                    .build();

            // 删除第一步校验时，设置到redis中的客户端 信息
            redisTemplate.delete(param.getClientId());

            // 删除 第二步 生成 code
            redisTemplate.delete(param.getCode());

            // 将当前登录的用户，设置到 redis 中，方便获取
            loginUserStorage().put(loginUser.getUserId(), loginUser);
            return token;
        } else {
            throw new Oauth2Exception(Oauth2ReturnStatus.CODE_INVALID);
        }
    }

    @Override
    public UserInfo getUserInfo(String userId) throws Oauth2Exception {
        var obj = loginUserStorage().get(userId);
        if (obj instanceof UserInfo) {
            return (UserInfo) obj;
        }
        throw new Oauth2Exception(Oauth2ReturnStatus.USER_INVALID);
    }

    private static final String LOGIN_USER_STORAGE_KEY = "login_user_storage";

    private BoundHashOperations<String, Object, Object> loginUserStorage() {
        return redisTemplate.boundHashOps(LOGIN_USER_STORAGE_KEY);
    }


}
