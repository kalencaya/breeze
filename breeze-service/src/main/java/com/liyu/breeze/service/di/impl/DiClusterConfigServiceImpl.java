package com.liyu.breeze.service.di.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liyu.breeze.dao.entity.DiClusterConfig;
import com.liyu.breeze.dao.mapper.DiClusterConfigMapper;
import com.liyu.breeze.service.convert.DiClusterConfigConvert;
import com.liyu.breeze.service.di.DiClusterConfigService;
import com.liyu.breeze.service.dto.DiClusterConfigDTO;
import com.liyu.breeze.service.param.DiClusterConfigParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Service
public class DiClusterConfigServiceImpl implements DiClusterConfigService {

    @Autowired
    private DiClusterConfigMapper diClusterConfigMapper;

    @Override
    public int insert(DiClusterConfigDTO dto) {
        DiClusterConfig config = DiClusterConfigConvert.INSTANCE.toDo(dto);
        return this.diClusterConfigMapper.insert(config);
    }

    @Override
    public int update(DiClusterConfigDTO dto) {
        DiClusterConfig config = DiClusterConfigConvert.INSTANCE.toDo(dto);
        return this.diClusterConfigMapper.updateById(config);
    }

    @Override
    public int deleteById(Long id) {
        return this.diClusterConfigMapper.deleteById(id);
    }

    @Override
    public int deleteBatch(Map<Integer, ? extends Serializable> map) {
        return this.diClusterConfigMapper.deleteBatchIds(map.values());
    }

    @Override
    public Page<DiClusterConfigDTO> listByPage(DiClusterConfigParam param) {
        Page<DiClusterConfigDTO> result = new Page<>();
        Page<DiClusterConfig> list = this.diClusterConfigMapper.selectPage(
                new Page<>(param.getCurrent(), param.getPageSize()),
                new LambdaQueryWrapper<DiClusterConfig>()
                        .like(StrUtil.isNotEmpty(param.getClusterName()), DiClusterConfig::getClusterName, param.getClusterName())
                        .eq(StrUtil.isNotEmpty(param.getClusterType()), DiClusterConfig::getClusterType, param.getClusterType())
        );
        List<DiClusterConfigDTO> dtoList = DiClusterConfigConvert.INSTANCE.toDto(list.getRecords());
        result.setCurrent(list.getCurrent());
        result.setSize(list.getSize());
        result.setRecords(dtoList);
        result.setTotal(list.getTotal());
        return result;
    }
}
