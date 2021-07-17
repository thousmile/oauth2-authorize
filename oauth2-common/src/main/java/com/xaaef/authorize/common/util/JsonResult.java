package com.xaaef.authorize.common.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * All rights Reserved, Designed By 深圳市铭灏天智能照明设备有限公司
 * <p>
 * RESTful API 统一返回实体类
 * </p>
 *
 * @author Wang Chen Chen
 * @version 1.0
 * @date 2021/7/5 9:31
 * @copyright 2021 http://www.mhtled.com Inc. All rights reserved.
 */

@Getter
@Setter
public class JsonResult<T> implements Serializable {

    private static final int SUCCESS = 200;

    private static final int FAIL = 100;

    /**
     * 返回状态码
     */
    private Integer status;

    /**
     * 返回消息
     */
    private String message;

    /**
     * 返回内容
     */
    private T data;

    private JsonResult() {
    }

    /**
     * 自定义返回状态码
     *
     * @param status
     * @param message
     * @param data
     * @return JsonResult
     * @author Wang Chen Chen<932560435@qq.com>
     * @date 2019/12/12 21:08
     */
    private static <T> JsonResult result(int status, String message, T data) {
        JsonResult result = new JsonResult();
        result.status = status;
        result.message = message;
        result.data = data;
        return result;
    }

    /**
     * 自定义返回状态码
     *
     * @param status
     * @param data
     * @return JsonResult
     * @author Wang Chen Chen<932560435@qq.com>
     * @date 2019/12/12 21:08
     */
    private static <T> JsonResult result(int status, T data) {
        return result(status, "ok", data);
    }

    /**
     * 自定义返回状态码 [不建议使用，建议扩展方法]
     *
     * @param status
     * @param message
     * @return JsonResult
     * @author Wang Chen Chen<932560435@qq.com>
     * @date 2019/12/12 21:08
     */
    public static <T> JsonResult result(int status, String message) {
        return result(status, message, null);
    }


    /**
     * 操作成功
     *
     * @return JsonResult
     * @author Wang Chen Chen<932560435@qq.com>
     * @date 2019/12/12 21:08
     */
    public static <T> JsonResult success() {
        return result(SUCCESS, "ok");
    }

    /**
     * 操作成功
     *
     * @param total
     * @param list
     * @author Wang Chen Chen<932560435@qq.com>
     * @date 2019/12/12 21:08
     */
    public static <T> JsonResult success(long total, List<T> list) {
        return success(new ResultPage<T>(total, list));
    }

    /**
     * 操作成功
     *
     * @param page
     * @author Wang Chen Chen<932560435@qq.com>
     * @date 2019/12/12 21:08
     */
    public static <T> JsonResult success(ResultPage<T> page) {
        return result(SUCCESS, "ok", page);
    }


    /**
     * 操作成功
     *
     * @param message
     * @return JsonResult
     * @author Wang Chen Chen<932560435@qq.com>
     * @date 2019/12/12 21:08
     */
    public static <T> JsonResult success(String message) {
        return result(SUCCESS, message, null);
    }

    /**
     * 操作成功
     *
     * @param data
     * @return JsonResult
     * @author Wang Chen Chen<932560435@qq.com>
     * @date 2019/12/12 21:08
     */
    public static <T> JsonResult success(T data) {
        return result(SUCCESS, "ok", data);
    }

    /**
     * 操作成功
     *
     * @param message
     * @param data
     * @return JsonResult
     * @author Wang Chen Chen<932560435@qq.com>
     * @date 2019/12/12 21:08
     */
    public static <T> JsonResult success(String message, T data) {
        return result(SUCCESS, message, data);
    }


    /**
     * 操作失败
     *
     * @param message
     * @return JsonResult
     * @author Wang Chen Chen<932560435@qq.com>
     * @date 2019/12/12 21:08
     */
    public static <T> JsonResult fail(String message) {
        return result(FAIL, message, null);
    }

    /**
     * 操作失败
     *
     * @author Wang Chen Chen<932560435@qq.com>
     * @date 2019/12/12 21:08
     */
    public static <T> JsonResult fail() {
        return result(FAIL, "fail", null);
    }


    /**
     * 异常错误
     *
     * @author Wang Chen Chen<932560435@qq.com>
     * @date 2019/12/12 21:08
     */
    public static <T> JsonResult<String> error(int status, String message) {
        return result(status, message, null);
    }


    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ResultPage<T> {

        /**
         * 总共，有多少条
         */
        private Long total;

        /**
         * 数据
         */
        private List<T> list;

    }

}
