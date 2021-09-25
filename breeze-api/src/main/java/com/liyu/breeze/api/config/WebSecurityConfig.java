package com.liyu.breeze.api.config;

import com.liyu.breeze.api.annotation.AnonymousAccess;
import com.liyu.breeze.api.security.CustomAccessDeniedHandler;
import com.liyu.breeze.api.security.CustomAuthenticationEntryPoint;
import com.liyu.breeze.api.security.TokenConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.ObjectUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author gleiyu
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private TokenConfigurer tokenConfigurer;
    @Autowired
    private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    @Autowired
    private CustomAccessDeniedHandler customAccessDeniedHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //查找匿名标记的资源
        Map<RequestMappingInfo, HandlerMethod> handlerMethodMap = super.getApplicationContext().getBean(RequestMappingHandlerMapping.class).getHandlerMethods();
        Set<String> anonymousUrls = new HashSet<>();
        for (Map.Entry<RequestMappingInfo, HandlerMethod> entry : handlerMethodMap.entrySet()) {
            HandlerMethod handlerMethod = entry.getValue();
            AnonymousAccess anonymousAccess = handlerMethod.getMethodAnnotation(AnonymousAccess.class);
            if (!ObjectUtils.isEmpty(anonymousAccess)) {
                anonymousUrls.addAll(entry.getKey().getPatternsCondition().getPatterns());
            }
        }

        http
                //禁用cors
                .csrf().disable()
                //.addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)

                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                //禁用iframe
                .and()
                .headers()
                .frameOptions()
                .disable()

                //请求权限配置
                .and()
                .authorizeRequests()
                //自定义匿名访问url
                .antMatchers(anonymousUrls.toArray(new String[0])).permitAll()
                //静态资源
                .antMatchers(HttpMethod.GET, "/**/*.css", "/**/*.js", "/**/*.png", "/**/*.woff", "/**/*.woff2").permitAll()
                .antMatchers("/swagger**/**", "/doc.html", "/v3/**", "/webjars/**").permitAll()
                .antMatchers("/druid/**").permitAll()
                //放行options请求
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(customAuthenticationEntryPoint)
                .accessDeniedHandler(customAccessDeniedHandler)
                //禁用session
                .and()
                .apply(tokenConfigurer)
        ;
    }
}
