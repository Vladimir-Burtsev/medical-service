package academy.kata.mis.medicalservice.util;

import academy.kata.mis.medicalservice.exceptions.AuthException;
import academy.kata.mis.medicalservice.model.dto.auth.JwtAuthentication;
import academy.kata.mis.medicalservice.model.dto.auth.JwtAuthenticationDto;
import academy.kata.mis.medicalservice.model.dto.auth.Role;
import academy.kata.mis.medicalservice.model.enums.RoleNameEnum;
import academy.kata.mis.medicalservice.service.AccessTokenService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.validation.constraints.NotBlank;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtProvider {
    private final SecretKey jwtAccessSecret;
    private final AccessTokenService accessTokenService;
    private final ObjectMapper objectMapper;

    public JwtProvider(@Value("${jwt.access.secret}") String jwtAccessSecret,
                       AccessTokenService accessTokenService,
                       ObjectMapper objectMapper) {
        this.jwtAccessSecret = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtAccessSecret));
        this.accessTokenService = accessTokenService;
        this.objectMapper = objectMapper;
    }

    public boolean validateAccessToken(@NotBlank String accessToken) {
        if (!accessTokenService.isBlocked(accessToken)) {
            try {
                Jwts.parserBuilder()
                        .setSigningKey(jwtAccessSecret)
                        .build()
                        .parseClaimsJws(accessToken);
                return true;
            } catch (Exception e) {
                log.error("invalid token", e);
            }
        }
        return false;
    }

    public JwtAuthentication getAuthentication(String accessToken) throws JsonProcessingException {
        String[] chunks = accessToken.split("\\.");
        Base64.Decoder decoder = Base64.getUrlDecoder();
        String payload = new String(decoder.decode(chunks[1]), StandardCharsets.UTF_8);
        JwtAuthenticationDto requestDto = objectMapper.readValue(payload, JwtAuthenticationDto.class);
        validateRequest(requestDto);
        JwtAuthentication jwtInfoToken = generate(requestDto);
        jwtInfoToken.setAuthenticated(true);
        return jwtInfoToken;
    }

    public String getTokenFromRequest(String bearer) {
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

    private JwtAuthentication generate(JwtAuthenticationDto requestDto) {
        final JwtAuthentication jwtInfoToken = new JwtAuthentication();
        jwtInfoToken.setUserId(requestDto.user());
        jwtInfoToken.setRoles(getRoles(requestDto.claims()));
        return jwtInfoToken;
    }

    private Set<Role> getRoles(List<String> claims) {
        return Arrays.stream(RoleNameEnum.values())
                .map(Enum::name)
                .toList()
                .stream()
                .filter(claims::contains)
                .map(Role::new)
                .collect(Collectors.toSet());
    }
}
