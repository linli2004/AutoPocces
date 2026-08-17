package com.autoprocess.logging;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RequestLoggingFilter extends OncePerRequestFilter {
    private static final Logger log = LoggerFactory.getLogger(RequestLoggingFilter.class);

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        long startAt = System.currentTimeMillis();

        String method = request.getMethod();
        String requestPath = buildRequestPath(request);
        String clientIp = resolveClientIp(request);

        try {
            filterChain.doFilter(request, response);
        } finally {
            long costMs = System.currentTimeMillis() - startAt;
            int status = response.getStatus();
            String user = resolveCurrentUser();

            log.info(
                    "HTTP {} {} -> {} ({} ms) ip={} user={}",
                    method,
                    requestPath,
                    status,
                    costMs,
                    clientIp,
                    user
            );
        }
    }

    private String buildRequestPath(HttpServletRequest request) {
        String query = request.getQueryString();
        if (!StringUtils.hasText(query)) {
            return request.getRequestURI();
        }
        return request.getRequestURI() + "?" + query;
    }

    private String resolveClientIp(HttpServletRequest request) {
        String forwarded = request.getHeader("X-Forwarded-For");
        if (StringUtils.hasText(forwarded)) {
            return forwarded.split(",")[0].trim();
        }
        return request.getRemoteAddr();
    }

    private String resolveCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null
                || !authentication.isAuthenticated()
                || authentication instanceof AnonymousAuthenticationToken) {
            return "anonymous";
        }
        return authentication.getName();
    }
}
