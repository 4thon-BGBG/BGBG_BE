package com.example.bgbg.jwt;

import java.io.IOException;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.bgbg.code.ErrorCode;
import com.example.bgbg.entity.User;
import com.example.bgbg.exception.GlobalException;
import com.example.bgbg.repository.user.UserRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;
    private final UserRepository userRepository;

    private static final List<String> NO_AUTH_URLS =
            List.of(
                    "/login",
                    "/register",
                    "/v3/api-docs",
                    "/swagger-ui",
                    "/swagger-resources",
                    "/webjars");

    public JWTFilter(JWTUtil jwtUtil, UserRepository userRepository) {
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        return NO_AUTH_URLS.stream().anyMatch(url -> path.startsWith(url));
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String authorization = request.getHeader("Authorization");
        log.info("Authorization header: {}", authorization);
        log.info("Request URI: {}", request.getRequestURI());

        if (authorization != null && authorization.startsWith("Bearer ")) {
            String token = authorization.substring(7);
            try {
                String username = jwtUtil.validationJWT(token);

                User user =
                        userRepository
                                .findByUsername(username)
                                .orElseThrow(() -> new GlobalException(ErrorCode.USER_NOT_FOUND));

                var authorities = List.of(new SimpleGrantedAuthority("ROLE_USER"));
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(user, null, authorities);

                SecurityContextHolder.getContext().setAuthentication(authToken);

            } catch (Exception e) {
                log.error("Invalid JWT Token", e);
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
        } else {
            log.info("No JWT token found in request headers");
        }

        filterChain.doFilter(request, response);
    }
}
