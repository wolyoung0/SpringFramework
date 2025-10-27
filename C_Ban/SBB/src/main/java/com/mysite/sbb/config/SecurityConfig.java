package com.mysite.sbb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration // Config 설정
@EnableWebSecurity // 웹 상에서 security 설정
public class SecurityConfig {

    // SecurityFilterChain을 만들어줘야함
    @Bean // 객체 생성
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.formLogin(Customizer.withDefaults()); // 디폴트로 제공하는 로그인 작동
        http.logout(Customizer.withDefaults()); // 디폴트로 제공하는 로그아웃 작동

        // 이거만 남고 다른거 못 씀. 왜 쓰는지 찾아봐야함 못 들음
        http.authorizeHttpRequests((authorize) -> authorize
                .requestMatchers("/css/**", "/js/**").permitAll()
                .requestMatchers("/").permitAll()
                .requestMatchers("/member/**").permitAll()
//                .requestMatchers("/question/**").permitAll()
                .anyRequest().authenticated()); // 그 외의 request는 인증이 되고 사용하도록

        return http.build();
    }
}
