package com.liyu.breeze.api.controller.admin;


import cn.hutool.extra.servlet.ServletUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liyu.breeze.api.annotation.AnonymousAccess;
import com.liyu.breeze.api.annotation.Logging;
import com.liyu.breeze.api.vo.ResponseVO;
import com.liyu.breeze.api.vo.TransferVO;
import com.liyu.breeze.common.constant.DictConstants;
import com.liyu.breeze.common.enums.RegisterChannelEnum;
import com.liyu.breeze.common.enums.UserStatusEnum;
import com.liyu.breeze.service.EmailService;
import com.liyu.breeze.service.UserService;
import com.liyu.breeze.service.dto.UserDTO;
import com.liyu.breeze.service.param.UserParam;
import com.liyu.breeze.service.vo.DictVO;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.text.RandomStringGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户基本信息表 前端控制器
 * </p>
 *
 * @author liyu
 */
@RestController
@RequestMapping("/api")
public class UserController {

    @Value("${app.name}")
    private String appName;
    @Value("${app.host}")
    private String appHost;

    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private EmailService emailService;

    @Logging
    @PostMapping(path = "/admin/user")
    @ApiOperation(value = "新增用户", notes = "新增用户")
    public ResponseEntity<ResponseVO> addUser(@Validated @RequestBody UserDTO userDTO, HttpServletRequest httpServletRequest) {
        Date date = new Date();
        userDTO.setRegisterTime(date);
        String randomPassword = this.randomPasswordGenerate(10);
        userDTO.setPassword(this.passwordEncoder.encode(randomPassword));
        userDTO.setUserStatus(DictVO.toVO(DictConstants.USER_STATUS, UserStatusEnum.UNBIND_EMAIL.getValue()));
        userDTO.setRegisterChannel(DictVO.toVO(DictConstants.REGISTER_CHANNEL, RegisterChannelEnum.BACKGROUND_IMPORT.getValue()));
        String ipAdderss = ServletUtil.getClientIP(httpServletRequest);
        userDTO.setRegisterIp(ipAdderss);
        this.userService.insert(userDTO);
        this.sendConfirmEmail(userDTO, randomPassword);
        //todo 授权默认角色信息
        return new ResponseEntity<>(ResponseVO.sucess(), HttpStatus.CREATED);
    }


    @Logging
    @PutMapping(path = "/admin/user")
    @ApiOperation(value = "修改用户", notes = "修改用户")
    public ResponseEntity<ResponseVO> editUser(@Validated @RequestBody UserDTO userDTO) {
        this.userService.update(userDTO);
        return new ResponseEntity<>(ResponseVO.sucess(), HttpStatus.OK);
    }


    @Logging
    @DeleteMapping(path = "/admin/user/{id}")
    @ApiOperation(value = "删除用户", notes = "根据id删除用户")
    public ResponseEntity<ResponseVO> deleteUser(@PathVariable(value = "id") String id) {
        this.userService.deleteById(Long.valueOf(id));
        return new ResponseEntity<>(ResponseVO.sucess(), HttpStatus.OK);
    }

    @Logging
    @PostMapping(path = "/admin/user/batch")
    @ApiOperation(value = "批量删除用户", notes = "根据id列表批量删除用户")
    public ResponseEntity<ResponseVO> deleteBatchUser(@RequestBody Map<Integer, String> map) {
        this.userService.deleteBatch(map);
        return new ResponseEntity<>(ResponseVO.sucess(), HttpStatus.OK);
    }

    @Logging
    @GetMapping(path = "/admin/user")
    @ApiOperation(value = "分页查询用户", notes = "分页查询用户")
    public ResponseEntity<IPage<UserDTO>> listUser(UserParam userParam) {
        Page<UserDTO> page = this.userService.listByPage(userParam);
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    /**
     * 随机生成密码
     *
     * @param length 密码长度
     * @return 密码
     */
    private String randomPasswordGenerate(int length) {
        //随机生成密码
        char[][] pairs = {{'a', 'z'}, {'A', 'Z'}, {'0', '9'}};
        RandomStringGenerator rsg = new RandomStringGenerator.Builder().withinRange(pairs).build();
        return rsg.generate(length);
    }

    /**
     * 向用户邮箱发送邮件，确认用户注册信息
     *
     * @param userDTO 用户信息
     */
    private void sendConfirmEmail(UserDTO userDTO, String password) {
        String subject = appName + "注册确认";
        // todo active user
        String html = "<html><body><p>" +
                "尊敬的用户：<br/> 感谢您注册" + appName + "，账号" + userDTO.getUserName() + "已开通";
        if (!StringUtils.isEmpty(password)) {
            html = html + "，初始密码为：" + password;
        }
        html = html + "。<br/> 登录后请及时修改密码。" +
                "<br/> </p></body></html>";

        String[] sendTo = {userDTO.getEmail()};
        this.emailService.sendHtmlEmail(sendTo, subject, html);
    }

    /**
     * 验证用户名是否存在
     *
     * @param userName 用户名
     * @return true/false
     */
    @AnonymousAccess
    @ApiOperation(value = "判断用户是否存在", notes = "根据用户名，判断用户是否存在")
    @GetMapping(path = "/user/validation/userName")
    public ResponseEntity<Boolean> isUserNameExists(String userName) {
        UserDTO user = this.userService.selectOne(userName);
        return new ResponseEntity<>(user == null, HttpStatus.OK);
    }

    /**
     * 验证用户邮箱是否存在
     *
     * @param email 邮箱地址
     * @return true/false
     */
    @AnonymousAccess
    @ApiOperation(value = "判断邮箱是否存在", notes = "根据邮箱，判断用户是否存在")
    @GetMapping(path = "/user/validation/email")
    public ResponseEntity<Boolean> isEmailExists(String email) {
        UserDTO user = this.userService.selectByEmail(email);
        return new ResponseEntity<>(user == null, HttpStatus.OK);
    }

    @ApiOperation(value = "根据用户名查询用户列表", notes = "根据用户名查询用户列表")
    @GetMapping(path = "/user/{userName}")
    public ResponseEntity<List<TransferVO>> listUserByUserName(@PathVariable(value = "userName") String userName) {
        List<TransferVO> result = new ArrayList<>();
        List<UserDTO> userList = this.userService.listByUserName(userName);
        userList.forEach(d -> {
            result.add(new TransferVO(String.valueOf(d.getId()), d.getUserName()));
        });
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * 配合前端穿梭框查询用户列表
     *
     * @param userName  用户名
     * @param roleId    角色id
     * @param direction 1:target 0:source
     * @return user list
     */
    @ApiOperation(value = "查询角色下用户列表", notes = "配合前端穿梭框查询用户列表")
    @PostMapping(path = "/user/role")
    public ResponseEntity<List<TransferVO>> listUserByUserAndRole(String userName, Long roleId, String direction) {
        List<TransferVO> result = new ArrayList<>();
        List<UserDTO> userList = this.userService.listByRole(roleId, userName, direction);
        userList.forEach(d -> {
            result.add(new TransferVO(String.valueOf(d.getId()), d.getUserName()));
        });
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * 配合前端穿梭框查询用户列表
     *
     * @param userName  用户名
     * @param deptId    dept id
     * @param direction 1:target 0:source
     * @return user list
     */
    @ApiOperation(value = "查询部门下用户列表", notes = "配合前端穿梭框查询用户列表")
    @PostMapping(path = "/user/dept")
    public ResponseEntity<List<TransferVO>> listUserByUserAndDept(String userName, Long deptId, String direction) {
        List<TransferVO> result = new ArrayList<>();
        List<UserDTO> userList = this.userService.listByDept(deptId, userName, direction);
        userList.forEach(d -> {
            result.add(new TransferVO(String.valueOf(d.getId()), d.getUserName()));
        });
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}

