package com.xaaef.authorize.clienttest.controller;

import com.xaaef.authorize.common.util.JsonResult;
import com.xaaef.authorize.common.util.SecurityUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

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
@RestController
@AllArgsConstructor
public class IndexController {

    /**
     * @author Wang Chen Chen<932560435@qq.com>
     * @date 2020/7/23 14:08
     */
    @GetMapping("hello")
    @ResponseBody
    public JsonResult<String> hello() {
        return JsonResult.success("hello");
    }


    /**
     * 获取登录信息
     *
     * @author Wang Chen Chen<932560435@qq.com>
     * @date 2020/7/23 14:08
     */
    @GetMapping("index")
    @ResponseBody
    public JsonResult<String> index() {
        return JsonResult.success(SecurityUtils.getTokenValue());
    }

}
