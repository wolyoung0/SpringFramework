package com.mysite.sbb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration // Config 설정
@EnableWebSecurity // 웹 상에서 security 설정
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    // SecurityFilterChain을 만들어줘야함
    @Bean // 객체 생성
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

//        http.formLogin(Customizer.withDefaults()); // 디폴트로 제공하는 로그인 작동, 로그인 페이지가 없을 때 사용
        http.formLogin(form -> form
                .loginPage("/member/login") // 로그인 페이지가 있을 때 사용.
                .defaultSuccessUrl("/") // 성공했을때 이동 주소
                .failureUrl("/member/login/error") // 실패했을때 이동 주소
                .usernameParameter("username") // 이메일을 id로 쓸 경우 이메일을 넣어야함.
                .passwordParameter("password")
                .permitAll());
        http.logout(Customizer.withDefaults()); // 디폴트로 제공하는 로그아웃 작동. 로그아웃 페이지가 없을 때 사용

//        http.csrf(csrf -> csrf.disable());

        // 이거만 남고 다른거 못 씀. 왜 쓰는지 찾아봐야함 못 들음
        http.authorizeHttpRequests((authorize) -> authorize
                .requestMatchers("/css/**", "/js/**", "/images/**").permitAll()
                .requestMatchers("/").permitAll()
                .requestMatchers("/member/**").permitAll()
                .requestMatchers("/question/**").permitAll()
                .anyRequest().authenticated()); // 그 외의 request는 인증이 되고 사용하도록

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
