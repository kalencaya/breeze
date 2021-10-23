package com.liyu.breeze.service.convert;

import com.liyu.breeze.dao.entity.LogLogin;
import com.liyu.breeze.dao.entity.ScheduleLog;
import com.liyu.breeze.service.dto.LogLoginDTO;
import com.liyu.breeze.service.dto.ScheduleLogDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * @author gleiyu
 */
@Mapper(uses = {DictVoConvert.class}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ScheduleLogConvert extends BaseConvert<ScheduleLog, ScheduleLogDTO> {
    ScheduleLogConvert INSTANCE = Mappers.getMapper(ScheduleLogConvert.class);

    @Override
    @Mapping(expression = "java(com.liyu.breeze.service.vo.DictVO.toVO(com.liyu.breeze.common.constant.DictConstants.TASK_RESULT,entity.getResult()))", target = "result")
    ScheduleLogDTO toDto(ScheduleLog entity);
}
