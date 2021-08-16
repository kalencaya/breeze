package com.liyu.breeze.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liyu.breeze.service.dto.UserDTO;
import com.liyu.breeze.service.param.DictTypeParam;
import com.liyu.breeze.service.param.UserParam;

import java.io.Serializable;
import java.util.Map;

/**
 * <p>
 * 用户基本信息表 服务类
 * </p>
 *
 * @author liyu
 * @since 2021-08-01
 */
public interface UserService {
    /**
     * 新增用户
     *
     * @param userDTO user
     * @return int
     */
    int insert(UserDTO userDTO);

    /**
     * 修改用户
     *
     * @param userDTO user
     * @return int
     */
    int update(UserDTO userDTO);

    /**
     * 逻辑删除用户，修改用户状态为注销
     *
     * @param id id
     * @return int
     */
    int deleteById(Long id);

    /**
     * 逻辑删除用户，修改用户状态为注销
     *
     * @param map ids
     * @return int
     */
    int deleteBatch(Map<Integer, ? extends Serializable> map);

    /**
     * 根据id查询用户
     *
     * @param id user id
     * @return user
     */
    UserDTO selectOne(Long id);

    /**
     * 根据用户名查询用户
     *
     * @param userName user name
     * @return user
     */
    UserDTO selectOne(String userName);

    /**
     * 分页查询
     *
     * @param userParam 查询参数
     * @return page list
     */
    Page<UserDTO> listByPage(UserParam userParam);

}
