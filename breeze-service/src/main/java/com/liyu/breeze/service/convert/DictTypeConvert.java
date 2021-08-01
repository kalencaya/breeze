package com.liyu.breeze.service.convert;

import com.liyu.breeze.dao.entity.DictType;
import com.liyu.breeze.service.dto.DictTypeDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * @author gleiyu
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DictTypeConvert extends BaseConvert<DictType, DictTypeDTO> {
    DictTypeConvert INSTANCE = Mappers.getMapper(DictTypeConvert.class);
}
