package com.liyu.breeze.service.di;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liyu.breeze.service.dto.di.DiClusterConfigDTO;
import com.liyu.breeze.service.param.di.DiClusterConfigParam;

import java.io.Serializable;
import java.util.Map;

public interface DiClusterConfigService {

    int insert(DiClusterConfigDTO dto);

    int update(DiClusterConfigDTO dto);

    int deleteById(Long id);

    int deleteBatch(Map<Integer, ? extends Serializable> map);

    Page<DiClusterConfigDTO> listByPage(DiClusterConfigParam param);

}
