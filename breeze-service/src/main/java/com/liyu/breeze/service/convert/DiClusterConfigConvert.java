package com.liyu.breeze.service.convert;

import com.liyu.breeze.dao.entity.DiClusterConfig;
import com.liyu.breeze.service.dto.DiClusterConfigDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * @author gleiyu
 */
@Mapper(uses = {DictVoConvert.class}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DiClusterConfigConvert extends BaseConvert<DiClusterConfig, DiClusterConfigDTO> {
    DiClusterConfigConvert INSTANCE = Mappers.getMapper(DiClusterConfigConvert.class);

    @Override
    @Mapping(expression = "java(com.liyu.breeze.service.vo.DictVO.toVO(com.liyu.breeze.common.constant.DictConstants.CLUSTER_TYPE,entity.getClusterType()))", target = "clusterType")
    DiClusterConfigDTO toDto(DiClusterConfig entity);
}
