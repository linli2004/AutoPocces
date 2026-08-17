package com.autoprocess.config;


import com.autoprocess.common.response.ApiResponse;
import com.autoprocess.security.JwtAuthenticationFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Configuration
public class SecurityConfig {

    /**
     * 密码加密器。
     *
     * AuthServiceImpl 中已经通过 PasswordEncoder
     * 校验数据库 BCrypt 密码。
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /**
     * Spring Security 主过滤器链。
     */
    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            JwtAuthenticationFilter jwtAuthenticationFilter,
            CorsConfigurationSource corsConfigurationSource,
            ObjectMapper objectMapper
    ) throws Exception {
        http

                /*
                 * REST API + JWT 不依赖 Cookie Session，
                 * 因此关闭 CSRF。
                 */
                .csrf(AbstractHttpConfigurer::disable)
                /*
                *使用现有corsconfig
                 */
                .cors(cors->cors.configurationSource(corsConfigurationSource))
                /*
                *Jwt无状态认证
                 */
                .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                /*
                不使用spring 默认登录页面
                 */
                .formLogin(AbstractHttpConfigurer::disable)
                /*
                不使用 HTTP Basic
                 */
                .httpBasic(AbstractHttpConfigurer::disable)
                /*
                接口权限规则
                 */
                .authorizeHttpRequests(
                        auth -> auth

                                /*
                                 * 浏览器跨域预检请求。
                                 */
                                .requestMatchers(
                                        HttpMethod.OPTIONS,
                                        "/**"
                                ).permitAll()

                                /*
                                 * 登录接口无需 JWT。
                                 */
                                .requestMatchers(
                                        "/auth/login",
                                        "/error"
                                ).permitAll()

                                /*
                                 * 其他接口全部要求登录。
                                 */
                                .anyRequest().authenticated()
                )
                /*
                统一处理认证失败和权限不足
                 */
                .exceptionHandling(
                        exception -> exception

                                /*
                                 * 没登录 / JWT 无效 / JWT 过期。
                                 */
                                .authenticationEntryPoint(
                                        (request, response, ex) ->
                                                writeSecurityError(
                                                        response,
                                                        HttpServletResponse.SC_UNAUTHORIZED,
                                                        "未登录或登录已过期",
                                                        objectMapper
                                                )
                                )

                                /*
                                 * 已登录但没有权限。
                                 */
                                .accessDeniedHandler(
                                        (request, response, ex) ->
                                                writeSecurityError(
                                                        response,
                                                        HttpServletResponse.SC_FORBIDDEN,
                                                        "没有访问权限",
                                                        objectMapper
                                                )
                                )
                )
                /*
                 * JWT Filter 必须运行在
                 * UsernamePasswordAuthenticationFilter 之前。
                 */
                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }


    /**
     * Spring Security 层异常不会进入 ControllerAdvice，
     * 所以这里直接写统一 ApiResponse JSON。
     */
    private void writeSecurityError(
            HttpServletResponse response,
            int status,
            String message,
            ObjectMapper objectMapper
    ) throws IOException {

        response.setStatus(status);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        ApiResponse<Void> body =
                new ApiResponse<>(status, message, null);

        objectMapper.writeValue(
                response.getWriter(),
                body
        );
    }
}
