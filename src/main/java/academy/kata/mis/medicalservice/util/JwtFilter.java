package academy.kata.mis.medicalservice.util;

import academy.kata.mis.medicalservice.exceptions.TokenException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtFilter extends GenericFilterBean {
    private static final String AUTHORIZATION = "Authorization";
    private final JwtProvider jwtProvider;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain fc)
            throws IOException, ServletException {
        String bearer = ((HttpServletRequest) request).getHeader(AUTHORIZATION);
        String token = jwtProvider.getTokenFromRequest(bearer);
        if (token != null && jwtProvider.validateAccessToken(token)) {
            SecurityContextHolder.getContext().setAuthentication(jwtProvider.getAuthentication(token));
            fc.doFilter(request, response);
        } else {
            SecurityContextHolder.clearContext();
            throw new TokenException("AccessToken is not valid");
        }
    }

}