package com.xaaef.authorize.server;

import com.xaaef.authorize.server.entity.ClientDetails;
import com.xaaef.authorize.server.mapper.ClientDetailsMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * All rights Reserved, Designed By www.xaaef.com
 * <p>
 * </p>
 *
 * @author Wang Chen Chen<932560435@qq.com>
 * @version 1.0.0
 * @date 2020/7/2317:51
 */

@Slf4j
@SpringBootTest
public class MapperTests {

    @Autowired
    private ClientDetailsMapper detailsMapper;

    @Test
    public void test(){
        ClientDetails clientDetails = detailsMapper.selectByPrimaryKey("651wdqdwqd");
        log.info(clientDetails.toString());
    }


}
