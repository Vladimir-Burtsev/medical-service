package academy.kata.mis.medicalservice.util;

import academy.kata.mis.medicalservice.exceptions.AuthException;
import academy.kata.mis.medicalservice.model.dto.auth.JwtAuthentication;
import academy.kata.mis.medicalservice.model.dto.auth.JwtAuthenticationDto;
import academy.kata.mis.medicalservice.service.AccessTokenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtFilter extends GenericFilterBean {
    private static final String AUTHORIZATION = "Authorization";
    private final JwtProvider jwtProvider;
    private final ObjectMapper objectMapper;
    private final AccessTokenService accessTokenService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain fc)
            throws IOException, ServletException {

        final String token = getTokenFromRequest((HttpServletRequest) request);

        if (token != null && !accessTokenService.isBlocked(token) && jwtProvider.validateAccessToken(token)) {
            String[] chunks = token.split("\\.");
            Base64.Decoder decoder = Base64.getUrlDecoder();
            String payload = new String(decoder.decode(chunks[1]), StandardCharsets.UTF_8);
            JwtAuthenticationDto requestDto = objectMapper.readValue(payload, JwtAuthenticationDto.class);
            validateRequest(requestDto);
            JwtAuthentication jwtInfoToken = JwtUtils.generate(requestDto);
            jwtInfoToken.setAuthenticated(true);
            SecurityContextHolder.getContext().setAuthentication(jwtInfoToken);
            fc.doFilter(request, response);
        } else {
            SecurityContextHolder.clearContext();
            throw new AuthException("123");
        }

    }

    private String getTokenFromRequest(HttpServletRequest request) {
        final String bearer = request.getHeader(AUTHORIZATION);
        if (StringUtils.hasText(bearer) && bearer.startsWith("Bearer ")) {
            return bearer.substring(7);
        }
        return null;
    }

    private void validateRequest(JwtAuthenticationDto requestDto) {
        if (requestDto == null) {
            throw new AuthException("Request data is empty");
        }
        if (requestDto.user() == null) {
            throw new AuthException("User is empty");
        }
        if (new Date().after(requestDto.expirationDate())) {
            throw new AuthException("Token is expired");
        }
    }

}