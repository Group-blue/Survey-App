package com.bilgeadam.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class UserServiceSecurity extends WebSecurityConfigurerAdapter {

    @Bean
    JwtTokenFilter getJwtTokenFilter(){
        return new JwtTokenFilter();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors();
        http.csrf().disable();

        http.authorizeRequests().antMatchers("/v3/api-docs/**",
                        "/swagger-ui/**",
                        "/auth/login").permitAll()
                        .anyRequest().authenticated();

        http.addFilterBefore(getJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}
