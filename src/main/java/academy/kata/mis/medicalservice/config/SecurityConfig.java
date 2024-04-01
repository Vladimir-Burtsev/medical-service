package academy.kata.mis.medicalservice.config;

import academy.kata.mis.medicalservice.util.JwtFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        // Отключаем базовую индентификацию с помощью логина пароля и форму идентификации
        http
                .httpBasic().disable()
                .formLogin().disable();

        // Включаем CORS и выключаем CSRF
        http = http.cors().and().csrf().disable();


        // Отключаем хранение состояния сессии на сервере
        http = http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and();


        // Устанавливаем обработчик запросов для неавторизованных запросов
        http = http
                .exceptionHandling()
                .authenticationEntryPoint(
                        (request, response, ex) -> {
                            response.sendError(
                                    HttpServletResponse.SC_UNAUTHORIZED,
                                    ex.getMessage()
                            );

                        }
                )
                .and();

        // Добавляем фильтр JWT токена
        http.addFilterBefore(
                jwtFilter,
                UsernamePasswordAuthenticationFilter.class
        );

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring()
                .requestMatchers(new RequestMatcher() {
                    @Override
                    public boolean matches(HttpServletRequest request) {
                        return HttpMethod.OPTIONS.matches(request.getMethod())
                                || Arrays.asList(
                                "/api/swagger-ui/**",
                                "/api/swagger-config",
                                "/api",
                                "/api/doc",
                                "/metrics/**",
                                "/health/**",
                                "/info/**",
                                "/loggers/**",
                                "/internal/*"
                        ).stream().anyMatch(pattern -> new AntPathRequestMatcher(pattern).matches(request));
                    }
                });
    }

}
