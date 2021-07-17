package com.xaaef.authorize.server.service.impl;

import com.xaaef.authorize.common.util.VerifyCodeUtils;
import com.xaaef.authorize.server.constant.TokenConstant;
import com.xaaef.authorize.server.service.CaptchaCodesService;
import com.xaaef.authorize.server.service.TokenCacheService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 *
 * </p>
 *
 * @author Wang Chen Chen<932560435@qq.com>
 * @version 1.0
 * @createTime 2020/3/5 0005 11:32
 */

@Slf4j
@Service
@AllArgsConstructor
public class CaptchaCodesServiceImpl implements CaptchaCodesService {

    private TokenCacheService cacheService;

    @Override
    public BufferedImage randomImageVerifyCode(String codeKey) {
        VerifyCodeUtils.ImageVerifyCode image = VerifyCodeUtils.getImage();
        // 将验证码的 codeKey 和 codeText , 保存在 redis 中，有效时间为 10 分钟
        cacheService.setString(
                TokenConstant.CAPTCHA_CODE_KEY + codeKey,
                image.getCodeText().toUpperCase(),
                5,
                TimeUnit.MINUTES
        );
        return image.getImage();
    }

    @Override
    public void deleteImageVerifyCode(String codeKey) {
        cacheService.remove(TokenConstant.CAPTCHA_CODE_KEY + codeKey);
    }

    @Override
    public boolean checkVerifyCode(String codeKey, String userCodeText) {
        // 获取服务器的 CodeText
        String serverCodeText = cacheService.getString(TokenConstant.CAPTCHA_CODE_KEY + codeKey);
        // 将 serverCodeText 和 user.codeText 都转换成小写，然后比较
        if (StringUtils.equals(serverCodeText, userCodeText.toUpperCase())) {
            return true;
        } else {
            return false;
        }
    }

}
