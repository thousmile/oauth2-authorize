package com.xaaef.authorize.server.service;

import com.xaaef.authorize.common.exception.Oauth2Exception;
import com.xaaef.authorize.common.param.AuthorizationCodeModeParam;
import com.xaaef.authorize.common.param.GetCodeModeParam;
import com.xaaef.authorize.common.token.OauthToken;
import com.xaaef.authorize.server.entity.UserInfo;
import com.xaaef.authorize.server.param.LoginParam;

import java.awt.image.BufferedImage;

/**
 * All rights Reserved, Designed By www.xaaef.com
 * <p>
 * 授权服务
 * </p>
 *
 * @author Wang Chen Chen<932560435@qq.com>
 * @version 1.0.0
 * @date 2020/7/2316:43
 */


public interface AuthorizeService {

    /**
     * 生成 验证码
     *
     * @param codeKey
     * @return BufferedImage
     * @author Wang Chen Chen<932560435@qq.com>
     * @date 2020/7/23 16:47
     */
    BufferedImage generateVerifyCode(String codeKey);


    /**
     * 校验 验证码
     *
     * @param codeKey
     * @param codeText
     * @return boolean
     * @author Wang Chen Chen<932560435@qq.com>
     * @date 2020/7/23 16:47
     */
    boolean checkVerifyCode(String codeKey, String codeText);


    /**
     * 校验授权码模式，第一步 客户端信息
     *
     * @param param
     * @throws Oauth2Exception
     * @author Wang Chen Chen<932560435@qq.com>
     * @date 2020/7/23 16:48
     */
    void checkAuthorizationCode(GetCodeModeParam param) throws Oauth2Exception;


    /**
     * 第二步，用户，登录
     *
     * @param param
     * @return String
     * @throws Oauth2Exception
     * @author Wang Chen Chen<932560435@qq.com>
     * @date 2020/7/23 16:48
     */
    String login(LoginParam param) throws Oauth2Exception;


    /**
     * 第三步，构造回调 地址，回调到 第三方服务
     *
     * @param clientId
     * @param code
     * @return String
     * @throws Oauth2Exception
     * @author Wang Chen Chen<932560435@qq.com>
     * @date 2020/7/23 16:48
     */
    String builderRedirectUri(String clientId, String code) throws Oauth2Exception;


    /**
     * 第四步，通过 code 获取
     *
     * @param param
     * @return OauthToken
     * @throws Oauth2Exception
     * @author Wang Chen Chen<932560435@qq.com>
     * @date 2020/7/23 16:48
     */
    OauthToken authorizationCode(AuthorizationCodeModeParam param) throws Oauth2Exception;

    /**
     * 第五步，获取用户信息
     *
     * @param userId
     * @return UserInfo
     * @throws Oauth2Exception
     * @author Wang Chen Chen<932560435@qq.com>
     * @date 2020/7/23 16:48
     */
    UserInfo getUserInfo(String userId) throws Oauth2Exception;


}
