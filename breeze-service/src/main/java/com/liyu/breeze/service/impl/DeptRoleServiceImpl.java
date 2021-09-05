package com.liyu.breeze.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.liyu.breeze.dao.entity.DeptRole;
import com.liyu.breeze.dao.mapper.DeptRoleMapper;
import com.liyu.breeze.service.DeptRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
 * <p>
 * 部门角色关联表 服务实现类
 * </p>
 *
 * @author liyu
 * @since 2021-08-01
 */
@Service
public class DeptRoleServiceImpl implements DeptRoleService {

    @Autowired
    private DeptRoleMapper deptRoleMapper;

    @Override
    public int deleteBydeptId(Serializable deptId) {
        return this.deptRoleMapper.delete(new LambdaQueryWrapper<DeptRole>()
                .eq(DeptRole::getDeptId, deptId));
    }

    @Override
    public int deleteByRoleId(Serializable roleId) {
        return this.deptRoleMapper.delete(new LambdaQueryWrapper<DeptRole>()
                .eq(DeptRole::getRoleId, roleId));
    }
}
