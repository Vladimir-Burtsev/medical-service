package academy.kata.mis.medicalservice.controller.outer.patien_talon_outer_controller;

import academy.kata.mis.medicalservice.ContextIT;
import academy.kata.mis.medicalservice.model.dto.auth.JwtAuthentication;
import academy.kata.mis.medicalservice.model.dto.auth.Role;
import academy.kata.mis.medicalservice.util.JwtProvider;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.jdbc.Sql;

import java.util.Set;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/controller/outer/patient_talon_outer_controller/cancel_reservation.sql")
@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/clear.sql")
public class CancelReservationIT extends ContextIT {
    @MockBean
    private JwtProvider jwtProvider;

    private final String accessToken = "Bearer token";

    //Проверяем отработку метода cancelReservation, если корректно даны talonId и userId
    @Test
    public void cancelReservationTalon_success() throws Exception {

        String userId = "cf29361a-c9ed-4644-a6dc-db639774850e";
        long talonId = 1L;

        //задаем нужное нам поведение при проверке токена
        JwtAuthentication jwtInfoToken = new JwtAuthentication();
        jwtInfoToken.setUserId(UUID.fromString(userId));
        jwtInfoToken.setRoles(Set.of(new Role("PATIENT"), new Role("DOCTOR")));
        jwtInfoToken.setAuthenticated(true);
        when(jwtProvider.getTokenFromRequest("Bearer token")).thenReturn("token");
        when(jwtProvider.validateAccessToken("token")).thenReturn(true);
        when(jwtProvider.getAuthentication("token")).thenReturn(jwtInfoToken);

        //запустим тест
        mockMvc.perform(
                        patch("/api/medical/patient/talon/unassign")
                                .header("Authorization", accessToken)
                                .param("talon_id", String.valueOf(talonId))
                )
                .andExpect(status().isOk());
    }

    //Проверяем выброс исключения, если talonId нет в базе данных (не существует)
    @Test
    public void cancelReservationTalonIncorrectTalonId() throws Exception {

        String userId = "cf29361a-c9ed-4644-a6dc-db639774850e";
        long talonId = 5L;

        //задаем нужное нам поведение при проверке токена
        JwtAuthentication jwtInfoToken = new JwtAuthentication();
        jwtInfoToken.setUserId(UUID.fromString(userId));
        jwtInfoToken.setRoles(Set.of(new Role("PATIENT"), new Role("DOCTOR")));
        jwtInfoToken.setAuthenticated(true);
        when(jwtProvider.getTokenFromRequest("Bearer token")).thenReturn("token");
        when(jwtProvider.validateAccessToken("token")).thenReturn(true);
        when(jwtProvider.getAuthentication("token")).thenReturn(jwtInfoToken);

        //запустим тест
        mockMvc.perform(
                        patch("/api/medical/patient/talon/unassign")
                                .header("Authorization", accessToken)
                                .param("talon_id", String.valueOf(talonId))
                )
                .andExpect(status().is4xxClientError());
    }

    //Проверяем выброс исключения, если зарегистрированный пользователь пытается отменить запись
    // к врачу другого пользователя
    @Test
    public void cancelReservationTalonIncorrectUserId() throws Exception {

        String userId = "cf29361a-c9ed-4644-a6dc-db639774850e";
        long talonId = 2L;

        //задаем нужное нам поведение при проверке токена
        JwtAuthentication jwtInfoToken = new JwtAuthentication();
        jwtInfoToken.setUserId(UUID.fromString(userId));
        jwtInfoToken.setRoles(Set.of(new Role("PATIENT"), new Role("DOCTOR")));
        jwtInfoToken.setAuthenticated(true);
        when(jwtProvider.getTokenFromRequest("Bearer token")).thenReturn("token");
        when(jwtProvider.validateAccessToken("token")).thenReturn(true);
        when(jwtProvider.getAuthentication("token")).thenReturn(jwtInfoToken);

        //запустим тест
        mockMvc.perform(
                        patch("/api/medical/patient/talon/unassign")
                                .header("Authorization", accessToken)
                                .param("talon_id", String.valueOf(talonId))
                )
                .andExpect(status().is4xxClientError());
    }
}
