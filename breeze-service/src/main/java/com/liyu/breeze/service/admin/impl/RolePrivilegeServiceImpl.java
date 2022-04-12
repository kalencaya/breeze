package com.liyu.breeze.service.admin.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.liyu.breeze.dao.entity.RolePrivilege;
import com.liyu.breeze.dao.mapper.RolePrivilegeMapper;
import com.liyu.breeze.service.admin.RolePrivilegeService;
import com.liyu.breeze.service.convert.RolePrivilegeConvert;
import com.liyu.breeze.service.dto.RolePrivilegeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 角色权限关联表 服务实现类
 * </p>
 *
 * @author liyu
 * @since 2021-08-01
 */
@Service
public class RolePrivilegeServiceImpl implements RolePrivilegeService {

    @Autowired
    private RolePrivilegeMapper rolePrivilegeMapper;

    @Override
    public int deleteByRoleId(Serializable roleId) {
        return this.rolePrivilegeMapper.delete(new LambdaQueryWrapper<RolePrivilege>()
                .eq(RolePrivilege::getRoleId, roleId));
    }

    @Override
    public int insert(RolePrivilegeDTO rolePrivilegeDTO) {
        RolePrivilege rp = RolePrivilegeConvert.INSTANCE.toDo(rolePrivilegeDTO);
        return this.rolePrivilegeMapper.insert(rp);
    }

    @Override
    public int delete(RolePrivilegeDTO rolePrivilegeDTO) {
        return this.rolePrivilegeMapper.deleteById(rolePrivilegeDTO.getId());
    }

    @Override
    public List<RolePrivilegeDTO> listByRoleId(Serializable roleId) {
        List<RolePrivilege> list = this.rolePrivilegeMapper.selectList(new LambdaQueryWrapper<RolePrivilege>()
                .eq(RolePrivilege::getRoleId, roleId));
        return RolePrivilegeConvert.INSTANCE.toDto(list);
    }

    @Override
    public List<RolePrivilegeDTO> listByRoleId(Serializable roleId, String resourceType) {
        List<RolePrivilege> list = this.rolePrivilegeMapper.selectByRoleId(roleId, resourceType);
        return RolePrivilegeConvert.INSTANCE.toDto(list);
    }
}
