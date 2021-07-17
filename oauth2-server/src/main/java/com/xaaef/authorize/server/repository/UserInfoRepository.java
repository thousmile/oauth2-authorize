package com.xaaef.authorize.server.repository;

import com.xaaef.authorize.common.domain.UserInfo;
import com.xaaef.authorize.common.enums.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * All rights Reserved, Designed By www.xaaef.com
 * <p>
 * </p>
 *
 * @author Wang Chen Chen
 * @version 1.0.1
 * @date 2021/7/12 14:35
 * @copyright 2021 http://www.xaaef.com Inc. All rights reserved.
 */

@Slf4j
@Repository
@AllArgsConstructor
public class UserInfoRepository {

    private JdbcTemplate jdbcTemplate;

    /**
     * 根据 用户ID , 查询用户详情
     *
     * @param account
     * @return ClientDetails
     * @author Wang Chen Chen
     * @date 2021/7/12 14:40
     */
    public UserInfo selectByAccount(String account) {
        var sql = "SELECT * FROM user_info WHERE user_id = ? OR username = ? OR mobile = ? OR email = ?";
        try {
            return jdbcTemplate.queryForObject(
                    sql, getUserInfoRowMapper(),
                    account,
                    account,
                    account,
                    account
            );
        } catch (EmptyResultDataAccessException ex) {
            log.error("ex : {}", ex.getMessage());
            return null;
        }
    }


    /**
     * 根据 手机号, 查询用户详情
     *
     * @param mobile
     * @return ClientDetails
     * @author Wang Chen Chen
     * @date 2021/7/12 14:40
     */
    public UserInfo selectByMobile(String mobile) {
        var sql = "SELECT * FROM user_info WHERE del_flag = 1 AND mobile = ?";
        try {
            return jdbcTemplate.queryForObject(sql, getUserInfoRowMapper(), mobile);
        } catch (EmptyResultDataAccessException ex) {
            log.error("ex : {}", ex.getMessage());
            return null;
        }
    }


    /**
     * 根据 手机号, 查询用户详情
     *
     * @param openId     社交平台颁发的用户唯一ID
     * @param socialType 社交账号类型，we_chat. 微信  tencent_qq. 腾讯QQ
     * @return ClientDetails
     * @author Wang Chen Chen
     * @date 2021/7/12 14:40
     */
    public UserInfo selectBySocialOpenId(String openId, String socialType) {
        var sql = "SELECT * FROM user_info AS u LEFT JOIN user_social AS us ON u.user_id = us.user_id " +
                "WHERE u.del_flag = 1 AND us.open_id = ? AND us.social_type = ?";
        try {
            return jdbcTemplate.queryForObject(sql, getUserInfoRowMapper(), openId, socialType);
        } catch (EmptyResultDataAccessException ex) {
            log.error("ex : {}", ex.getMessage());
            return null;
        }
    }


    /**
     * 根据 手机号, 查询用户详情
     *
     * @param userId     本平台用户的唯一ID
     * @param openId     社交平台颁发的用户唯一ID
     * @param socialType 社交账号类型，we_chat. 微信  tencent_qq. 腾讯QQ
     * @return ClientDetails
     * @author Wang Chen Chen
     * @date 2021/7/12 14:40
     */
    public int insertSocial(Long userId, String openId, String socialType) {
        var sql = "INSERT INTO `sys_user_social` (`user_id`, `open_id`, `social_type`, `create_time`) VALUES (?, ?, ?, ?, ?);";
        try {
            return jdbcTemplate.update(sql, userId, openId, socialType, LocalDateTime.now());
        } catch (DataAccessException ex) {
            log.error("ex : {}", ex.getMessage());
            return 0;
        }
    }


    /**
     * 根据 邮箱, 查询用户详情
     *
     * @param email
     * @return ClientDetails
     * @author Wang Chen Chen
     * @date 2021/7/12 14:40
     */
    public UserInfo selectByEmail(String email) {
        var sql = "SELECT * FROM sys_user WHERE del_flag = 1 AND email = ?";
        try {
            var userInfo = jdbcTemplate.queryForObject(sql, getUserInfoRowMapper(), email);
            return userInfo;
        } catch (EmptyResultDataAccessException ex) {
            log.error("ex : {}", ex.getMessage());
            return null;
        }
    }


    private RowMapper<UserInfo> getUserInfoRowMapper() {
        return (rs, rowNum) -> {
            var gender = GenderType.genderType(rs.getInt("gender"));
            var adminFlag = rs.getInt("admin_flag") == 0 ? AdminFlag.NO : AdminFlag.YES;
            var status = UserStatus.getUserStatus(rs.getInt("status"));
            LocalDate birthday = rs.getDate("birthday").toLocalDate();
            return UserInfo.builder()
                    .userId(rs.getLong("user_id"))
                    .avatar(rs.getString("avatar"))
                    .username(rs.getString("username"))
                    .mobile(rs.getString("mobile"))
                    .email(rs.getString("email"))
                    .nickname(rs.getString("nickname"))
                    .password(rs.getString("password"))
                    .gender(gender)
                    .birthday(birthday)
                    .status(status)
                    .adminFlag(adminFlag)
                    .build();
        };
    }

}
