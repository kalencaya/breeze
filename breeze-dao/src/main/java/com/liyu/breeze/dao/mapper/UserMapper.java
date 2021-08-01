package com.liyu.breeze.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liyu.breeze.dao.entity.User;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 用户基本信息表 Mapper 接口
 * </p>
 *
 * @author liyu
 * @since 2021-08-01
 */
@Repository
public interface UserMapper extends BaseMapper<User> {

}
