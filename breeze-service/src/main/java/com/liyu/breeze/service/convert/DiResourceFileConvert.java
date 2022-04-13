package com.liyu.breeze.service.convert;

import com.liyu.breeze.dao.entity.DiJobLink;
import com.liyu.breeze.service.dto.DiJobLinkDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * @author gleiyu
 */
@Mapper(uses = {}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DiJobLinkConvert extends BaseConvert<DiJobLink, DiJobLinkDTO> {
    DiJobLinkConvert INSTANCE = Mappers.getMapper(DiJobLinkConvert.class);

}
