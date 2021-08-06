//package com.liyu.breeze.api.security;
//
//import com.magicdt.api.config.SecurityProperties;
//import com.magicdt.api.controller.dto.OnlineUserDTO;
//import com.magicdt.api.controller.service.OnlineUserService;
//import com.magicdt.common.constant.Constants;
//import com.magicdt.service.util.RedisUtil;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.GenericFilterBean;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.http.HttpServletRequest;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * jwt token 过滤器
// *
// * @author gleiyu
// */
//@Slf4j
//@Component
//public class TokenFilter extends GenericFilterBean {
//
//    private RedisUtil redisUtil;
//    private OnlineUserService onlineUserService;
//
//    private final SecurityProperties properties;
//
//    public TokenFilter(SecurityProperties properties) {
//        this.properties = properties;
//    }
//
//    @Autowired
//    public void setRedisUtil(RedisUtil redisUtil) {
//        this.redisUtil = redisUtil;
//    }
//
//    @Autowired
//    public void setOnlineUserService(OnlineUserService onlineUserService) {
//        this.onlineUserService = onlineUserService;
//    }
//
//    /**
//     * 验证每个请求提交的token是否有效，并解析设置权限信息
//     *
//     * @param request  request
//     * @param response response
//     * @param chain    chain
//     * @throws IOException      IOException
//     * @throws ServletException ServletException
//     */
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
//        String token = resolveToken(httpServletRequest);
//        String requestUri = httpServletRequest.getRequestURI();
//        //用户在线且有效，获取用户的权限信息 只要在redis中存在数据则认为是有效在线用户
//        OnlineUserDTO onlineUserDTO = (OnlineUserDTO) this.redisUtil.get(Constants.ONLINE_TOKEN_KEY + token);
//        if (onlineUserDTO != null) {
//            long time = onlineUserDTO.getRemember() ? properties.getLongTokenValidityInSeconds() / 1000 : properties.getTokenValidityInSeconds() / 1000;
//            Authentication authentication = getAuthentication(onlineUserDTO);
//            redisUtil.set(Constants.ONLINE_USER_KEY + onlineUserDTO.getUserName(), onlineUserDTO.getToken(), time);
//            redisUtil.set(Constants.ONLINE_TOKEN_KEY + onlineUserDTO.getToken(), onlineUserDTO, time);
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//        }
//        chain.doFilter(request, response);
//    }
//
//    /**
//     * 获取请求header中的token数据
//     *
//     * @param request 请求
//     * @return token
//     */
//    private String resolveToken(HttpServletRequest request) {
//        return request.getHeader(Constants.TOKEN_KEY);
//    }
//
//    /**
//     * 获取redis中登录用户的权限信息
//     * 后台修改角色权限时会同步更新redis中用户的权限信息为null，这里如果权限为null时则到数据库中再次加载用户的最新权限数据
//     *
//     * @param onlineUserDTO 在线用户
//     * @return Authentication
//     */
//    private Authentication getAuthentication(OnlineUserDTO onlineUserDTO) {
//        if (onlineUserDTO == null) {
//            return null;
//        }
//        if (onlineUserDTO.getPrivileges() == null) {
//            //从数据库中再次加载用户权限 放入缓存
//            OnlineUserDTO onlineUser = onlineUserService.getAllPrivilegeByToken(onlineUserDTO.getToken());
//            List<String> roleList = new ArrayList<>();
//            List<String> privilegeList = new ArrayList<>();
//            List<GrantedAuthority> authorities = new ArrayList<>();
//            for (String p : onlineUser.getPrivileges()) {
//                authorities.add(new SimpleGrantedAuthority(p));
//                privilegeList.add(p.toLowerCase());
//            }
//            for (String r : onlineUser.getRoles()) {
//                authorities.add(new SimpleGrantedAuthority(r));
//                roleList.add(r.toLowerCase());
//            }
//            onlineUserDTO.setRoles(roleList);
//            onlineUserDTO.setPrivileges(privilegeList);
//            User principal = new User(onlineUserDTO.getUserName(), "", authorities);
//            return new UsernamePasswordAuthenticationToken(principal, onlineUserDTO.getToken(), authorities);
//        } else {
//            List<GrantedAuthority> authorities = new ArrayList<>();
//            List<String> privileges = onlineUserDTO.getPrivileges();
//            List<String> roles = onlineUserDTO.getRoles();
//            for (String privilege : privileges) {
//                authorities.add(new SimpleGrantedAuthority(privilege));
//            }
//            for (String role : roles) {
//                authorities.add(new SimpleGrantedAuthority(role));
//            }
//            User principal = new User(onlineUserDTO.getUserName(), "", authorities);
//            return new UsernamePasswordAuthenticationToken(principal, onlineUserDTO.getToken(), authorities);
//        }
//    }
//}
