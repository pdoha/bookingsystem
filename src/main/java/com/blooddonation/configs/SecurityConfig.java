package com.blooddonation.configs;

import com.blooddonation.member.service.LoginFailureHandler;
import com.blooddonation.member.service.LoginSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity//메소드별 통제 가능
public class SecurityConfig {
    //페이지접근 (인증과 인가)
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        /* 시큐리티의 핵심 기능 1. 인증(로그인) 2.인가(페이지 접근)*/
        /* 인증 설정 S - 로그인 */
        http.formLogin(f -> {
            f.loginPage("/member/login")
                    .usernameParameter("userid") //이메일 or userId
                    .passwordParameter("userPw")
                    /*//성공시 이동할 페이지
                    .defaultSuccessUrl("/")
                    //실패시 로그인 창
                    .failureUrl("/member/login?error=true");*/

                    //url 대신 상세 설정을 위해 (service에서 만든것)
                    .successHandler(new LoginSuccessHandler())
                    .failureHandler(new LoginFailureHandler());

        });
        /* 인증 설정 E - 로그인 */
        return http.build();
    }


    /*    http.logout(c -> {
            //이동할 주소
            c.logoutRequestMatcher(new AntPathRequestMatcher("/member/logout")).logoutSuccessUrl("/member/login");

        });

        *//*인증 설정 E = 로그인, 로그아웃*//*

        *//* 인가 설정 S - 접근 통제 *//*
        //hasAuthrity(..) hasAnyAuthority(..) - 여러개일때, hasRole, hasAnyRole
        //ROLE_롤명칭
        //hasAthority('ADMIN')
        //ROLE_ADMIN -> hasAuthority('ROLE_ADMIN')
        //hasRole('ADMIN')
        http.authorizeHttpRequests(c -> {
            c.requestMatchers("/mypage/**").authenticated() //회원전용
                    //.requestMatchers("/admin/**").hasAnyAuthority("ADMIN","MAMAGER")//"ADMIN","MAMAGER"만 /admin/**의 모든 클래스 접근 가능
                    .anyRequest().permitAll();//그외 모든 페이지는 모두 접근 가능
        });
        *//* 인가 설정 S - 접근 통제 *//*
        *//*
         http.exceptionHandling(c -> {
             c.authenticationEntryPoint(new AuthenticationEntryPoint() {
                 @Override
                 public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

                 }
             });//권한 실패했을때 로그인 페이지로 가는 것이 아니라 따로 정해놓은 페이지로 가기
         });
        *//*

        //주소가  admin이면 관리자 페이지임.
        http.exceptionHandling(c -> {
            c.authenticationEntryPoint((req, res, e) -> {
                String URL  = req.getRequestURI();
                if (URL.indexOf("/admin") != -1) { //관리자 페이지
                    res.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                } else {//회원전용 페이지
                    res.sendRedirect(req.getContextPath() + "/member/login");

                }
            });
        });

        return http.build();
    }*/

    //비밀번호 해시화
    //encode : 해시화
    //matches : 해시된 비밀번호화 사용자가 입력한 비밀번호의 일치 여부
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}