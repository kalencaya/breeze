package com.liyu.breeze.service;

import com.liyu.breeze.service.dto.RoleDTO;
import com.liyu.breeze.service.dto.UserDTO;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @author liyu
 * @since 2021-08-01
 */
public interface RoleService {
    /**
     * 新增角色
     *
     * @param roleDTO role
     * @return int
     */
    int insert(RoleDTO roleDTO);

    /**
     * 修改角色
     *
     * @param roleDTO role
     * @return int
     */
    int update(RoleDTO roleDTO);

    /**
     * 删除角色
     *
     * @param id id
     * @return int
     */
    int deleteById(Long id);

    /**
     * 批量删除角色
     *
     * @param map ids
     * @return int
     */
    int deleteBatch(Map<Integer, ? extends Serializable> map);

    /**
     * 根据id查询
     *
     * @param id user id
     * @return user
     */
    RoleDTO selectOne(Long id);

    /**
     * 根据角色编码查询
     *
     * @param roleCode roleCode
     * @return user
     */
    RoleDTO selectOne(String roleCode);

    /**
     * 查询全部角色
     *
     * @return list
     */
    List<RoleDTO> listAll();
}
