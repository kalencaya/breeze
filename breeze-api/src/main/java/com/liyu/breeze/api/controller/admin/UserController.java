package com.liyu.breeze.api.controller.admin;


import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.servlet.ServletUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liyu.breeze.api.annotation.AnonymousAccess;
import com.liyu.breeze.api.annotation.Logging;
import com.liyu.breeze.api.security.OnlineUserService;
import com.liyu.breeze.api.security.TokenProvider;
import com.liyu.breeze.api.security.UserDetailInfo;
import com.liyu.breeze.api.util.I18nUtil;
import com.liyu.breeze.api.vo.*;
import com.liyu.breeze.common.constant.Constants;
import com.liyu.breeze.common.constant.DictConstants;
import com.liyu.breeze.common.enums.ErrorShowTypeEnum;
import com.liyu.breeze.common.enums.RegisterChannelEnum;
import com.liyu.breeze.common.enums.ResponseCodeEnum;
import com.liyu.breeze.common.enums.UserStatusEnum;
import com.liyu.breeze.service.EmailService;
import com.liyu.breeze.service.RoleService;
import com.liyu.breeze.service.UserRoleService;
import com.liyu.breeze.service.UserService;
import com.liyu.breeze.service.dto.RoleDTO;
import com.liyu.breeze.service.dto.UserDTO;
import com.liyu.breeze.service.dto.UserRoleDTO;
import com.liyu.breeze.service.param.UserParam;
import com.liyu.breeze.service.util.RedisUtil;
import com.liyu.breeze.service.vo.DictVO;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.text.RandomStringGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
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
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private RoleService roleService;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private AuthenticationManagerBuilder authenticationManagerBuilder;
    @Autowired
    private TokenProvider tokenProvider;
    @Autowired
    private OnlineUserService onlineUserService;

    /**
     * 用户账号密码登录
     * 单点登录
     *
     * @param loginUser 用户信息
     * @return 登录成功后返回token
     */

    @AnonymousAccess
    @PostMapping(path = "/user/login")
    @ApiOperation(value = "用户登录", notes = "用户登录接口")
    public ResponseEntity<ResponseVO> login(@Validated @RequestBody LoginInfoVO loginUser, HttpServletRequest request) {
        //检查验证码
        String authCode = (String) redisUtil.get(loginUser.getUuid());
        redisUtil.delKeys(loginUser.getUuid());
        if (!StringUtils.isEmpty(authCode) && authCode.equalsIgnoreCase(loginUser.getAuthCode())) {
            try {
                //检查用户名密码
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(loginUser.getUserName(), loginUser.getPassword());
                //spring security框架调用userDetailsService获取用户信息并验证，验证通过后返回一个Authentication对象，存储到线程的SecurityContext中
                Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                //生成token 使用uuid作为token
                String token = tokenProvider.createToken();
                final UserDetailInfo userInfo = (UserDetailInfo) authentication.getPrincipal();
                userInfo.setLoginTime(new Date());
                userInfo.setLoginIpAddress(ServletUtil.getClientIP(request));
                userInfo.setRemember(loginUser.getRemember());
                //查询用户权限信息，同时存储到redis onlineuser中
                List<RoleDTO> roles = userService.getAllPrivilegeByUserName(userInfo.getUsername());
                userInfo.getUser().setRoles(roles);
                //存储信息到redis中
                onlineUserService.insert(userInfo, token);
                //验证成功返回token
                ResponseVO info = ResponseVO.sucess();
                info.setData(token);
                return new ResponseEntity<>(info, HttpStatus.OK);
            } catch (BadCredentialsException | InternalAuthenticationServiceException e) {
                return new ResponseEntity<>(ResponseVO.error(ResponseCodeEnum.ERROR_CUSTOM.getCode(),
                        I18nUtil.get("response.error.login.password"), ErrorShowTypeEnum.ERROR_MESSAGE), HttpStatus.OK);
            }
        } else {
            return new ResponseEntity<>(ResponseVO.error(ResponseCodeEnum.ERROR_CUSTOM.getCode(),
                    I18nUtil.get("response.error.authCode"), ErrorShowTypeEnum.ERROR_MESSAGE), HttpStatus.OK);
        }
    }

    @AnonymousAccess
    @PostMapping(path = "/user/logout")
    @ApiOperation(value = "用户登出", notes = "用户登出接口")
    public ResponseEntity<ResponseVO> logout(@RequestBody String token) {
        if (token != null) {
            this.onlineUserService.logoutByToken(token);
        }
        return new ResponseEntity<>(ResponseVO.sucess(), HttpStatus.OK);
    }

    /**
     * 根据token获取redis中的用户信息
     *
     * @param token token
     * @return 用户及权限角色信息
     */
    @AnonymousAccess
    @GetMapping(path = "/user/get/{token}")
    @ApiOperation(value = "查询用户权限", notes = "根据token信息查询用户所有权限")
    public ResponseEntity<ResponseVO> getOnlineUserInfo(@PathVariable(value = "token") String token) {
        OnlineUserVO onlineUser = this.onlineUserService.getAllPrivilegeByToken(token);
        ResponseVO info = ResponseVO.sucess();
        info.setData(onlineUser);
        return new ResponseEntity<>(info, HttpStatus.OK);
    }

    /**
     * 用户注册
     *
     * @param registerInfo       用户注册信息
     * @param httpServletRequest HttpServletRequest
     * @return OperateInfo
     */
    @Logging
    @AnonymousAccess
    @PostMapping(path = "/user/register")
    @ApiOperation(value = "用户注册", notes = "用户注册接口")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<ResponseVO> register(@Validated @RequestBody RegisterInfoVO registerInfo, HttpServletRequest httpServletRequest) {
        //校验验证码是否一致
        String authCode = (String) redisUtil.get(registerInfo.getUuid());
        redisUtil.delKeys(registerInfo.getUuid());
        if (!StrUtil.isEmpty(authCode) && authCode.equalsIgnoreCase(registerInfo.getAuthCode())) {
            //校验两次输入密码是否一致
            if (registerInfo.getPassword().equals(registerInfo.getConfirmPassword())) {
                Date date = new Date();
                UserDTO userDTO = new UserDTO();
                userDTO.setUserName(registerInfo.getUserName().toLowerCase());
                userDTO.setEmail(registerInfo.getEmail().toLowerCase());
                String password = passwordEncoder.encode(registerInfo.getPassword());
                userDTO.setPassword(password);
                userDTO.setUserStatus(DictVO.toVO(DictConstants.USER_STATUS, UserStatusEnum.UNBIND_EMAIL.getValue()));
                userDTO.setRegisterChannel(DictVO.toVO(DictConstants.REGISTER_CHANNEL, RegisterChannelEnum.REGISTER.getValue()));
                userDTO.setRegisterTime(date);
                //获取客户端ip地址
                String ipAddress = ServletUtil.getClientIP(httpServletRequest);
                userDTO.setRegisterIp(ipAddress);
                this.sendConfirmEmail(userDTO, null);
                this.userService.insert(userDTO);
                //授权普通用户角色
                UserDTO userInfo = this.userService.selectOne(userDTO.getUserName());
                RoleDTO roleDTO = roleService.selectOne(Constants.ROLE_NORMAL);
                UserRoleDTO userRoleDTO = new UserRoleDTO();
                userRoleDTO.setUserId(userInfo.getId());
                userRoleDTO.setRoleId(roleDTO.getId());
                userRoleDTO.setCreateTime(date);
                userRoleDTO.setUpdateTime(date);
                this.userRoleService.insert(userRoleDTO);
                return new ResponseEntity<>(ResponseVO.sucess(), HttpStatus.CREATED);
            } else {
                //前台有验证提示，此处只做返回，不展示
                return new ResponseEntity<>(ResponseVO.error(ResponseCodeEnum.ERROR_CUSTOM.getCode(),
                        I18nUtil.get("response.error"), ErrorShowTypeEnum.SILENT), HttpStatus.OK);
            }
        } else {
            return new ResponseEntity<>(ResponseVO.error(ResponseCodeEnum.ERROR_CUSTOM.getCode(),
                    I18nUtil.get("response.error.authCode"), ErrorShowTypeEnum.ERROR_MESSAGE), HttpStatus.OK);
        }
    }

    @Logging
    @PostMapping(path = "/admin/user")
    @ApiOperation(value = "新增用户", notes = "新增用户")
    @PreAuthorize("@svs.validate(T(com.liyu.breeze.common.constant.PrivilegeConstants).USER_ADD)")
    public ResponseEntity<ResponseVO> addUser(@Validated @RequestBody UserDTO userDTO, HttpServletRequest httpServletRequest) {
        Date date = new Date();
        userDTO.setRegisterTime(date);
        String randomPassword = this.randomPasswordGenerate(10);
        userDTO.setPassword(this.passwordEncoder.encode(randomPassword));
        userDTO.setUserStatus(DictVO.toVO(DictConstants.USER_STATUS, UserStatusEnum.UNBIND_EMAIL.getValue()));
        userDTO.setRegisterChannel(DictVO.toVO(DictConstants.REGISTER_CHANNEL, RegisterChannelEnum.BACKGROUND_IMPORT.getValue()));
        String ipAddress = ServletUtil.getClientIP(httpServletRequest);
        userDTO.setRegisterIp(ipAddress);
        this.userService.insert(userDTO);
        this.sendConfirmEmail(userDTO, randomPassword);
        //授权普通用户角色
        UserDTO userInfo = this.userService.selectOne(userDTO.getUserName());
        RoleDTO roleDTO = roleService.selectOne(Constants.ROLE_NORMAL);
        UserRoleDTO userRoleDTO = new UserRoleDTO();
        userRoleDTO.setUserId(userInfo.getId());
        userRoleDTO.setRoleId(roleDTO.getId());
        userRoleDTO.setCreateTime(date);
        userRoleDTO.setUpdateTime(date);
        this.userRoleService.insert(userRoleDTO);
        return new ResponseEntity<>(ResponseVO.sucess(), HttpStatus.CREATED);
    }


    @Logging
    @PutMapping(path = "/admin/user")
    @ApiOperation(value = "修改用户", notes = "修改用户")
    @PreAuthorize("@svs.validate(T(com.liyu.breeze.common.constant.PrivilegeConstants).USER_EDIT)")
    public ResponseEntity<ResponseVO> editUser(@Validated @RequestBody UserDTO userDTO) {
        this.userService.update(userDTO);
        return new ResponseEntity<>(ResponseVO.sucess(), HttpStatus.OK);
    }


    @Logging
    @DeleteMapping(path = "/admin/user/{id}")
    @ApiOperation(value = "删除用户", notes = "根据id删除用户")
    @PreAuthorize("@svs.validate(T(com.liyu.breeze.common.constant.PrivilegeConstants).USER_DELETE)")
    public ResponseEntity<ResponseVO> deleteUser(@PathVariable(value = "id") String id) {
        this.userService.deleteById(Long.valueOf(id));
        return new ResponseEntity<>(ResponseVO.sucess(), HttpStatus.OK);
    }

    @Logging
    @PostMapping(path = "/admin/user/batch")
    @ApiOperation(value = "批量删除用户", notes = "根据id列表批量删除用户")
    @PreAuthorize("@svs.validate(T(com.liyu.breeze.common.constant.PrivilegeConstants).USER_DELETE)")
    public ResponseEntity<ResponseVO> deleteBatchUser(@RequestBody Map<Integer, String> map) {
        this.userService.deleteBatch(map);
        return new ResponseEntity<>(ResponseVO.sucess(), HttpStatus.OK);
    }

    @Logging
    @GetMapping(path = "/admin/user")
    @ApiOperation(value = "分页查询用户", notes = "分页查询用户")
    @PreAuthorize("@svs.validate(T(com.liyu.breeze.common.constant.PrivilegeConstants).USER_SELECT)")
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
    @Logging
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
    @Logging
    @AnonymousAccess
    @ApiOperation(value = "判断邮箱是否存在", notes = "根据邮箱，判断用户是否存在")
    @GetMapping(path = "/user/validation/email")
    public ResponseEntity<Boolean> isEmailExists(String email) {
        UserDTO user = this.userService.selectByEmail(email);
        return new ResponseEntity<>(user == null, HttpStatus.OK);
    }

    @Logging
    @GetMapping(path = "/user/{userName}")
    @ApiOperation(value = "根据用户名查询用户列表", notes = "根据用户名查询用户列表")
    @PreAuthorize("@svs.validate(T(com.liyu.breeze.common.constant.PrivilegeConstants).USER_SELECT)")
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
    @Logging
    @PostMapping(path = "/user/role")
    @ApiOperation(value = "查询角色下用户列表", notes = "配合前端穿梭框查询用户列表")
    @PreAuthorize("@svs.validate(T(com.liyu.breeze.common.constant.PrivilegeConstants).ROLE_GRANT)")
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
    @Logging
    @PostMapping(path = "/user/dept")
    @ApiOperation(value = "查询部门下用户列表", notes = "配合前端穿梭框查询用户列表")
    @PreAuthorize("@svs.validate(T(com.liyu.breeze.common.constant.PrivilegeConstants).DEPT_GRANT)")
    public ResponseEntity<List<TransferVO>> listUserByUserAndDept(String userName, Long deptId, String direction) {
        List<TransferVO> result = new ArrayList<>();
        List<UserDTO> userList = this.userService.listByDept(deptId, userName, direction);
        userList.forEach(d -> {
            result.add(new TransferVO(String.valueOf(d.getId()), d.getUserName()));
        });
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}

