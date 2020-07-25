package com.xaaef.authorize.server.controller;

import com.xaaef.authorize.common.exception.Oauth2Exception;
import com.xaaef.authorize.common.param.AuthorizationCodeModeParam;
import com.xaaef.authorize.common.param.ClientModeParam;
import com.xaaef.authorize.common.param.GetCodeModeParam;
import com.xaaef.authorize.common.param.PasswordModeParam;
import com.xaaef.authorize.common.token.OauthToken;
import com.xaaef.authorize.common.util.JsonResult;
import com.xaaef.authorize.common.util.VerifyCodeUtils;
import com.xaaef.authorize.server.entity.UserInfo;
import com.xaaef.authorize.server.param.LoginParam;
import com.xaaef.authorize.server.service.AuthorizeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
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
@RequestMapping("authorize")
public class AuthorizeController {

    private AuthorizeService authorizeService;

    @Autowired
    public AuthorizeController(AuthorizeService authorizeService) {
        this.authorizeService = authorizeService;
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
        log.info("param: {}", param);
        return JsonResult.success();
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
        log.info("param: {}", param);
        return JsonResult.success();
    }


    /**
     * 授权码模式，第一步，第三方请求用户登录
     *
     * @author Wang Chen Chen<932560435@qq.com>
     * @date 2020/7/23 14:08
     */
    @GetMapping("code")
    public ModelAndView getCode(@Validated GetCodeModeParam param, BindingResult br) {
        var result = new ModelAndView("paramError");
        if (br.hasErrors()) {
            result.addObject("errors", br.getFieldErrors());
        } else {
            try {
                authorizeService.checkAuthorizationCode(param);
                result.addObject("clientId", param.getClientId());
                result.setViewName("login");
            } catch (Oauth2Exception e) {
                var oauth2Errors = List.of(
                        new FieldError("Oauth2Exception", String.valueOf(e.getStatus()), e.getMessage())
                );
                result.addObject("errors", oauth2Errors);
            }
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
        } else {
            try {
                String code = authorizeService.login(param);
                return JsonResult.success(null, code);
            } catch (Oauth2Exception e) {
                return JsonResult.fail(e.getMessage());
            }
        }
    }


    /**
     * 授权码模式，第三步，如果用户登录成功，回调到第三方应用
     *
     * @author Wang Chen Chen<932560435@qq.com>
     * @date 2020/7/23 14:08
     */
    @GetMapping("/login/success/callback")
    public ModelAndView loginSuccessCallback(String clientId, String code) {
        var result = new ModelAndView("paramError");
        try {
            String redirectUri = authorizeService.builderRedirectUri(clientId, code);
            log.info("redirectUri: {}", redirectUri);
            result.setView(new RedirectView(redirectUri));
            return result;
        } catch (Oauth2Exception e) {
            var oauth2Errors = List.of(
                    new FieldError("Oauth2Exception", String.valueOf(e.getStatus()), e.getMessage())
            );
            result.addObject("errors", oauth2Errors);
        }
        return result;
    }


    /**
     * 授权码模式，第四步  第三方通过 code 换取 token
     *
     * @author Wang Chen Chen<932560435@qq.com>
     * @date 2020/7/23 14:08
     */
    @PostMapping("code")
    @ResponseBody
    public JsonResult<String> authorizationCode(@RequestBody @Validated AuthorizationCodeModeParam param,
                                                BindingResult br) {
        if (br.hasErrors()) {
            String message = br.getFieldError().getDefaultMessage();
            return JsonResult.fail(message);
        }
        try {
            OauthToken oauthToken = authorizeService.authorizationCode(param);
            return JsonResult.success(oauthToken);
        } catch (Oauth2Exception e) {
            return JsonResult.result(e.getStatus(), e.getMessage());
        }
    }


    /**
     * 授权码模式，第五步  第三方通过 第四步返回的 access_token 获取用户 ID
     *
     * @author Wang Chen Chen<932560435@qq.com>
     * @date 2020/7/23 14:08
     */
    @PostMapping("userinfo")
    @ResponseBody
    public JsonResult<String> getUserInfo(@RequestAttribute("userId") String userId) {
        try {
            var userInfo = authorizeService.getUserInfo(userId);
            return JsonResult.success(userInfo);
        } catch (Oauth2Exception e) {
            return JsonResult.result(e.getStatus(), e.getMessage());
        }
    }


    /**
     * 获取验证码，前端传入一个 key，根据key生成一个验证码。将key 和验证码的值 保存在 redis 中
     * 用户登录的时候，根据key获取到，生成验证码值 和 用户输入验证码的值 做一个比较。
     *
     * @author Wang Chen Chen<932560435@qq.com>
     * @date 2020/7/23 14:08
     */
    @GetMapping("/verify/code/{codeKey}")
    public void imageVerifyCode(@PathVariable String codeKey, HttpServletResponse response) throws IOException {
        // 设置响应的类型格式为图片格式
        response.setContentType("image/jpeg");
        // 禁止图像缓存。
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        BufferedImage image = authorizeService.generateVerifyCode(codeKey);
        // 获取 图片 验证码
        ImageIO.write(image, "JPEG", response.getOutputStream());
    }


}
