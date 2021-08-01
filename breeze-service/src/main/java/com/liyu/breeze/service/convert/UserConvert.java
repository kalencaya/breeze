package com.liyu.breeze.service.convert;

import com.liyu.breeze.dao.entity.User;
import com.liyu.breeze.service.dto.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * @author gleiyu
 */
@Mapper(uses = DictVoConvert.class)
public interface UserConvert extends BaseConvert<User, UserDTO> {

    @Override
    @Mapping(expression = "java(com.liyu.breeze.service.vo.DictVO.toVO(\"id_card_type\",entity.getIdCardType()))", target = "idCardType")
    @Mapping(expression = "java(com.liyu.breeze.service.vo.DictVO.toVO(\"gender\",entity.getGender()))", target = "gender")
    @Mapping(expression = "java(com.liyu.breeze.service.vo.DictVO.toVO(\"nation\",entity.getNation()))", target = "nation")
    @Mapping(expression = "java(com.liyu.breeze.service.vo.DictVO.toVO(\"user_status\",entity.getUserStatus()))", target = "userStatus")
    @Mapping(expression = "java(com.liyu.breeze.service.vo.DictVO.toVO(\"register_channel\",entity.getRegisterChannel()))", target = "registerChannel")
    UserDTO toDto(User entity);
}
