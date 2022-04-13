package com.liyu.breeze.service.convert;

import com.liyu.breeze.dao.entity.DiResourceFile;
import com.liyu.breeze.service.dto.DiResourceFileDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * @author gleiyu
 */
@Mapper(uses = {}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DiResourceFileConvert extends BaseConvert<DiResourceFile, DiResourceFileDTO> {
    DiResourceFileConvert INSTANCE = Mappers.getMapper(DiResourceFileConvert.class);

}
