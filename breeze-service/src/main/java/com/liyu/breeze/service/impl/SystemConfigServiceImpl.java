package com.liyu.breeze.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.liyu.breeze.dao.entity.SystemConfig;
import com.liyu.breeze.dao.mapper.SystemConfigMapper;
import com.liyu.breeze.service.SystemConfigService;
import com.liyu.breeze.service.convert.SystemConfigConvert;
import com.liyu.breeze.service.dto.SystemConfigDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统配置信息表 服务实现类
 * </p>
 *
 * @author liyu
 * @since 2021-10-10
 */
@Service
public class SystemConfigServiceImpl implements SystemConfigService {

    @Autowired
    private SystemConfigMapper systemConfigMapper;

    @Override
    public int insert(SystemConfigDTO systemConfigDTO) {
        SystemConfig systemConfig = SystemConfigConvert.INSTANCE.toDo(systemConfigDTO);
        return this.systemConfigMapper.insert(systemConfig);
    }

    @Override
    public int updateByCode(SystemConfigDTO systemConfigDTO) {
        SystemConfig systemConfig = SystemConfigConvert.INSTANCE.toDo(systemConfigDTO);
        return this.systemConfigMapper.update(systemConfig, new LambdaQueryWrapper<SystemConfig>()
                .eq(SystemConfig::getCfgCode, systemConfig.getCfgCode()));
    }

    @Override
    public int deleteByCode(String code) {
        if (StrUtil.isEmpty(code)) {
            return 0;
        } else {
            return this.systemConfigMapper.delete(new LambdaQueryWrapper<SystemConfig>()
                    .eq(SystemConfig::getCfgCode, code));
        }

    }

    @Override
    public SystemConfigDTO selectByCode(String code) {
        SystemConfig systemConfig = this.systemConfigMapper.selectOne(new LambdaQueryWrapper<SystemConfig>()
                .eq(SystemConfig::getCfgCode, code));
        return SystemConfigConvert.INSTANCE.toDto(systemConfig);
    }
}
