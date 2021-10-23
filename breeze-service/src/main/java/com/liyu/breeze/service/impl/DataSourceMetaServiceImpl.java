package com.liyu.breeze.service.impl;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liyu.breeze.dao.entity.DataSourceMeta;
import com.liyu.breeze.dao.mapper.DataSourceMetaMapper;
import com.liyu.breeze.service.DataSourceMetaService;
import com.liyu.breeze.service.convert.DataSourceMetaConvert;
import com.liyu.breeze.service.dto.DataSourceMetaDTO;
import com.liyu.breeze.service.param.DataSourceMetaParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author gleiyu
 */
@Service
public class DataSourceMetaServiceImpl implements DataSourceMetaService {

    @Autowired
    private DataSourceMetaMapper dataSourceMetaMapper;

    @Override
    public int insert(DataSourceMetaDTO metaDTO) {
        if (StrUtil.isNotEmpty(metaDTO.getPassword())) {
            String encodePasswd = Base64.encode(metaDTO.getPassword());
            metaDTO.setPassword(encodePasswd);
        }
        DataSourceMeta meta = DataSourceMetaConvert.INSTANCE.toDo(metaDTO);
        return this.dataSourceMetaMapper.insert(meta);
    }

    @Override
    public int update(DataSourceMetaDTO metaDTO) {
        if (StrUtil.isNotEmpty(metaDTO.getPassword())) {
            String encodePasswd = Base64.encode(metaDTO.getPassword());
            metaDTO.setPassword(encodePasswd);
        }
        DataSourceMeta meta = DataSourceMetaConvert.INSTANCE.toDo(metaDTO);
        return this.dataSourceMetaMapper.updateById(meta);
    }

    @Override
    public int deleteById(Long id) {
        return this.dataSourceMetaMapper.deleteById(id);
    }

    @Override
    public int deleteBatch(Map<Integer, ? extends Serializable> map) {
        return this.dataSourceMetaMapper.deleteBatchIds(map.values());
    }

    @Override
    public DataSourceMetaDTO selectOne(Long id) {
        DataSourceMeta meta = this.dataSourceMetaMapper.selectById(id);
        return DataSourceMetaConvert.INSTANCE.toDto(meta);
    }

    @Override
    public Page<DataSourceMetaDTO> listByPage(DataSourceMetaParam param) {
        Page<DataSourceMetaDTO> result = new Page<>();
        Page<DataSourceMeta> list = this.dataSourceMetaMapper.selectPage(
                new Page<>(param.getCurrent(), param.getPageSize()),
                Wrappers.lambdaQuery(DataSourceMeta.class)
                        .eq(StrUtil.isNotEmpty(param.getDatabaseName()), DataSourceMeta::getDatabaseName, param.getDatabaseName())
                        .eq(StrUtil.isNotEmpty(param.getDataSourceType()), DataSourceMeta::getDatasourceType, param.getDataSourceType())
                        .eq(StrUtil.isNotEmpty(param.getHostName()), DataSourceMeta::getHostName, param.getHostName())
                        .like(StrUtil.isNotEmpty(param.getDataSourceName()), DataSourceMeta::getDatasourceName, param.getDataSourceName())
        );
        List<DataSourceMetaDTO> dtoList = DataSourceMetaConvert.INSTANCE.toDto(list.getRecords());
        result.setCurrent(list.getCurrent());
        result.setSize(list.getSize());
        result.setRecords(dtoList);
        result.setTotal(list.getTotal());
        return result;
    }
}
