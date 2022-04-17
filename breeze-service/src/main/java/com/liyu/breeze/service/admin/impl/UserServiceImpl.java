package com.liyu.breeze.service.admin.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liyu.breeze.common.enums.UserStatusEnum;
import com.liyu.breeze.dao.entity.Role;
import com.liyu.breeze.dao.entity.User;
import com.liyu.breeze.dao.mapper.UserMapper;
import com.liyu.breeze.service.admin.UserService;
import com.liyu.breeze.service.convert.RoleConvert;
import com.liyu.breeze.service.convert.UserConvert;
import com.liyu.breeze.service.dto.RoleDTO;
import com.liyu.breeze.service.dto.UserDTO;
import com.liyu.breeze.service.param.UserParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户基本信息表 服务实现类
 * </p>
 *
 * @author liyu
 * @since 2021-08-01
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public int insert(UserDTO userDTO) {
        User user = UserConvert.INSTANCE.toDo(userDTO);
        return this.userMapper.insert(user);
    }

    @Override
    public int update(UserDTO userDTO) {
        User user = UserConvert.INSTANCE.toDo(userDTO);
        return this.userMapper.updateById(user);
    }

    @Override
    public int updateByUserName(UserDTO userDTO) {
        User user = UserConvert.INSTANCE.toDo(userDTO);
        return this.userMapper.update(user, new LambdaQueryWrapper<User>()
                .eq(User::getUserName, user.getUserName())
        );
    }

    @Override
    public int deleteById(Long id) {
        User user = new User();
        user.setId(id);
        user.setUserStatus(UserStatusEnum.LOGOFF.getValue());
        return this.userMapper.updateById(user);
    }

    @Override
    public int deleteBatch(Map<Integer, ? extends Serializable> map) {
        List<Integer> list = new ArrayList<>();
        for (Map.Entry<Integer, ? extends Serializable> entry : map.entrySet()) {
            list.add((Integer) entry.getValue());
        }

        return this.userMapper.batchUpdateUserStatus(list, UserStatusEnum.LOGOFF.getValue());

    }

    @Override
    public UserDTO selectOne(Long id) {
        User user = this.userMapper.selectById(id);
        return UserConvert.INSTANCE.toDto(user);
    }

    @Override
    public UserDTO selectOne(String userName) {
        User user = this.userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getUserName, userName));
        return UserConvert.INSTANCE.toDto(user);
    }

    @Override
    public Page<UserDTO> listByPage(UserParam userParam) {
        User user = new User();
        user.setUserName(userParam.getUserName());
        user.setNickName(userParam.getNickName());
        user.setEmail(userParam.getEmail());
        user.setUserStatus(userParam.getUserStatus());
        Page<UserDTO> result = new Page<>();
        Page<User> list = this.userMapper.selectPage(
                new Page<>(userParam.getCurrent(), userParam.getPageSize()),
                userParam.getDeptId(),
                userParam.getRoleId(),
                user
        );
        List<UserDTO> dtoList = UserConvert.INSTANCE.toDto(list.getRecords());
        result.setCurrent(list.getCurrent());
        result.setSize(list.getSize());
        result.setRecords(dtoList);
        result.setTotal(list.getTotal());
        return result;
    }

    @Override
    public UserDTO selectByEmail(String email) {
        User user = this.userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getEmail, email));
        return UserConvert.INSTANCE.toDto(user);
    }

    @Override
    public List<UserDTO> listByRole(Long roleId, String userName, String direction) {
        List<User> list = this.userMapper.selectByRoleOrDept("", String.valueOf(roleId), userName, direction);
        return UserConvert.INSTANCE.toDto(list);
    }

    @Override
    public List<UserDTO> listByDept(Long deptId, String userName, String direction) {
        List<User> list = this.userMapper.selectByRoleOrDept(String.valueOf(deptId), "", userName, direction);
        return UserConvert.INSTANCE.toDto(list);
    }

    @Override
    public List<UserDTO> listByUserName(String userName) {
        List<User> list = this.userMapper.selectList(new LambdaQueryWrapper<User>()
                .like(User::getUserName, userName));
        return UserConvert.INSTANCE.toDto(list);
    }

    @Override
    public List<RoleDTO> getAllPrivilegeByUserName(String userName) {
        List<Role> list = this.userMapper.selectAllPrivilege(userName);
        return RoleConvert.INSTANCE.toDto(list);
    }
}
