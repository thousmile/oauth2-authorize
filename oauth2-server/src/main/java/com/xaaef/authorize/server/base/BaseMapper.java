package com.xaaef.authorize.server.base;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * All rights Reserved, Designed By www.xaaef.com
 * <p>
 * 通用 增删改查询
 * </p>
 *
 * @author Wang Chen Chen<932560435@qq.com>
 * @version 1.0.0
 * @date 2020/7/2311:37
 */

public interface BaseMapper<T, ID> extends Mapper<T>, MySqlMapper<T> {

}

