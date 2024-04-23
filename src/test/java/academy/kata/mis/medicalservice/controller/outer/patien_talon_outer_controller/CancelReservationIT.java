package academy.kata.mis.medicalservice.controller.outer.patien_talon_outer_controller;

import academy.kata.mis.medicalservice.ContextIT;
import academy.kata.mis.medicalservice.controller.outer.PatientTalonOuterController;
import academy.kata.mis.medicalservice.exceptions.AuthException;
import academy.kata.mis.medicalservice.feign.PersonFeignClient;
import academy.kata.mis.medicalservice.feign.StructureFeignClient;
import academy.kata.mis.medicalservice.model.dto.auth.JwtAuthentication;
import academy.kata.mis.medicalservice.model.dto.auth.Role;
import academy.kata.mis.medicalservice.model.dto.feign.OrganizationDto;
import academy.kata.mis.medicalservice.model.dto.feign.PersonDto;
import academy.kata.mis.medicalservice.model.entity.MedicalService;
import academy.kata.mis.medicalservice.model.entity.Patient;
import academy.kata.mis.medicalservice.model.entity.Talon;
import academy.kata.mis.medicalservice.repository.TalonRepository;
import academy.kata.mis.medicalservice.service.AuditMessageService;
import academy.kata.mis.medicalservice.service.TalonService;
import academy.kata.mis.medicalservice.util.JwtProvider;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.checkerframework.checker.units.qual.A;
import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.util.AssertionErrors;

import java.util.Set;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/controller/outer/patient_talon_outer_controller/cancel_reservation.sql")
@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/clear.sql")
public class CancelReservationIT extends ContextIT {

    @MockBean
    private JwtProvider jwtProvider;

    @MockBean
    private PatientTalonOuterController patientTalonOuterController;

    @Autowired
    private TalonService talonService;

    private String accessToken = "Bearer token";

    @Test
    public void cancelReservation_success() throws Exception {

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
//                                .contentType(MediaType.APPLICATION_JSON)
                                .header("Authorization", accessToken)
                                .param("talon_id", String.valueOf(talonId))
                )
                .andExpect(status().isOk());

        //Проверям ошибку в запросе. Запрос без параметра TalonId
        mockMvc.perform(
                        patch("/api/medical/patient/talon/unassign")
                                .header("Authorization", accessToken)
                )
                .andExpect(status().is4xxClientError());

        //проверяем что была одна попытка отправить запрос в медикал сервис
        verify(patientTalonOuterController, times(1)).cancelReservation(anyLong(), any());
    }

    @Test
    public void cancelReservationTalonFromTalonServiceSuccess() {
        String userIdSuccess = "cf29361a-c9ed-4644-a6dc-db639774850e";
        long talonIdSuccess = 1L;
        talonService.cancelReservationTalon(talonIdSuccess, UUID.fromString(userIdSuccess));
        Assert.assertEquals(null, talonService.findById(talonIdSuccess).getPatient());
    }


    //Проверяем выброс исключения IllegalArgumentException, если talonId < 0
    @Test
    public void cancelReservationTalonFromTalonServiceExceptionNegativeTalonId() {
        String userIdSuccess = "cf29361a-c9ed-4644-a6dc-db639774850e";
        long talonIdException = -2L;
        boolean exception = false;
        try {
            talonService.cancelReservationTalon(talonIdException, UUID.fromString(userIdSuccess));
        } catch (IllegalArgumentException ex) {
            exception = true;
        }
        Assert.assertTrue(exception);
    }

    //Проверяем выброс исключения IllegalArgumentException, если talonId нет в базе данных (не существует)
    @Test
    public void cancelReservationTalonFromTalonServiceExceptionNoTalonId() {
        String userIdSuccess = "cf29361a-c9ed-4644-a6dc-db639774850e";
        long talonIdException = 34;
        boolean exception = false;
        try {
            talonService.cancelReservationTalon(talonIdException, UUID.fromString(userIdSuccess));
        } catch (IllegalArgumentException ex) {
            exception = true;
        }
        Assert.assertTrue(exception);
    }

    //Проверяем выброс исключения AuthException, если зарегистрированный пользователь пытается отменить запись
    // к врачу другого пользователя. Т.е. userTalonId != userAuthId
    @Test
    public void cancelReservationTalonFromTalonServiceExceptionUserIdNoEqualsUserAuthId() {
        String userIdException = "3c0f6e56-69f3-459e-9fe8-5069d4537951";
        long talonIdSuccess = 1L;
        boolean exception = false;
        try {
            talonService.cancelReservationTalon(talonIdSuccess, UUID.fromString(userIdException));
        } catch (AuthException ex) {
            exception = true;
        }
        Assert.assertTrue(exception);
    }
}
