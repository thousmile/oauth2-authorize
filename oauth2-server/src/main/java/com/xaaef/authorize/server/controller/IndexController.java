package com.xaaef.authorize.server.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * All rights Reserved, Designed By www.xaaef.com
 * <p>
 * </p>
 *
 * @author Wang Chen Chen
 * @version 1.0.1
 * @date 2021/7/15 16:59
 * @copyright 2021 http://www.xaaef.com Inc. All rights reserved.
 */

@Slf4j
@Controller
public class IndexController {

    @GetMapping(value = {"", "/", "index", "home"})
    public ModelAndView index() {
        return new ModelAndView("index");
    }

}
