package com.liyu.breeze.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liyu.breeze.dao.entity.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    /**
     * 批量修改用户状态
     *
     * @param idList     用户id列表
     * @param userStatus 用户状态
     * @return int
     */
    @Update({"<script>" +
            "update t_user " +
            "set user_status = #{userStatus} " +
            "where id in " +
            "<foreach item=\"id\" index=\"index\" collection =\"idList\" open=\"(\" close=\")\" separator=\",\"> #{id}</foreach>" +
            "</script>"})
    int batchUpdateUserStatus(@Param("idList") List<Integer> idList, @Param("userStatus") String userStatus);
}
