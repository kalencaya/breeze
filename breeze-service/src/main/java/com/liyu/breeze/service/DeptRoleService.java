package com.liyu.breeze.service;

import java.io.Serializable;

/**
 * <p>
 * 部门角色关联表 服务类
 * </p>
 *
 * @author liyu
 * @since 2021-08-01
 */
public interface DeptRoleService {
    /**
     * 根据部门删除
     *
     * @param deptId deptid
     * @return int
     */
    int deleteBydeptId(Serializable deptId);

    /**
     * 根据角色删除
     *
     * @param roleId role id
     * @return int
     */
    int deleteByRoleId(Serializable roleId);
}
