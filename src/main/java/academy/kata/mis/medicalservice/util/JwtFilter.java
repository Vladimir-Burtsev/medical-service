package academy.kata.mis.medicalservice.util;

import academy.kata.mis.medicalservice.config.SecurityAuthenticationEntryPoint;
import academy.kata.mis.medicalservice.exceptions.AuthException;
import academy.kata.mis.medicalservice.model.dto.auth.JwtAuthentication;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private static final String AUTHORIZATION = "Authorization";
    private final JwtProvider jwtProvider;
    private final SecurityAuthenticationEntryPoint securityAuthenticationEntryPoint;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {
        String bearer = request.getHeader(AUTHORIZATION);
        String token = jwtProvider.getTokenFromRequest(bearer);
        if (token != null) {
            if (jwtProvider.validateAccessToken(token)) {
                JwtAuthentication authentication = jwtProvider.getAuthentication(token);
                if (authentication.isAuthenticated()) {
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    chain.doFilter(request, response);
                } else {
                    invalidRequest(request, response, HttpStatus.FORBIDDEN.value(), "Access token forbidden.");
                }
            } else {
                invalidRequest(request, response, HttpStatus.UNAUTHORIZED.value(),"Access token invalid.");
            }
        } else {
            chain.doFilter(request, response);
        }
    }

    private void invalidRequest(HttpServletRequest request,
                                HttpServletResponse response,
                                int status,
                                String message) throws IOException {
        SecurityContextHolder.clearContext();
        response.setStatus(status);
        securityAuthenticationEntryPoint.commence(request, response,
                new AuthException(message));
    }

}