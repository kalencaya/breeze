package com.liyu.breeze.service.convert;

import com.liyu.breeze.dao.entity.Dict;
import com.liyu.breeze.service.dto.DictDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * @author gleiyu
 */
@Mapper(uses = {DictTypeConvert.class, DictVoConvert.class}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DictConvert extends BaseConvert<Dict, DictDTO> {
    DictConvert INSTANCE = Mappers.getMapper(DictConvert.class);

    @Mapping(source = "dictType.dictTypeCode", target = "dictTypeCode")
    @Override
    Dict toDo(DictDTO dto);

    @Mapping(source = "dictType", target = "dictType")
    @Mapping(source = "dictTypeCode", target = "dictType.dictTypeCode")
    @Override
    DictDTO toDto(Dict entity);
}
