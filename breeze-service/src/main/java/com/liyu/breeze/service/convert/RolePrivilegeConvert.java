package com.liyu.breeze.service.convert;

import com.liyu.breeze.dao.entity.UserDept;
import com.liyu.breeze.service.dto.UserDeptDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author gleiyu
 */
@Mapper
public interface UserDeptConvert extends BaseConvert<UserDept, UserDeptDTO> {

    UserDeptConvert INSTANCE = Mappers.getMapper(UserDeptConvert.class);
}
