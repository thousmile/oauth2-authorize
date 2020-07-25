package com.xaaef.authorize.server;

import com.xaaef.authorize.common.constant.StatusConst;
import com.xaaef.authorize.common.util.SnowFlakeUtils;
import com.xaaef.authorize.server.entity.ClientDetails;
import com.xaaef.authorize.server.entity.UserInfo;
import com.xaaef.authorize.server.mapper.ClientDetailsMapper;
import com.xaaef.authorize.server.mapper.UserInfoMapper;
import com.xaaef.authorize.server.util.RandomDataUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

/**
 * All rights Reserved, Designed By www.xaaef.com
 * <p>
 * </p>
 *
 * @author Wang Chen Chen<932560435@qq.com>
 * @version 1.0.0
 * @date 2020/7/2516:48
 */


@Slf4j
@SpringBootTest
public class RandomGenerateDataTests {

    @Autowired
    private ClientDetailsMapper detailsMapper;

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 生成客户端
     *
     * @date 2020/7/25 16:48
     */
    @Test
    public void test1() {
        ClientDetails details = ClientDetails.builder()
                .clientId(SnowFlakeUtils.getStringId())
                .secret(RandomDataUtils.randomString())
                .name("测试 app 002 ")
                .logoUrl("https://xaaef.com/static/img/logo.6eda394c.png")
                .description("测试 app 002 ")
                .clientType("internal")
                .grantTypes("authorization_code")
                .domainName("localhost")
                .scope("user_base")
                .status(StatusConst.NORMAL)
                .createTime(LocalDateTime.now())
                .lastUpdateTime(LocalDateTime.now())
                .build();
        detailsMapper.insertSelective(details);
    }

    /**
     * 生成 用户
     *
     * @date 2020/7/25 16:48
     */
    @Test
    public void test2() {
        String tel = RandomDataUtils.randomTel();
        UserInfo userInfo = UserInfo.builder()
                .userId(SnowFlakeUtils.getStringId())
                .username(tel)
                .mobile(tel)
                .password(passwordEncoder.encode("123456"))
                .nickname(RandomDataUtils.randomChineseName())
                .avatar("https://image.xaaef.com/09083333a95f44c38e0b2ef829718a18.jpg")
                .gender(RandomDataUtils.getNum(0, 2))
                .address(RandomDataUtils.randomRoad())
                .status(StatusConst.NORMAL)
                .createTime(LocalDateTime.now())
                .lastUpdateTime(LocalDateTime.now())
                .build();
        userInfoMapper.insertSelective(userInfo);
    }

}
