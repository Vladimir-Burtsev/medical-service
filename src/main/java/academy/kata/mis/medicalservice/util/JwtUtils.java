package academy.kata.mis.medicalservice.util;

import academy.kata.mis.medicalservice.model.dto.auth.JwtAuthentication;
import academy.kata.mis.medicalservice.model.dto.auth.JwtAuthenticationDto;
import academy.kata.mis.medicalservice.model.dto.auth.Role;
import academy.kata.mis.medicalservice.model.enums.RoleNameEnum;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class JwtUtils {

    public static JwtAuthentication generate(JwtAuthenticationDto requestDto) {
        final JwtAuthentication jwtInfoToken = new JwtAuthentication();
        jwtInfoToken.setUserId(requestDto.user());
        jwtInfoToken.setRoles(getRoles(requestDto.claims()));
        return jwtInfoToken;
    }

    private static Set<Role> getRoles(List<String> claims) {
        return Arrays.stream(RoleNameEnum.values())
                .map(Enum::name)
                .toList()
                .stream()
                .filter(claims::contains)
                .map(Role::new)
                .collect(Collectors.toSet());
    }
}
