package com.xaaef.authorize.server.repository;

import com.xaaef.authorize.common.domain.ClientDetails;
import com.xaaef.authorize.common.enums.ClientType;
import com.xaaef.authorize.common.util.JsonUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

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
public class ClientDetailsRepository {

    private JdbcTemplate jdbcTemplate;

    /**
     * 根据客户端ID,查询客户端详情
     *
     * @param clientId
     * @return ClientDetails
     * @author Wang Chen Chen
     * @date 2021/7/12 14:40
     */
    public ClientDetails selectById(String clientId) {
        var sql = "SELECT * FROM client_details WHERE del_flag = 1 AND client_id = ?";
        try {
            var clientDetails = jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
                var clientType = rs.getInt("client_type") == 0 ? ClientType.OTHER : ClientType.SYSTEM;
                var grantTypesStr = rs.getString("grant_types").toLowerCase();
                List<String> grantTypes = null;
                if (StringUtils.isNotEmpty(grantTypesStr)) {
                    grantTypes = JsonUtils.toListPojo(grantTypesStr, String.class);
                }
                return ClientDetails.builder()
                        .clientId(rs.getString("client_id"))
                        .secret(rs.getString("secret"))
                        .name(rs.getString("name"))
                        .logo(rs.getString("logo"))
                        .description(rs.getString("description"))
                        .clientType(clientType)
                        .grantTypes(grantTypes)
                        .domainName(rs.getString("domain_name"))
                        .scope(rs.getString("scope"))
                        .build();
            }, clientId);
            return clientDetails;
        } catch (EmptyResultDataAccessException ex) {
            log.error("ex : {}", ex.getMessage());
            return null;
        }
    }

}
