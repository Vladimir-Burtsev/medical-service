package academy.kata.mis.medicalservice.controller.outer.patien_talon_outer_controller;

import academy.kata.mis.medicalservice.ContextIT;
import academy.kata.mis.medicalservice.feign.PersonFeignClient;
import academy.kata.mis.medicalservice.feign.StructureFeignClient;
import academy.kata.mis.medicalservice.model.dto.auth.JwtAuthentication;
import academy.kata.mis.medicalservice.model.dto.auth.Role;
import academy.kata.mis.medicalservice.model.dto.department_organization.DepartmentAndOrganizationDto;
import academy.kata.mis.medicalservice.model.dto.feign.PersonDto;
import academy.kata.mis.medicalservice.service.AuditMessageService;
import academy.kata.mis.medicalservice.service.ReportServiceSender;
import academy.kata.mis.medicalservice.util.JwtProvider;
import org.junit.jupiter.api.Test;
import org.mockito.Spy;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.jdbc.Sql;

import java.util.Set;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Sql(executionPhase = Sql.ExecutionPhase
        .BEFORE_TEST_METHOD, value = "/scripts/controller/outer/patient_talon_outer_controller/cancel_reservation.sql")
@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/clear.sql")
public class CancelReservationIT extends ContextIT {

    @MockBean
    private JwtProvider jwtProvider;
    @MockBean
    private AuditMessageService auditMessageService;
    @Spy
    private ReportServiceSender reportServiceSender;
    @MockBean
    private PersonFeignClient personFeignClient;
    @MockBean
    StructureFeignClient structureFeignClient;

    private final String accessToken = "Bearer token";

    //Проверяем отработку метода cancelReservation, если корректно даны talonId и userId
    @Test
    public void cancelReservationTalon_success() throws Exception {

        String userId = "cf29361a-c9ed-4644-a6dc-db639774850e";
        long talonId = 1L;

        //задаем нужное нам поведение при проверке токена
        JwtAuthentication jwtInfoToken = new JwtAuthentication();
        jwtInfoToken.setUserId(UUID.fromString(userId));
        jwtInfoToken.setRoles(Set.of(new Role("PATIENT")));
        jwtInfoToken.setAuthenticated(true);
        when(jwtProvider.getTokenFromRequest("Bearer token")).thenReturn("token");
        when(jwtProvider.validateAccessToken("token")).thenReturn(true);
        when(jwtProvider.getAuthentication("token")).thenReturn(jwtInfoToken);
        when(personFeignClient.getPersonEmailByUserId(any())).thenReturn("email");
        when(personFeignClient.getPersonById(anyLong()))
                .thenReturn(new PersonDto(1L, "Fist Name", "Last Name"));
        when(structureFeignClient.getDepartmentAndOrganizationName(anyLong()))
                .thenReturn(new DepartmentAndOrganizationDto(
                        1L,
                        "Department Name1",
                        1L,
                        "Organization Name1"));

        doNothing().when(reportServiceSender)
                .sendInMessageService(any(), any(), any(), any(), any(), any(), any(), any());

        mockMvc.perform(
                        patch("/api/medical/patient/talon/unassign")
                                .header("Authorization", accessToken)
                                .param("talon_id", String.valueOf(talonId))
                )
                .andExpect(status().isOk());

        //проверяем что была попытка отправить запрос в аудит сервис
        verify(auditMessageService, times(1)).sendAudit(anyString(), anyString(), anyString());
    }

    //Проверяем выброс исключения и соответствие сообщения при выбросе исключения,
    // если talonId нет в базе данных (не существует)
    @Test
    public void cancelReservationTalon_IncorrectTalonId() throws Exception {

        String userId = "cf29361a-c9ed-4644-a6dc-db639774850e";
        long talonId = 5L;
        String answerException = String.format("Талон с Id = %s у пользователя с userId = %s не существует.",
                talonId,
                userId
        );

        //задаем нужное нам поведение при проверке токена
        JwtAuthentication jwtInfoToken = new JwtAuthentication();
        jwtInfoToken.setUserId(UUID.fromString(userId));
        jwtInfoToken.setRoles(Set.of(new Role("PATIENT")));
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
                .andExpect(status().is(422))
                .andExpect(content().string(answerException));

        //проверяем что не было попыток отправить запрос в message service
        verify(reportServiceSender, times(0))
                .sendInMessageService(any(), any(), any(), any(), any(), any(), any(), any());

        //проверяем что не было попыток отправить запрос в аудит сервис
        verify(auditMessageService, times(0)).sendAudit(anyString(), anyString(), anyString());
    }

    //Проверяем выброс исключения и соответствие сообщения при выбросе исключения,
    //если зарегистрированный пользователь пытается отменить запись к врачу другого пользователя
    @Test
    public void cancelReservationTalon_IncorrectUserId() throws Exception {

        String userId = "cf29361a-c9ed-4644-a6dc-db639774850e";
        long talonId = 2L;
        String answerException = String.format("Талон с Id = %s у пользователя с userId = %s не существует.",
                talonId,
                userId
        );

        //задаем нужное нам поведение при проверке токена
        JwtAuthentication jwtInfoToken = new JwtAuthentication();
        jwtInfoToken.setUserId(UUID.fromString(userId));
        jwtInfoToken.setRoles(Set.of(new Role("PATIENT")));
        jwtInfoToken.setAuthenticated(true);
        when(jwtProvider.getTokenFromRequest("Bearer token")).thenReturn("token");
        when(jwtProvider.validateAccessToken("token")).thenReturn(true);
        when(jwtProvider.getAuthentication("token")).thenReturn(jwtInfoToken);

        mockMvc.perform(
                        patch("/api/medical/patient/talon/unassign")
                                .header("Authorization", accessToken)
                                .param("talon_id", String.valueOf(talonId))
                )
                .andExpect(status().is(422))
                .andExpect(content().string(answerException));

        //проверяем что не было попыток отправить запрос в message service
        verify(reportServiceSender, times(0))
                .sendInMessageService(any(), any(), any(), any(), any(), any(), any(), any());

        //проверяем что не было попыток отправить запрос в аудит сервис
        verify(auditMessageService, times(0)).sendAudit(anyString(), anyString(), anyString());
    }

    //Проверяем выброс исключения и соответствие сообщения при выбросе исключения,
    //если у талона нет пользователя (patient = null)
    @Test
    public void cancelReservationTalon_PatientNull() throws Exception {

        String userId = "cf29361a-c9ed-4644-a6dc-db639774850e";
        long talonId = 3L;
        String answerException = String.format("Талон с Id = %s у пользователя с userId = %s не существует.",
                talonId,
                userId
        );

        //задаем нужное нам поведение при проверке токена
        JwtAuthentication jwtInfoToken = new JwtAuthentication();
        jwtInfoToken.setUserId(UUID.fromString(userId));
        jwtInfoToken.setRoles(Set.of(new Role("PATIENT")));
        jwtInfoToken.setAuthenticated(true);
        when(jwtProvider.getTokenFromRequest("Bearer token")).thenReturn("token");
        when(jwtProvider.validateAccessToken("token")).thenReturn(true);
        when(jwtProvider.getAuthentication("token")).thenReturn(jwtInfoToken);

        mockMvc.perform(
                        patch("/api/medical/patient/talon/unassign")
                                .header("Authorization", accessToken)
                                .param("talon_id", String.valueOf(talonId))
                )
                .andExpect(status().is(422))
                .andExpect(content().string(answerException));

        //проверяем что не было попыток отправить запрос в message service
        verify(reportServiceSender, times(0))
                .sendInMessageService(any(), any(), any(), any(), any(), any(), any(), any());

        //проверяем что не было попыток отправить запрос в аудит сервис
        verify(auditMessageService, times(0)).sendAudit(anyString(), anyString(), anyString());
    }
}
