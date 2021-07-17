package com.xaaef.authorize.server.controller;

import com.xaaef.authorize.common.exception.OAuth2Exception;
import com.xaaef.authorize.common.token.OAuth2Token;
import com.xaaef.authorize.common.util.JsonResult;
import com.xaaef.authorize.common.util.JsonUtils;
import com.xaaef.authorize.common.util.SecurityUtils;
import com.xaaef.authorize.server.params.*;
import com.xaaef.authorize.server.service.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URI;
import java.util.List;

/**
 * All rights Reserved, Designed By www.xaaef.com
 * <p>
 * 授权认证 控制器
 * </p>
 *
 * @author Wang Chen Chen<932560435@qq.com>
 * @version 1.0.0
 * @date 2020/7/2314:02
 */


@Slf4j
@Controller
@AllArgsConstructor
public class AuthorizeController {

    private ClientAuthorizeService clientAuthorizeService;

    private PasswordAuthorizeService passwordAuthorizeService;

    private SmsAuthorizeService smsAuthorizeService;

    private CaptchaCodesService captchaCodesService;

    private AuthorizationCodeAuthorizeService codeAuthorizeService;

    private TokenService tokenService;

    /**
     * 授权码模式，1.第三方应用，请求获取用户信息。在此校验客户端是否正确
     *
     * @author Wang Chen Chen<932560435@qq.com>
     * @date 2020/7/23 14:08
     */
    @GetMapping("code")
    public ModelAndView getCode(@Validated GetCodeModeParam param, BindingResult br) {
        var result = new ModelAndView("paramError");
        if (br.hasErrors()) {
            result.addObject("errors", br.getFieldErrors());
        }
        log.info("getCode param: {}", JsonUtils.toJson(param));
        try {
            // 校验客户端是否存在！
            // 一个随机的 授权ID，由系统随机生成，绑定给每个前来授权的第三方应用，
            // 主要用来连贯用户一些列操作！
            String codeId = codeAuthorizeService.validateClient(param);

            result.addObject("codeId", codeId);

            result.setViewName("login");
        } catch (OAuth2Exception e) {
            var oauth2Errors = List.of(
                    new FieldError("OAuth2Exception", String.valueOf(e.getStatus()), e.getMessage())
            );
            result.addObject("errors", oauth2Errors);
        }
        return result;
    }

    /**
     * 授权码模式，第二步，用户登录
     *
     * @author Wang Chen Chen<932560435@qq.com>
     * @date 2020/7/23 14:08
     */
    @PostMapping("login")
    @ResponseBody
    public JsonResult<String> login(@RequestBody @Validated LoginParam param, BindingResult br) {
        if (br.hasErrors()) {
            return JsonResult.fail(br.getFieldError().getDefaultMessage());
        }
        try {
            // 回调 URL
            String redirectUri = codeAuthorizeService.login(param);
            // 让前端页面去跳转到 第三方应用
            return JsonResult.success(null, redirectUri);
        } catch (OAuth2Exception e) {
            return JsonResult.error(e.getStatus(), e.getMessage());
        }
    }


    /**
     * 授权码模式，第三步，第三方应用通过 code 来换取 access_token
     *
     * @author Wang Chen Chen<932560435@qq.com>
     * @date 2020/7/23 14:08
     */
    @PostMapping("access_token")
    @ResponseBody
    public JsonResult<String> authorizationCode(@RequestBody @Validated AuthorizationCodeModeParam param, BindingResult br) {
        if (br.hasErrors()) {
            return JsonResult.fail(br.getFieldError().getDefaultMessage());
        }
        log.info("authorizationCode param: {}", JsonUtils.toJson(param));
        try {
            var token = codeAuthorizeService.authorize(param);
            return JsonResult.success(token);
        } catch (OAuth2Exception e) {
            return JsonResult.error(e.getStatus(), e.getMessage());
        }
    }


    /**
     * 客户端模式
     *
     * @author Wang Chen Chen<932560435@qq.com>
     * @date 2020/7/23 14:08
     */
    @PostMapping("client")
    @ResponseBody
    public JsonResult<String> client(@RequestBody @Validated ClientModeParam param, BindingResult br) {
        if (br.hasErrors()) {
            String message = br.getFieldError().getDefaultMessage();
            return JsonResult.fail(message);
        }
        log.info("client param: {}", JsonUtils.toJson(param));
        try {
            var token = clientAuthorizeService.authorize(param);
            return JsonResult.success(token);
        } catch (OAuth2Exception e) {
            return JsonResult.error(e.getStatus(), e.getMessage());
        }
    }


    /**
     * 密码模式
     *
     * @author Wang Chen Chen<932560435@qq.com>
     * @date 2020/7/23 14:08
     */
    @PostMapping("password")
    @ResponseBody
    public JsonResult<String> password(@RequestBody @Validated PasswordModeParam param, BindingResult br) {
        if (br.hasErrors()) {
            String message = br.getFieldError().getDefaultMessage();
            return JsonResult.fail(message);
        }
        log.info("password param: {}", JsonUtils.toJson(param));
        try {
            var token = passwordAuthorizeService.authorize(param);
            return JsonResult.success(token);
        } catch (OAuth2Exception e) {
            return JsonResult.error(e.getStatus(), e.getMessage());
        }
    }


    /**
     * 发送 短信验证码
     *
     * @author Wang Chen Chen<932560435@qq.com>
     * @date 2020/7/23 14:08
     */
    @PostMapping("sms/send")
    @ResponseBody
    public JsonResult<String> sendSms(@RequestBody @Validated SendSmsParam param, BindingResult br) {
        if (br.hasErrors()) {
            String message = br.getFieldError().getDefaultMessage();
            return JsonResult.fail(message);
        }
        log.info("send sms param: {}", JsonUtils.toJson(param));
        try {
            String code = smsAuthorizeService.sendSms(param.getClientId(), param.getMobile());
            return JsonResult.success(code);
        } catch (OAuth2Exception e) {
            return JsonResult.error(e.getStatus(), e.getMessage());
        }
    }


    /**
     * 手机短信验证码 模式
     *
     * @author Wang Chen Chen<932560435@qq.com>
     * @date 2020/7/23 14:08
     */
    @PostMapping("sms")
    @ResponseBody
    public JsonResult<String> sms(@RequestBody @Validated SmsModeParam param, BindingResult br) {
        if (br.hasErrors()) {
            String message = br.getFieldError().getDefaultMessage();
            return JsonResult.fail(message);
        }
        log.info("sms param: {}", JsonUtils.toJson(param));
        try {
            var token = smsAuthorizeService.authorize(param);
            return JsonResult.success(token);
        } catch (OAuth2Exception e) {
            return JsonResult.error(e.getStatus(), e.getMessage());
        }
    }


    /**
     * 刷新 token
     *
     * @author Wang Chen Chen<932560435@qq.com>
     * @date 2020/7/23 14:08
     */
    @PostMapping("refresh")
    @ResponseBody
    public JsonResult<String> refreshToken(@RequestHeader("refresh_token") String refreshToken) {
        if (StringUtils.isEmpty(refreshToken)) {
            return JsonResult.fail("请求头中没有 refresh_token 参数！");
        }
        try {
            var token = tokenService.refresh(refreshToken);
            return JsonResult.success(token);
        } catch (OAuth2Exception e) {
            return JsonResult.error(e.getStatus(), e.getMessage());
        }
    }


    /**
     * 退出登录
     *
     * @author Wang Chen Chen<932560435@qq.com>
     * @date 2020/7/23 14:08
     */
    @PostMapping("logout")
    @ResponseBody
    public JsonResult<String> logout() {
        tokenService.logout();
        return JsonResult.success();
    }


    /**
     * 获取用户信息
     *
     * @author Wang Chen Chen<932560435@qq.com>
     * @date 2020/7/23 14:08
     */
    @GetMapping("loginInfo")
    @ResponseBody
    public JsonResult<String> loginInfo() {
        return JsonResult.success(SecurityUtils.getTokenValue());
    }


    /**
     * 获取验证码，前端传入一个 key，根据key生成一个验证码。将key 和验证码的值 保存在 redis 中
     * 用户登录的时候，根据key获取到，生成验证码值 和 用户输入验证码的值 做一个比较。
     *
     * @author Wang Chen Chen<932560435@qq.com>
     * @date 2020/7/23 14:08
     */
    @GetMapping("/captcha/codes/{codeKey}")
    public void imageCaptchaCodes(@PathVariable String codeKey, HttpServletResponse response) throws IOException {
        // 设置响应的类型格式为图片格式
        response.setContentType("image/jpeg");
        // 禁止图像缓存。
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        BufferedImage image = captchaCodesService.randomImageVerifyCode(codeKey);
        // 获取 图片 验证码
        ImageIO.write(image, "JPEG", response.getOutputStream());
    }


}
