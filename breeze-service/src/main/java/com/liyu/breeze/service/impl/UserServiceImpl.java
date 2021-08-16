package com.liyu.breeze.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liyu.breeze.common.enums.UserStatusEnum;
import com.liyu.breeze.dao.entity.User;
import com.liyu.breeze.dao.mapper.UserMapper;
import com.liyu.breeze.service.UserService;
import com.liyu.breeze.service.convert.UserConvert;
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
 * todo 删除关联数据
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
        Page<UserDTO> result = new Page<>();
        Page<User> list = this.userMapper.selectPage(
                new Page<>(userParam.getCurrent(), userParam.getPageSize()),
                new LambdaQueryWrapper<User>()
                        .like(StrUtil.isNotEmpty(userParam.getUserName()), User::getUserName, userParam.getUserName())
                        .like(StrUtil.isNotEmpty(userParam.getNickName()), User::getNickName, userParam.getNickName())
                        .like(StrUtil.isNotEmpty(userParam.getEmail()), User::getEmail, userParam.getEmail())
                        .eq(StrUtil.isNotEmpty(userParam.getUserStatus()), User::getUserStatus, userParam.getUserStatus())
        );
        List<UserDTO> dtoList = UserConvert.INSTANCE.toDto(list.getRecords());
        result.setCurrent(list.getCurrent());
        result.setSize(list.getSize());
        result.setRecords(dtoList);
        result.setTotal(list.getTotal());
        return result;
    }
}
