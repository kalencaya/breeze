package com.liyu.breeze.api.controller.admin;


import cn.hutool.json.JSONUtil;
import com.liyu.breeze.api.annotation.Logging;
import com.liyu.breeze.api.vo.ResponseVO;
import com.liyu.breeze.common.constant.Constants;
import com.liyu.breeze.common.enums.RoleTypeEnum;
import com.liyu.breeze.service.RoleService;
import com.liyu.breeze.service.UserRoleService;
import com.liyu.breeze.service.dto.RoleDTO;
import com.liyu.breeze.service.dto.UserRoleDTO;
import com.liyu.breeze.service.vo.DictVO;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 角色表 前端控制器
 * </p>
 *
 * @author liyu
 */
@RestController
@RequestMapping("/api/admin/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserRoleService userRoleService;

    @Logging
    @GetMapping
    @ApiOperation(value = "查询角色列表", notes = "查询全部角色信息")
    public ResponseEntity<List<RoleDTO>> listAll() {
        List<RoleDTO> list = this.roleService.listAll();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @Logging
    @PostMapping
    @ApiOperation(value = "新增角色", notes = "新增角色")
    public ResponseEntity<ResponseVO> addRole(@Validated @RequestBody RoleDTO roleDTO) {
        if (roleDTO.getRoleType() == null) {
            roleDTO.setRoleType(new DictVO(RoleTypeEnum.USER_DEF.getValue(), RoleTypeEnum.USER_DEF.getLabel()));
        }
        String roleCode = Constants.USER_DEFINE_ROLE_PREFIX + roleDTO.getRoleCode();
        roleDTO.setRoleCode(roleCode);
        this.roleService.insert(roleDTO);
        return new ResponseEntity<>(ResponseVO.sucess(), HttpStatus.CREATED);
    }


    @Logging
    @PutMapping
    @ApiOperation(value = "修改角色", notes = "修改角色")
    public ResponseEntity<ResponseVO> editRole(@Validated @RequestBody RoleDTO roleDTO) {
        this.roleService.update(roleDTO);
        return new ResponseEntity<>(ResponseVO.sucess(), HttpStatus.CREATED);
    }

    @Logging
    @DeleteMapping(path = "/{id}")
    @ApiOperation(value = "删除角色", notes = "删除角色")
    public ResponseEntity<ResponseVO> deleteRole(@PathVariable(value = "id") String id) {
        this.roleService.deleteById(Long.valueOf(id));
        return new ResponseEntity<>(ResponseVO.sucess(), HttpStatus.OK);
    }

    @Logging
    @PostMapping(path = "/grant")
    @ApiOperation(value = "用户授权角色", notes = "用户授权角色")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<ResponseVO> grantRole(@NotNull Long roleId, @NotNull String userIds) {
        List<Long> userList = JSONUtil.toList(userIds, Long.class);
        List<UserRoleDTO> oldUserList = this.userRoleService.listByRoleId(roleId);
        List<Long> tmpList = new ArrayList<>(userList.size());
        tmpList.addAll(userList);
        //grant new user
        tmpList.removeAll(oldUserList.stream().map(UserRoleDTO::getUserId).collect(Collectors.toList()));
        for (Long userId : tmpList) {
            UserRoleDTO userRole = new UserRoleDTO();
            userRole.setRoleId(roleId);
            userRole.setUserId(userId);
            this.userRoleService.insert(userRole);
        }
        //revoke removed user
        for (UserRoleDTO userRole : oldUserList) {
            if (!userList.contains(userRole.getUserId())) {
                this.userRoleService.delete(userRole);
            }
        }
        return new ResponseEntity<>(ResponseVO.sucess(), HttpStatus.OK);
    }
}

