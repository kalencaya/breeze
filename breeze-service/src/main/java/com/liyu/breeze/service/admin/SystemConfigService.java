package com.liyu.breeze.service.admin;

import com.liyu.breeze.service.dto.SystemConfigDTO;

/**
 * <p>
 * 系统配置信息表 服务类
 * </p>
 *
 * @author liyu
 * @since 2021-10-10
 */
public interface SystemConfigService {

    int insert(SystemConfigDTO systemConfigDTO);

    int updateByCode(SystemConfigDTO systemConfigDTO);

    int deleteByCode(String code);

    SystemConfigDTO selectByCode(String code);

    String getSeatunnelHome();
}
