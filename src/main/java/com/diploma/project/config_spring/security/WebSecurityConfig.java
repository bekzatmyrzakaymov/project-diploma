package com.diploma.project.config_spring.security;

import com.diploma.project.config_spring.security.jwt.AuthEntryPointJwt;
import com.diploma.project.model.oauth.jwt.JwtConfigurer;
import com.diploma.project.model.oauth.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;


    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                .antMatchers("/v2/api-docs",
                        "/configuration/ui",
                        "/swagger-resources/**",
                        "/configuration/security",
                        "/swagger-ui/**",
                        "/webjars/springfox-swagger-ui/**").permitAll()
                .antMatchers("/api/v1/auth/**").permitAll()
                .antMatchers("/dictionary/**").permitAll()
                .antMatchers("/fl/get-fl-by-iin/**").permitAll()
                .antMatchers("/fl/get-fl-fio/**").permitAll()
                .antMatchers("/ul/get-reg-addres-ul/**").permitAll()
                .antMatchers("/ul/get-ul-risk/**").permitAll()
                .antMatchers("/fl/get-fl-risk/**").permitAll()
                .anyRequest().authenticated().and().apply(new JwtConfigurer(jwtTokenProvider));
    }


}
