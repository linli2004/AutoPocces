package com.autoprocess.security;


import com.autoprocess.common.util.JwtUtil;
import com.autoprocess.entity.SysUser;
import com.autoprocess.enums.UserStatus;
import com.autoprocess.service.AuthService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import org.springframework.http.HttpHeaders;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.List;

/**
 * JWT 请求认证过滤器。
 *
 * 请求：
 *
 * Authorization: Bearer xxxxx
 *
 * 处理流程：
 *
 * Bearer Token
 *      ↓
 * JwtUtil.parse()
 *      ↓
 * 获取 userId
 *      ↓
 * AuthService.findById()
 *      ↓
 * 检查用户是否存在、是否启用
 *      ↓
 * 写入 SecurityContext
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private static final String BEARER_PREFIX = "Bearer ";
    private final JwtUtil jwtUtil;
    private final AuthService authService;

    public JwtAuthenticationFilter(JwtUtil jwtUtil,AuthService authService){
        this.jwtUtil=jwtUtil;
        this.authService=authService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);

        /*
         * 请求没有 Token。
         *
         * 不在这里直接返回 401，
         * 因为 /auth/login 等公开接口本身就允许没有 Token。
         *
         * 是否必须登录最终由 SecurityConfig 判断。
         */
        if (!StringUtils.hasText(authorization) || !authorization.startsWith(BEARER_PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }
        String token = authorization.substring(BEARER_PREFIX.length()).trim();

        if (!StringUtils.hasText(token)) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            /*
             * 已经认证过则不重复处理。
             */
            if (SecurityContextHolder.getContext().getAuthentication() == null) {

                Claims claims = jwtUtil.parse(token);

                /*
                 * JwtUtil.generate() 中 subject 保存的是用户 ID。
                 */
                String userId = claims.getSubject();

                if (StringUtils.hasText(userId)) {

                    /*
                     * 不完全相信 JWT 中的 username / role。
                     *
                     * 每次重新查询数据库，
                     * 这样用户被禁用之后旧 Token 也会立即失效。
                     */
                    SysUser user = authService.findById(userId);
                    if (user != null
                            && user.getStatus() == UserStatus.ENABLED
                            && user.getRole() != null) {

                        SimpleGrantedAuthority authority =
                                new SimpleGrantedAuthority(
                                        "ROLE_" + user.getRole().name()
                                );

                        UsernamePasswordAuthenticationToken authentication =
                                new UsernamePasswordAuthenticationToken(
                                        user,
                                        null,
                                        List.of(authority)
                                );

                        authentication.setDetails(
                                new WebAuthenticationDetailsSource()
                                        .buildDetails(request)
                        );

                        SecurityContext context =
                                SecurityContextHolder.createEmptyContext();

                        context.setAuthentication(authentication);

                        SecurityContextHolder.setContext(context);
                    }
                }
            }

        } catch (JwtException | IllegalArgumentException ex) {

            /*
             * JWT 过期
             * JWT 签名错误
             * JWT 格式错误
             *
             * 都视为未登录。
             *
             * 不在 Filter 里直接写 Response，
             * 后续统一由 Spring Security 的
             * AuthenticationEntryPoint 返回 401。
             */
            SecurityContextHolder.clearContext();
        }
        filterChain.doFilter(request, response);
    }
}
