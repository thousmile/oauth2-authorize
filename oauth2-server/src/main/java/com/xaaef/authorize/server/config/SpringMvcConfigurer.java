package com.xaaef.authorize.server.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xaaef.authorize.common.util.JsonUtils;
import com.xaaef.authorize.server.interceptor.Oauth2HandlerInterceptor;
import com.xaaef.authorize.server.props.Oauth2Properties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.Ordered;
import org.springframework.format.Formatter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static com.xaaef.authorize.common.constant.DateTimeFormatConst.DEFAULT_DATE_TIME_PATTERN;
import static com.xaaef.authorize.common.constant.DateTimeFormatConst.DEFAULT_DATE_PATTERN;
import static com.xaaef.authorize.common.constant.DateTimeFormatConst.DEFAULT_TIME_PATTERN;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

/**
 * All rights Reserved, Designed By www.ifsaid.com
 * <p>
 * spring mvc 配置
 * </p>
 *
 * @author Wang Chen Chen <932560435@qq.com>
 * @version 2.0
 * @date 2019/4/18 11:45
 * @copyright 2019 http://www.ifsaid.com/ Inc. All rights reserved.
 */

@Slf4j
@Configuration
public class SpringMvcConfigurer implements WebMvcConfigurer {

    @Bean
    public FilterRegistrationBean corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
        //设置优先级为最高
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return bean;
    }

    @Autowired
    private Oauth2Properties props;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        log.info("ExcludePath : {}", props.getExcludePath());
        registry.addInterceptor(new Oauth2HandlerInterceptor(props))
                .addPathPatterns("/**")
                .excludePathPatterns(props.getExcludePath())
                .excludePathPatterns(List.of("/css/**", "/img/**", "/js/**"));
    }


    /**
     * 创建http请求工具
     *
     * @param []
     * @return org.springframework.web.client.RestTemplate
     * @author Wang Chen Chen<932560435@qq.com>
     * @date 2020/7/23 11:15
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }


    /**
     * 密码加密，工具类
     *
     * @author Wang Chen Chen<932560435@qq.com>
     * @date 2020/7/25 10:08
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    /***
     * Date日期类型转换器
     */
    @Bean
    public Formatter<Date> dateFormatter() {
        return new Formatter<Date>() {

            @Override
            public Date parse(String text, Locale locale) {
                Date date = null;
                try {
                    date = DateUtils.parseDate(text, locale, DEFAULT_DATE_TIME_PATTERN);
                } catch (Exception e) {
                    log.error(e.getMessage());
                }
                return date;
            }

            @Override
            public String print(Date date, Locale locale) {
                return DateFormatUtils.format(date, DEFAULT_DATE_TIME_PATTERN, TimeZone.getDefault(), locale);
            }
        };
    }

    @Bean
    public Formatter<LocalDate> localDateFormatter() {
        return new Formatter<LocalDate>() {
            @Override
            public LocalDate parse(String text, Locale locale) {
                return LocalDate.parse(text, DateTimeFormatter.ofPattern(DEFAULT_DATE_PATTERN, locale));
            }

            @Override
            public String print(LocalDate object, Locale locale) {
                return DateTimeFormatter.ofPattern(DEFAULT_DATE_PATTERN, locale).format(object);
            }
        };
    }

    @Bean
    public Formatter<LocalTime> localTimeFormatter() {
        return new Formatter<LocalTime>() {
            @Override
            public LocalTime parse(String text, Locale locale) {
                return LocalTime.parse(text, DateTimeFormatter.ofPattern(DEFAULT_TIME_PATTERN, locale));
            }

            @Override
            public String print(LocalTime object, Locale locale) {
                return DateTimeFormatter.ofPattern(DEFAULT_TIME_PATTERN, locale).format(object);
            }
        };
    }

    @Bean
    public Formatter<LocalDateTime> localDateTimeFormatter() {
        return new Formatter<LocalDateTime>() {

            @Override
            public String print(LocalDateTime localDateTime, Locale locale) {
                return DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_PATTERN, locale).format(localDateTime);
            }

            @Override
            public LocalDateTime parse(String text, Locale locale) {
                return LocalDateTime.parse(text, DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_PATTERN, locale));
            }
        };
    }

}
