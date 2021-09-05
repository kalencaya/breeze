package com.liyu.breeze.service;

import java.io.Serializable;

/**
 * <p>
 * 角色权限关联表 服务类
 * </p>
 *
 * @author liyu
 * @since 2021-08-01
 */
public interface RolePrivilegeService {
    /**
     * 根据角色删除
     *
     * @param roleId roleid
     * @return int
     */
    int deleteByRoleId(Serializable roleId);
}
