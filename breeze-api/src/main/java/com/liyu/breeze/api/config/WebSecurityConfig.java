package com.liyu.breeze.api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

/**
 * @author gleiyu
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {


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
                .anyRequest().permitAll()
                //自定义匿名访问url
//                .antMatchers(anonymousUrls.toArray(new String[0])).permitAll()
                //静态资源
//                .antMatchers(HttpMethod.GET, "/**/*.css", "/**/*.js", "/**/*.png", "/**/*.woff", "/**/*.woff2").permitAll()
//                .antMatchers("/swagger**/**", "/doc.html", "/v3/**", "/webjars/**").permitAll()
//                .antMatchers("/druid/**").permitAll()
                //放行options请求
//                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
//                .anyRequest().authenticated()
//                .and()
//                .exceptionHandling()
//                .authenticationEntryPoint(customAuthenticationEntryPoint)
//                .accessDeniedHandler(customAccessDeniedHandler)
        //禁用session
//                .and().
//                apply(tokenConfigurer)
        ;
    }
}
