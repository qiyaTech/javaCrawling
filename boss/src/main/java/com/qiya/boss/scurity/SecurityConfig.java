package com.qiya.boss.scurity;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


/**
 * Created by qiyalm on 16/6/15.
 */
@Configuration
@EnableWebSecurity
@EnableAutoConfiguration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        return new CustomUserDetailsService();
    }

    @Bean
    public MyFilterSecurityInterceptor myFilterSecurityInterceptor(){
        MyFilterSecurityInterceptor filterSecurityInterceptor=new MyFilterSecurityInterceptor();
        return new MyFilterSecurityInterceptor();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.headers().frameOptions().disable();

        http
                .csrf().disable()   //禁用csrf安全防御攻击(这里是为了解决post请求受限制的问题,也可不禁用,但每次请求都要传token)
                .authorizeRequests()
                .antMatchers("/api/**","/data/**","/img/**","/css/**","/plugins/**/**","/plugins/**/**／**","/plugins/**","/js/lib/**","js/lib/demo/**","/images/**","/js/appjs/**","/dist/**","/bower_components/**","/login").permitAll()
                //所匹配的这些地址允许所有用户访问(permitAll())
                .antMatchers(HttpMethod.POST, "/taskManager/MsgStart","/activiti/commitTaskComment/**").permitAll()
                .anyRequest().authenticated()  //其他地址的访问均需验证权限
                .and()
                .formLogin()
                .loginPage("/login").permitAll()
                .usernameParameter("phone")
                .passwordParameter("password")
                .defaultSuccessUrl("/index")
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login").permitAll();

        http.addFilterBefore(myFilterSecurityInterceptor(), FilterSecurityInterceptor.class);
    }


    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService());
    }

}
