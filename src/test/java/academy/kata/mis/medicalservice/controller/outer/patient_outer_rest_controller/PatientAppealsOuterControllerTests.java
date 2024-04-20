package academy.kata.mis.medicalservice.controller.outer.patient_outer_rest_controller;

import academy.kata.mis.medicalservice.ContextIT;
import academy.kata.mis.medicalservice.feign.PersonFeignClient;
import academy.kata.mis.medicalservice.model.dto.auth.JwtAuthentication;
import academy.kata.mis.medicalservice.model.dto.auth.Role;
import academy.kata.mis.medicalservice.service.ReportServiceSender;
import academy.kata.mis.medicalservice.util.JwtProvider;
import org.junit.jupiter.api.Test;
import org.mockito.Spy;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import java.util.Set;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
        value = "/scripts/controller/outer/patient_outer_rest_controller/patientAppealsOuterControllerBase.sql")
@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/clear.sql")
public class PatientAppealsOuterControllerTests extends ContextIT {

    @MockBean
    private JwtProvider jwtProvider;
    @Spy
    private ReportServiceSender reportServiceSender;
    @MockBean
    private PersonFeignClient personFeignClient;

    private String accessToken = "Bearer token";

    @Test
    public void isPatientExistAndAuthenticatedUserPatientBadId() throws Exception {
        JwtAuthentication jwtInfoToken = new JwtAuthentication();
        jwtInfoToken.setUserId(UUID.fromString("cf29361a-c9ed-4644-a6dc-db639774851e"));
        jwtInfoToken.setRoles(Set.of(new Role("PATIENT"), new Role("DOCTOR")));
        jwtInfoToken.setAuthenticated(true);

        when(jwtProvider.getTokenFromRequest("Bearer token")).thenReturn("token");
        when(jwtProvider.validateAccessToken("token")).thenReturn(true);
        when(jwtProvider.getAuthentication("token")).thenReturn(jwtInfoToken);

        String patientId = "1";

        mockMvc.perform(get("/api/medical/patient/appeal/report")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("patient_id", patientId)
                        .param("appeal_id", "1")
                        .param("send_email", "false")
                        .header("Authorization", accessToken))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().string(String.format("Current user is not patient with id %s", patientId)));
    }

    @Test
    public void isPatientExistAndAuthenticatedUserPatientNotFound() throws Exception {
        JwtAuthentication jwtInfoToken = new JwtAuthentication();
        jwtInfoToken.setUserId(UUID.fromString("cf29361a-c9ed-4644-a6dc-db639774851e"));
        jwtInfoToken.setRoles(Set.of(new Role("PATIENT"), new Role("DOCTOR")));
        jwtInfoToken.setAuthenticated(true);

        when(jwtProvider.getTokenFromRequest("Bearer token")).thenReturn("token");
        when(jwtProvider.validateAccessToken("token")).thenReturn(true);
        when(jwtProvider.getAuthentication("token")).thenReturn(jwtInfoToken);

        String patientId = "3";

        mockMvc.perform(get("/api/medical/patient/appeal/report")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("patient_id", patientId)
                        .param("appeal_id", "1")
                        .param("send_email", "false")
                        .header("Authorization", accessToken))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().string(String.format("Пациент не найден; patientId:%s;", patientId)));
    } //todo разобраться что не так

    @Test
    public void isAppealExistAndPatientOwnerBadId() throws Exception {
        JwtAuthentication jwtInfoToken = new JwtAuthentication();
        jwtInfoToken.setUserId(UUID.fromString("cf29361a-c9ed-4644-a6dc-db639774850e"));
        jwtInfoToken.setRoles(Set.of(new Role("PATIENT"), new Role("DOCTOR")));
        jwtInfoToken.setAuthenticated(true);

        when(jwtProvider.getTokenFromRequest("Bearer token")).thenReturn("token");
        when(jwtProvider.validateAccessToken("token")).thenReturn(true);
        when(jwtProvider.getAuthentication("token")).thenReturn(jwtInfoToken);

        String appealId = "3";

        mockMvc.perform(get("/api/medical/patient/appeal/report")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("patient_id", "1")
                        .param("appeal_id", appealId)
                        .param("send_email", "false")
                        .header("Authorization", accessToken))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().string(String.format("Appeal with id %s not found", appealId)));
    }

    @Test
    public void isAppealExistAndPatientOwnerNotFound() throws Exception {
        JwtAuthentication jwtInfoToken = new JwtAuthentication();
        jwtInfoToken.setUserId(UUID.fromString("cf29361a-c9ed-4644-a6dc-db639774850e"));
        jwtInfoToken.setRoles(Set.of(new Role("PATIENT"), new Role("DOCTOR")));
        jwtInfoToken.setAuthenticated(true);

        when(jwtProvider.getTokenFromRequest("Bearer token")).thenReturn("token");
        when(jwtProvider.validateAccessToken("token")).thenReturn(true);
        when(jwtProvider.getAuthentication("token")).thenReturn(jwtInfoToken);

        String appealId = "2";

        mockMvc.perform(get("/api/medical/patient/appeal/report")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("patient_id", "1")
                        .param("appeal_id", appealId)
                        .param("send_email", "false")
                        .header("Authorization", accessToken))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().string(String.format("Current user is not owner of appeal with id %s", appealId)));
    }

    @Test
    public void isAppealExistAndPatientTestEmail() throws Exception {
        JwtAuthentication jwtInfoToken = new JwtAuthentication();
        jwtInfoToken.setUserId(UUID.fromString("cf29361a-c9ed-4644-a6dc-db639774850e"));
        jwtInfoToken.setRoles(Set.of(new Role("PATIENT"), new Role("DOCTOR")));
        jwtInfoToken.setAuthenticated(true);

        when(jwtProvider.getTokenFromRequest("Bearer token")).thenReturn("token");
        when(jwtProvider.validateAccessToken("token")).thenReturn(true);
        when(jwtProvider.getAuthentication("token")).thenReturn(jwtInfoToken);
        when(personFeignClient.getPersonContactByUserId(any())).thenReturn("email");

        doNothing().when(reportServiceSender).sendInReportService(any(), any(), any(), any());


        mockMvc.perform(get("/api/medical/patient/appeal/report")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("patient_id", "1")
                        .param("appeal_id", "1")
                        .param("send_email", "true")
                        .header("Authorization", accessToken))
                .andExpect(status().isOk())
                .andExpect(content().string("Отчет отпвлен на почту"));

    }

    @Test
    public void isAppealExistAndPatientTestDownload() throws Exception {
        JwtAuthentication jwtInfoToken = new JwtAuthentication();
        jwtInfoToken.setUserId(UUID.fromString("cf29361a-c9ed-4644-a6dc-db639774850e"));
        jwtInfoToken.setRoles(Set.of(new Role("PATIENT"), new Role("DOCTOR")));
        jwtInfoToken.setAuthenticated(true);

        when(jwtProvider.getTokenFromRequest("Bearer token")).thenReturn("token");
        when(jwtProvider.validateAccessToken("token")).thenReturn(true);
        when(jwtProvider.getAuthentication("token")).thenReturn(jwtInfoToken);

        mockMvc.perform(get("/api/medical/patient/appeal/report")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("patient_id", "1")
                        .param("appeal_id", "1")
                        .param("send_email", "false")
                        .header("Authorization", accessToken))
                .andExpect(status().isSeeOther());

    }
}
