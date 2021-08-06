//package com.liyu.breeze.api.security;
//
//import com.magicdt.api.controller.dto.JwtUserDTO;
//import com.magicdt.common.enums.AccountStatusEnum;
//import com.magicdt.service.AccountService;
//import com.magicdt.service.dto.AccountDTO;
//import com.magicdt.service.dto.PrivilegeDTO;
//import com.magicdt.service.dto.RoleDTO;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Component;
//import org.springframework.util.CollectionUtils;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * @author gleiyu
// */
//@Slf4j
//@Component
//public class UserDetailsServiceImpl implements UserDetailsService {
//
//    private AccountService accountService;
//
//
//    @Autowired
//    public void setAccountService(AccountService accountService) {
//        this.accountService = accountService;
//    }
//
//    /**
//     * 根据用户名查询登录用户信息
//     *
//     * @param userName 用户名
//     * @return 用户信息
//     * @throws UsernameNotFoundException UsernameNotFoundException
//     */
//    @Override
//    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
//        AccountDTO accountDTO = accountService.selectByUserName(userName);
//        boolean flag = accountDTO.getAccountStatus() != null
//                && !(accountDTO.getAccountStatus().getCode().equals(AccountStatusEnum.NORMAL_UNBIND_EMAIL.getCode()) ||
//                accountDTO.getAccountStatus().getCode().equals(AccountStatusEnum.NORMAL_BIND_EMAIL.getCode()));
//        if (accountDTO == null) {
//            throw new BadCredentialsException("用户名或者密码错误");
//        } else if (flag) {
//            throw new BadCredentialsException("用户状态异常，请联系管理员");
//        } else {
//            JwtUserDTO user = new JwtUserDTO();
//            user.setAccount(accountDTO);
//            //查询用户角色权限信息
//            List<RoleDTO> privilegeS = this.accountService.getAllPrivilegeByUserName(userName);
//            user.setAuthorities(this.toGrantedAutiority(privilegeS));
//            return user;
//        }
//    }
//
//    private List<GrantedAuthority> toGrantedAutiority(List<RoleDTO> roles) {
//        if (CollectionUtils.isEmpty(roles)) {
//            return null;
//        } else {
//            List<GrantedAuthority> list = new ArrayList<>();
//            for (RoleDTO role : roles) {
//                if (role.getPrivileges() == null) {
//                    continue;
//                }
//                for (PrivilegeDTO privilege : role.getPrivileges()) {
//                    GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(privilege.getPrivilegeCode());
//                    list.add(grantedAuthority);
//                }
//            }
//            return list;
//        }
//    }
//}
