package com.xaaef.authorize.server.service;

import java.awt.image.BufferedImage;

/**
 * All rights Reserved, Designed By www.xaaef.com
 * <p>
 * </p>
 *
 * @author Wang Chen Chen
 * @version 1.0.1
 * @date 2021/7/12 14:11
 * @copyright 2021 http://www.xaaef.com Inc. All rights reserved.
 */


public interface CaptchaCodesService {

    /**
     * 随机获取图形验证码
     *
     * @param codeKey
     * @return ImageVerifyCode
     * @author Wang Chen Chen<932560435@qq.com>
     * @createTime 2020/3/5 0005 11:31
     */
    BufferedImage randomImageVerifyCode(String codeKey);

    /**
     * 删除在 redis 中缓存的。图形验证码值
     *
     * @param codeKey
     * @return ImageVerifyCode
     * @author Wang Chen Chen<932560435@qq.com>
     * @createTime 2020/3/5 0005 11:31
     */
    void deleteImageVerifyCode(String codeKey);

    /**
     * 删除在 redis 中缓存的。图形验证码值
     *
     * @param codeKey
     * @param userCodeText
     * @return ImageVerifyCode
     * @author Wang Chen Chen<932560435@qq.com>
     * @createTime 2020/3/5 0005 11:31
     */
    boolean checkVerifyCode(String codeKey, String userCodeText);


}
