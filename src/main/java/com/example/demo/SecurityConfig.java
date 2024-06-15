package com.example.demo;
import java.io.IOException;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
public class SecurityConfig implements LogoutSuccessHandler{

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.cors(cors -> cors.disable()).csrf(csrf -> csrf.disable())
            .authorizeHttpRequests((requests) -> requests
                    .requestMatchers(HttpMethod.POST,"/search/**","/user-login/**").permitAll()
                    .requestMatchers("/","/register").permitAll()
                    .requestMatchers("**.js").permitAll()
                    .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                    .anyRequest().authenticated()
            )
            .formLogin((form) -> form
                    // ログインページへのパスを指定→コントローラーにもGET、/loginでの処理を記載する必要がある
                    .loginPage("/login")
                    .loginProcessingUrl("/login")
                    // ログイン成功時に表示される画面へのパス
                    .defaultSuccessUrl("/userDetail")
                    .failureUrl("/register")
                    .permitAll())
                    .logout(logout ->
            logout
                .logoutUrl("/logout") // 登出请求的URL
                // .logoutSuccessHandler(new CustomLogoutSuccessHandler()) // 使用自定义的LogoutSuccessHandler
                .permitAll()
                .invalidateHttpSession(true) // 使Session失效
                .clearAuthentication(true) // 清除认证信息
                .deleteCookies("JSESSIONID") // 删除cookie
        );;

        return http.build();
    }
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'onLogoutSuccess'");
    }

}