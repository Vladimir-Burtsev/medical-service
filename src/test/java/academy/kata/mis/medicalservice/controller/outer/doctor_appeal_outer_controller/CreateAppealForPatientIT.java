package academy.kata.mis.medicalservice.controller.outer.doctor_appeal_outer_controller;

import academy.kata.mis.medicalservice.ContextIT;
import academy.kata.mis.medicalservice.feign.PersonFeignClient;
import academy.kata.mis.medicalservice.model.dto.CreateAppealForPatientRequest;
import academy.kata.mis.medicalservice.model.dto.auth.JwtAuthentication;
import academy.kata.mis.medicalservice.model.dto.auth.Role;
import academy.kata.mis.medicalservice.model.dto.feign.PersonDto;
import academy.kata.mis.medicalservice.util.JwtProvider;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import java.util.Set;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value =
        "/scripts/controller.outer.doctor_appeal_outer_controller/createAppealForPatient.sql")
@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/clear.sql")
public class CreateAppealForPatientIT extends ContextIT {
    @MockBean
    private PersonFeignClient personFeignClient;

    @MockBean
    private JwtProvider jwtProvider;

    private final String accessToken = "Bearer token";

    @Test
    public void createAppealForPatient_success() throws Exception {
        String user = "cf29361a-c9ed-4644-a6dc-db639774860e";
        CreateAppealForPatientRequest request = new CreateAppealForPatientRequest(1L, 1L, 1L);

        JwtAuthentication jwtInfoToken = new JwtAuthentication();
        jwtInfoToken.setUserId(UUID.fromString(user));
        jwtInfoToken.setRoles(Set.of(new Role("PATIENT"), new Role("DOCTOR")));
        jwtInfoToken.setAuthenticated(true);
        when(jwtProvider.getTokenFromRequest("Bearer token")).thenReturn("token");
        when(jwtProvider.validateAccessToken("token")).thenReturn(true);
        when(jwtProvider.getAuthentication("token")).thenReturn(jwtInfoToken);

        when(personFeignClient.getPersonById(1L)).thenReturn(new PersonDto(1L, "Vasya", "Pupkin"));

        mockMvc.perform(post(
                "/api/medical/doctor/appeal/create")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", accessToken)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().string("Обращение создано"));
    }

    @Test
    public void createAppealForPatient_noDoctor() throws Exception {
        String user = "cf29361a-c9ed-4644-a6dc-db639774860e";
        CreateAppealForPatientRequest request = new CreateAppealForPatientRequest(4L, 1L, 1L);

        JwtAuthentication jwtInfoToken = new JwtAuthentication();
        jwtInfoToken.setUserId(UUID.fromString(user));
        jwtInfoToken.setRoles(Set.of(new Role("PATIENT"), new Role("DOCTOR")));
        jwtInfoToken.setAuthenticated(true);
        when(jwtProvider.getTokenFromRequest("Bearer token")).thenReturn("token");
        when(jwtProvider.validateAccessToken("token")).thenReturn(true);
        when(jwtProvider.getAuthentication("token")).thenReturn(jwtInfoToken);


        mockMvc.perform(post(
                "/api/medical/doctor/appeal/create")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", accessToken)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().string("Доктор не найден"));
    }

    @Test
    public void createAppealForPatient_diseaseDepDoesntExistAndNotMatchesDoctorDepartment() throws Exception {
        String user = "cf29361a-c9ed-4644-a6dc-db639774860e";
        CreateAppealForPatientRequest request = new CreateAppealForPatientRequest(1L, 1L, 4L);

        JwtAuthentication jwtInfoToken = new JwtAuthentication();
        jwtInfoToken.setUserId(UUID.fromString(user));
        jwtInfoToken.setRoles(Set.of(new Role("PATIENT"), new Role("DOCTOR")));
        jwtInfoToken.setAuthenticated(true);
        when(jwtProvider.getTokenFromRequest("Bearer token")).thenReturn("token");
        when(jwtProvider.validateAccessToken("token")).thenReturn(true);
        when(jwtProvider.getAuthentication("token")).thenReturn(jwtInfoToken);

        mockMvc.perform(post(
                "/api/medical/doctor/appeal/create")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", accessToken)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().string("Заболевание не найдено или не совпадает с отделением доктора."));
    }

    @Test
    public void createAppealForPatient_patientDoesntExistsAndNotFromSameOrganizationAsDoctor() throws Exception {
        String user = "cf29361a-c9ed-4644-a6dc-db639774860e";
        CreateAppealForPatientRequest request = new CreateAppealForPatientRequest(1L, 4L, 1L);

        JwtAuthentication jwtInfoToken = new JwtAuthentication();
        jwtInfoToken.setUserId(UUID.fromString(user));
        jwtInfoToken.setRoles(Set.of(new Role("PATIENT"), new Role("DOCTOR")));
        jwtInfoToken.setAuthenticated(true);
        when(jwtProvider.getTokenFromRequest("Bearer token")).thenReturn("token");
        when(jwtProvider.validateAccessToken("token")).thenReturn(true);
        when(jwtProvider.getAuthentication("token")).thenReturn(jwtInfoToken);

        when(personFeignClient.getPersonById(1L)).thenReturn(new PersonDto(1L, "Vasya", "Pupkin"));

        mockMvc.perform(post(
                "/api/medical/doctor/appeal/create")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", accessToken)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().string("Пациент не существует или находится с доктором в разных организациях"));

        verify(personFeignClient, times(1)).getPersonById(anyLong());
    }
}
