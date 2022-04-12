package com.liyu.breeze.service.di;

import com.liyu.breeze.service.dto.DiJobStepAttrTypeDTO;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 数据集成-作业步骤参数类型信息 服务类
 * </p>
 *
 * @author liyu
 * @since 2022-03-30
 */
public interface DiJobStepAttrTypeService {

    /**
     * 查询全部参数说明，
     *
     * @return map
     */
    List<DiJobStepAttrTypeDTO> listByType(String stepType,String stepName);
}
