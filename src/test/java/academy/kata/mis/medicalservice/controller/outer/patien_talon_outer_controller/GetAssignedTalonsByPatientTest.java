package academy.kata.mis.medicalservice.controller.outer.patien_talon_outer_controller;

import academy.kata.mis.medicalservice.ContextIT;
import academy.kata.mis.medicalservice.feign.PersonFeignClient;
import academy.kata.mis.medicalservice.feign.StructureFeignClient;
import academy.kata.mis.medicalservice.model.dto.auth.JwtAuthentication;
import academy.kata.mis.medicalservice.model.dto.auth.Role;
import academy.kata.mis.medicalservice.model.dto.person.PersonFullNameDto;
import academy.kata.mis.medicalservice.model.dto.positions.PositionsNameAndCabinetDto;
import academy.kata.mis.medicalservice.util.JwtProvider;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.jdbc.Sql;

import java.util.NoSuchElementException;
import java.util.Set;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/controller/outer/patient_talon_outer_controller/dataForGetAssignedTalonsByPatientTest.sql")
@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/clear.sql")
public class GetAssignedTalonsByPatientTest extends ContextIT {

    @MockBean
    private PersonFeignClient personFeignClient;

    @MockBean
    private StructureFeignClient structureFeignClient;

    @MockBean
    private JwtProvider jwtProvider;

    private final String accessToken = "Bearer token";
    private final String userId = "662b6f6e-4702-44c4-98f4-e73243087d46";


    @Test
    public void getAssignedTalonsByPatient_success() throws Exception {
        long patientId = 1L;
        JwtAuthentication jwtInfoToken = new JwtAuthentication();
        jwtInfoToken.setUserId(UUID.fromString(userId));
        jwtInfoToken.setRoles(Set.of(new Role("PATIENT")));
        jwtInfoToken.setAuthenticated(true);
        when(jwtProvider.getTokenFromRequest("Bearer token")).thenReturn("token");
        when(jwtProvider.validateAccessToken("token")).thenReturn(true);
        when(jwtProvider.getAuthentication("token")).thenReturn(jwtInfoToken);


        PersonFullNameDto patient = new PersonFullNameDto(1, "Name",
                "LastName", "Patronymic");
        when(personFeignClient.getPersonFullNameDtoById(1L)).thenReturn(patient);

        PositionsNameAndCabinetDto positionsNameAndCabinetDto = new PositionsNameAndCabinetDto(1,
                "position medical_staff DOCTOR  name11", "value of cabinet num: 7");
        when(structureFeignClient.getPositionsNameAndCabinetById(1L)).thenReturn(positionsNameAndCabinetDto);


        mockMvc.perform(
                        get("/api/medical/patient/talon/assigned")
                                .header("Authorization", accessToken)
                                .param("patient_id", String.valueOf(patientId))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("talons.length()").value(2))
                .andExpect(jsonPath("talons[0].talonId").value(2))
                .andExpect(jsonPath("talons[0].visitTime").value("2024-05-17T10:00:00"))
                .andExpect(jsonPath("talons[0].doctorId").value(1))
                .andExpect(jsonPath("talons[1].talonId").value(1))
                .andExpect(jsonPath("talons[1].visitTime").value("2024-05-17T10:00:00"))
                .andExpect(jsonPath("talons[1].doctorId").value(1));

        verify(personFeignClient, times(1)).getPersonFullNameDtoById(1);

        verify(structureFeignClient, times(1)).getPositionsNameAndCabinetById(1);
    }


    @Test
    public void getAssignedTalonsByPatient_patientNotExist() throws Exception {
        long patientId = 3L;
        JwtAuthentication jwtInfoToken = new JwtAuthentication();
        jwtInfoToken.setUserId(UUID.fromString(userId));
        jwtInfoToken.setRoles(Set.of(new Role("PATIENT")));
        jwtInfoToken.setAuthenticated(true);
        when(jwtProvider.getTokenFromRequest("Bearer token")).thenReturn("token");
        when(jwtProvider.validateAccessToken("token")).thenReturn(true);
        when(jwtProvider.getAuthentication("token")).thenReturn(jwtInfoToken);

        mockMvc.perform(
                        get("/api/medical/patient/talon/assigned")
                                .header("Authorization", accessToken)
                                .param("patient_id", String.valueOf(patientId))
                )
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().string("Пациент с ID: " + patientId + " - не найден, или у вас нет прав доступа"));
    }


    @Test
    public void getAssignedTalonsByPatient_userInNotAccess() throws Exception {
        long patientId = 1L;
        String userId = "599d9ef0-7ae0-4924-890b-55eb13f85e53";
        JwtAuthentication jwtInfoToken = new JwtAuthentication();
        jwtInfoToken.setUserId(UUID.fromString(userId));
        jwtInfoToken.setRoles(Set.of(new Role("PATIENT")));
        jwtInfoToken.setAuthenticated(true);
        when(jwtProvider.getTokenFromRequest("Bearer token")).thenReturn("token");
        when(jwtProvider.validateAccessToken("token")).thenReturn(true);
        when(jwtProvider.getAuthentication("token")).thenReturn(jwtInfoToken);

        mockMvc.perform(
                        get("/api/medical/patient/talon/assigned")
                                .header("Authorization", accessToken)
                                .param("patient_id", String.valueOf(patientId))
                )
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content()
                        .string("Пациент с ID: " + patientId + " - не найден, или у вас нет прав доступа"));
    }


    @Test
    public void getAssignedTalonsByPatient_personNotExist() throws Exception {
        long patientId = 1L;
        JwtAuthentication jwtInfoToken = new JwtAuthentication();
        jwtInfoToken.setUserId(UUID.fromString(userId));
        jwtInfoToken.setRoles(Set.of(new Role("PATIENT")));
        jwtInfoToken.setAuthenticated(true);
        when(jwtProvider.getTokenFromRequest("Bearer token")).thenReturn("token");
        when(jwtProvider.validateAccessToken("token")).thenReturn(true);
        when(jwtProvider.getAuthentication("token")).thenReturn(jwtInfoToken);


        when(personFeignClient.getPersonFullNameDtoById(1L)).thenThrow(NoSuchElementException.class);

        mockMvc.perform(
                        get("/api/medical/patient/talon/assigned")
                                .header("Authorization", accessToken)
                                .param("patient_id", String.valueOf(patientId))
                )
                .andExpect(status().isNotFound());

        verify(personFeignClient, times(1)).getPersonFullNameDtoById(1);
    }


    @Test
    public void getAssignedTalonsByPatient_positionNotExist() throws Exception {
        long patientId = 1L;
        JwtAuthentication jwtInfoToken = new JwtAuthentication();
        jwtInfoToken.setUserId(UUID.fromString(userId));
        jwtInfoToken.setRoles(Set.of(new Role("PATIENT")));
        jwtInfoToken.setAuthenticated(true);
        when(jwtProvider.getTokenFromRequest("Bearer token")).thenReturn("token");
        when(jwtProvider.validateAccessToken("token")).thenReturn(true);
        when(jwtProvider.getAuthentication("token")).thenReturn(jwtInfoToken);


        PersonFullNameDto patient = new PersonFullNameDto(1, "Name",
                "LastName", "Patronymic");
        when(personFeignClient.getPersonFullNameDtoById(1L)).thenReturn(patient);

        when(structureFeignClient.getPositionsNameAndCabinetById(1L)).thenThrow(NoSuchElementException.class);


        mockMvc.perform(
                        get("/api/medical/patient/talon/assigned")
                                .header("Authorization", accessToken)
                                .param("patient_id", String.valueOf(patientId))
                )
                .andExpect(status().isNotFound());

        verify(personFeignClient, times(1)).getPersonFullNameDtoById(1);

        verify(structureFeignClient, times(1)).getPositionsNameAndCabinetById(1);
    }
}
