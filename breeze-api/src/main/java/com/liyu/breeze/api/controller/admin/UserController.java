package com.liyu.breeze.api.controller.admin;


import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.servlet.ServletUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.Strings;
import com.liyu.breeze.api.annotation.AnonymousAccess;
import com.liyu.breeze.api.annotation.Logging;
import com.liyu.breeze.api.security.OnlineUserService;
import com.liyu.breeze.api.security.TokenProvider;
import com.liyu.breeze.api.security.UserDetailInfo;
import com.liyu.breeze.api.util.I18nUtil;
import com.liyu.breeze.api.util.SecurityUtil;
import com.liyu.breeze.api.vo.*;
import com.liyu.breeze.common.constant.Constants;
import com.liyu.breeze.common.constant.DictConstants;
import com.liyu.breeze.common.enums.ErrorShowTypeEnum;
import com.liyu.breeze.common.enums.RegisterChannelEnum;
import com.liyu.breeze.common.enums.ResponseCodeEnum;
import com.liyu.breeze.common.enums.UserStatusEnum;
import com.liyu.breeze.service.admin.*;
import com.liyu.breeze.service.dto.admin.RoleDTO;
import com.liyu.breeze.service.dto.admin.UserActiveDTO;
import com.liyu.breeze.service.dto.admin.UserDTO;
import com.liyu.breeze.service.dto.admin.UserRoleDTO;
import com.liyu.breeze.service.param.admin.UserParam;
import com.liyu.breeze.service.util.RedisUtil;
import com.liyu.breeze.service.vo.DictVO;
import io.swagger.annotations.Api;
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
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * ????????????????????? ???????????????
 * </p>
 *
 * @author liyu
 */
@RestController
@RequestMapping("/api")
@Api(tags = "????????????-????????????")
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
    @Autowired
    private UserActiveService userActiveService;

    /**
     * ????????????????????????
     * ????????????
     *
     * @param loginUser ????????????
     * @return ?????????????????????token
     */

    @AnonymousAccess
    @PostMapping(path = "/user/login")
    @ApiOperation(value = "????????????", notes = "??????????????????")
    public ResponseEntity<ResponseVO> login(@Validated @RequestBody LoginInfoVO loginUser, HttpServletRequest request) {
        //???????????????
        String authCode = (String) redisUtil.get(loginUser.getUuid());
        redisUtil.delKeys(loginUser.getUuid());
        if (!StringUtils.isEmpty(authCode) && authCode.equalsIgnoreCase(loginUser.getAuthCode())) {
            try {
                //?????????????????????
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(loginUser.getUserName(), loginUser.getPassword());
                //spring security????????????userDetailsService?????????????????????????????????????????????????????????Authentication???????????????????????????SecurityContext???
                Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                //??????token ??????uuid??????token
                String token = tokenProvider.createToken();
                final UserDetailInfo userInfo = (UserDetailInfo) authentication.getPrincipal();
                userInfo.setLoginTime(new Date());
                userInfo.setLoginIpAddress(ServletUtil.getClientIP(request));
                userInfo.setRemember(loginUser.getRemember());
                //??????????????????????????????????????????redis onlineuser???
                List<RoleDTO> roles = userService.getAllPrivilegeByUserName(userInfo.getUsername());
                userInfo.getUser().setRoles(roles);
                //???????????????redis???
                onlineUserService.insert(userInfo, token);
                //??????????????????token
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
    @ApiOperation(value = "????????????", notes = "??????????????????")
    public ResponseEntity<ResponseVO> logout(@RequestBody String token) {
        if (token != null) {
            this.onlineUserService.logoutByToken(token);
        }
        return new ResponseEntity<>(ResponseVO.sucess(), HttpStatus.OK);
    }

    @AnonymousAccess
    @PostMapping(path = "/user/passwd/edit")
    public ResponseEntity<ResponseVO> editPassword(@NotNull String oldPassword, @NotNull String password, @NotNull String confirmPassword) {
        String userName = SecurityUtil.getCurrentUserName();
        if (!StrUtil.isEmpty(userName)) {
            if (password.equals(confirmPassword)) {
                UserDTO user = this.userService.selectOne(userName);
                if (this.passwordEncoder.matches(oldPassword, user.getPassword())) {
                    UserDTO userDTO = new UserDTO();
                    userDTO.setId(user.getId());
                    userDTO.setPassword(this.passwordEncoder.encode(password));
                    this.userService.update(userDTO);
                    return new ResponseEntity<>(ResponseVO.sucess(), HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(ResponseVO.error(I18nUtil.get("response.error.oldPassword")), HttpStatus.OK);
                }
            } else {
                return new ResponseEntity<>(ResponseVO.error(I18nUtil.get("response.error.notSamePassword")), HttpStatus.OK);
            }
        } else {
            return new ResponseEntity<>(ResponseVO.error(String.valueOf(HttpServletResponse.SC_UNAUTHORIZED), I18nUtil.get("response.error.unauthorized")), HttpStatus.OK);
        }
    }

    /**
     * ?????????????????????
     *
     * @param email ????????????
     * @return ResponseVO
     */
    @Logging
    @GetMapping(path = "/user/email/getAuth")
    @ApiOperation(value = "?????????????????????", notes = "???????????????????????????????????????????????????")
    public ResponseEntity<ResponseVO> sendActiveEmail(@Email String email) {
        String userName = SecurityUtil.getCurrentUserName();
        if (!Strings.isNullOrEmpty(userName)) {
            UserActiveDTO activeDTO = new UserActiveDTO();
            activeDTO.setUserName(userName);
            long time = System.currentTimeMillis() + 1000 * 60 * 10;
            activeDTO.setExpiryTime(time);
            activeDTO.setActiveCode(randomPasswordGenerate(6));
            this.userActiveService.insert(activeDTO);
            String subject = appName + "????????????";
            String html = "<html><body><p>" +
                    "??????????????????" + userName +
                    "<br/><br/>?????????????????????/?????????????????????<br/><h3>" + activeDTO.getActiveCode() +
                    "</h3><br/> ??????:?????????????????????10????????????????????????????????????????????????" +
                    "</p></body></html>";
            String[] sendTo = {email};
            emailService.sendHtmlEmail(sendTo, subject, html);
        }
        return new ResponseEntity<>(ResponseVO.sucess(), HttpStatus.OK);
    }

    /**
     * ????????????????????????????????????
     *
     * @param authCode ?????????
     * @return ResponseVO
     */
    @Logging
    @GetMapping(path = "/user/email/auth")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<ResponseVO> getEmailAuthCode(@NotNull String authCode, @Email String email) {
        String userName = SecurityUtil.getCurrentUserName();
        if (!StrUtil.isEmpty(userName)) {
            UserActiveDTO userActive = this.userActiveService.selectOne(userName, authCode);
            if (userActive == null || System.currentTimeMillis() > userActive.getExpiryTime()) {
                return new ResponseEntity<>(ResponseVO.error(I18nUtil.get("response.error.authCode.expired")), HttpStatus.OK);
            } else {
                UserDTO user = new UserDTO();
                user.setUserName(userName);
                user.setEmail(email);
                user.setUserStatus(DictVO.toVO(DictConstants.USER_STATUS, UserStatusEnum.BIND_EMAIL.getValue()));
                this.userActiveService.updateByUserAndCode(userActive);
                this.userService.updateByUserName(user);
                return new ResponseEntity<>(ResponseVO.sucess(), HttpStatus.OK);
            }
        } else {
            return new ResponseEntity<>(ResponseVO.error(String.valueOf(HttpServletResponse.SC_UNAUTHORIZED), I18nUtil.get("response.error.unauthorized")), HttpStatus.OK);
        }
    }

    /**
     * ??????token??????redis??????????????????
     *
     * @param token token
     * @return ???????????????????????????
     */
    @AnonymousAccess
    @GetMapping(path = "/user/get/{token}")
    @ApiOperation(value = "??????????????????", notes = "??????token??????????????????????????????")
    public ResponseEntity<ResponseVO> getOnlineUserInfo(@PathVariable(value = "token") String token) {
        OnlineUserVO onlineUser = this.onlineUserService.getAllPrivilegeByToken(token);
        ResponseVO info = ResponseVO.sucess();
        info.setData(onlineUser);
        return new ResponseEntity<>(info, HttpStatus.OK);
    }

    /**
     * ????????????
     *
     * @param registerInfo       ??????????????????
     * @param httpServletRequest HttpServletRequest
     * @return OperateInfo
     */
    @Logging
    @AnonymousAccess
    @PostMapping(path = "/user/register")
    @ApiOperation(value = "????????????", notes = "??????????????????")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<ResponseVO> register(@Validated @RequestBody RegisterInfoVO registerInfo, HttpServletRequest httpServletRequest) {
        //???????????????????????????
        String authCode = (String) redisUtil.get(registerInfo.getUuid());
        redisUtil.delKeys(registerInfo.getUuid());
        if (!StrUtil.isEmpty(authCode) && authCode.equalsIgnoreCase(registerInfo.getAuthCode())) {
            //????????????????????????????????????
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
                //???????????????ip??????
                String ipAddress = ServletUtil.getClientIP(httpServletRequest);
                userDTO.setRegisterIp(ipAddress);
                this.sendConfirmEmail(userDTO, null);
                this.userService.insert(userDTO);
                //????????????????????????
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
                //??????????????????????????????????????????????????????
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
    @ApiOperation(value = "????????????", notes = "????????????")
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
        //????????????????????????
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
    @ApiOperation(value = "????????????", notes = "????????????")
    @PreAuthorize("@svs.validate(T(com.liyu.breeze.common.constant.PrivilegeConstants).USER_EDIT)")
    public ResponseEntity<ResponseVO> editUser(@Validated @RequestBody UserDTO userDTO) {
        this.userService.update(userDTO);
        return new ResponseEntity<>(ResponseVO.sucess(), HttpStatus.OK);
    }


    @Logging
    @DeleteMapping(path = "/admin/user/{id}")
    @ApiOperation(value = "????????????", notes = "??????id????????????")
    @PreAuthorize("@svs.validate(T(com.liyu.breeze.common.constant.PrivilegeConstants).USER_DELETE)")
    public ResponseEntity<ResponseVO> deleteUser(@PathVariable(value = "id") String id) {
        this.userService.deleteById(Long.valueOf(id));
        return new ResponseEntity<>(ResponseVO.sucess(), HttpStatus.OK);
    }

    @Logging
    @PostMapping(path = "/admin/user/batch")
    @ApiOperation(value = "??????????????????", notes = "??????id????????????????????????")
    @PreAuthorize("@svs.validate(T(com.liyu.breeze.common.constant.PrivilegeConstants).USER_DELETE)")
    public ResponseEntity<ResponseVO> deleteBatchUser(@RequestBody Map<Integer, String> map) {
        this.userService.deleteBatch(map);
        return new ResponseEntity<>(ResponseVO.sucess(), HttpStatus.OK);
    }

    @Logging
    @GetMapping(path = "/admin/user")
    @ApiOperation(value = "??????????????????", notes = "??????????????????")
    @PreAuthorize("@svs.validate(T(com.liyu.breeze.common.constant.PrivilegeConstants).USER_SELECT)")
    public ResponseEntity<Page<UserDTO>> listUser(UserParam userParam) {
        Page<UserDTO> page = this.userService.listByPage(userParam);
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    /**
     * ??????????????????
     *
     * @param length ????????????
     * @return ??????
     */
    private String randomPasswordGenerate(int length) {
        //??????????????????
        char[][] pairs = {{'a', 'z'}, {'A', 'Z'}, {'0', '9'}};
        RandomStringGenerator rsg = new RandomStringGenerator.Builder().withinRange(pairs).build();
        return rsg.generate(length);
    }

    /**
     * ??????????????????????????????????????????????????????
     *
     * @param userDTO ????????????
     */
    private void sendConfirmEmail(UserDTO userDTO, String password) {
        String subject = appName + "????????????";
        String html = "<html><body><p>" +
                "??????????????????<br/> ???????????????" + appName + "?????????" + userDTO.getUserName() + "?????????";
        if (!StringUtils.isEmpty(password)) {
            html = html + "?????????????????????" + password;
        }
        html = html + "???<br/> ?????????????????????????????????" +
                "<br/> </p></body></html>";

        String[] sendTo = {userDTO.getEmail()};
        this.emailService.sendHtmlEmail(sendTo, subject, html);
    }

    /**
     * ???????????????????????????
     *
     * @param userName ?????????
     * @return true/false
     */
    @Logging
    @AnonymousAccess
    @ApiOperation(value = "????????????????????????", notes = "??????????????????????????????????????????")
    @GetMapping(path = "/user/validation/userName")
    public ResponseEntity<Boolean> isUserNameExists(String userName) {
        UserDTO user = this.userService.selectOne(userName);
        return new ResponseEntity<>(user == null, HttpStatus.OK);
    }

    /**
     * ??????????????????????????????
     *
     * @param email ????????????
     * @return true/false
     */
    @Logging
    @AnonymousAccess
    @ApiOperation(value = "????????????????????????", notes = "???????????????????????????????????????")
    @GetMapping(path = "/user/validation/email")
    public ResponseEntity<Boolean> isEmailExists(String email) {
        UserDTO user = this.userService.selectByEmail(email);
        return new ResponseEntity<>(user == null, HttpStatus.OK);
    }

    @Logging
    @GetMapping(path = "/user/info")
    @ApiOperation(value = "?????????????????????????????????", notes = "?????????????????????????????????")
    public ResponseEntity<UserDTO> listUserByUserName() {
        String userName = SecurityUtil.getCurrentUserName();
        if (!StrUtil.isEmpty(userName)) {
            UserDTO userinfo = this.userService.selectOne(userName);
            return new ResponseEntity<>(userinfo, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new UserDTO(), HttpStatus.OK);
        }
    }

    /**
     * ???????????????????????????????????????
     *
     * @param userName  ?????????
     * @param roleId    ??????id
     * @param direction 1:target 0:source
     * @return user list
     */
    @Logging
    @PostMapping(path = "/user/role")
    @ApiOperation(value = "???????????????????????????", notes = "???????????????????????????????????????")
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
     * ???????????????????????????????????????
     *
     * @param userName  ?????????
     * @param deptId    dept id
     * @param direction 1:target 0:source
     * @return user list
     */
    @Logging
    @PostMapping(path = "/user/dept")
    @ApiOperation(value = "???????????????????????????", notes = "???????????????????????????????????????")
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

