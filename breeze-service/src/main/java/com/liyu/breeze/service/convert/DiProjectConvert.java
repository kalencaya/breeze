package com.liyu.breeze.service.convert;

import com.liyu.breeze.dao.entity.DiProject;
import com.liyu.breeze.service.dto.DiProjectDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * @author gleiyu
 */
@Mapper(uses = {}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DiProjectConvert extends BaseConvert<DiProject, DiProjectDTO> {
    DiProjectConvert INSTANCE = Mappers.getMapper(DiProjectConvert.class);

}
