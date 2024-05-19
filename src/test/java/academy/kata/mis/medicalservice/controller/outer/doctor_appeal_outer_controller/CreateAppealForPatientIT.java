package academy.kata.mis.medicalservice.controller.outer.doctor_appeal_outer_controller;

import academy.kata.mis.medicalservice.ContextIT;
import academy.kata.mis.medicalservice.feign.PersonFeignClient;
import academy.kata.mis.medicalservice.model.dto.CreateAppealForPatientRequest;
import academy.kata.mis.medicalservice.model.dto.GetAppealShortInfo;
import academy.kata.mis.medicalservice.model.dto.GetCurrentPatientInformation;
import academy.kata.mis.medicalservice.model.dto.auth.JwtAuthentication;
import academy.kata.mis.medicalservice.model.dto.auth.Role;
import academy.kata.mis.medicalservice.model.dto.visit.VisitShortDto;
import academy.kata.mis.medicalservice.model.enums.AppealStatus;
import academy.kata.mis.medicalservice.service.AuditMessageService;
import academy.kata.mis.medicalservice.util.JwtProvider;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static academy.kata.mis.medicalservice.model.enums.InsuranceType.DMS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value =
        "/scripts/controller.outer.doctor_appeal_outer_controller/createAppealForPatient.sql")
@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/clear.sql")
public class CreateAppealForPatientIT extends ContextIT {

    @MockBean
    private AuditMessageService auditMessageService;

    @MockBean
    private JwtProvider jwtProvider;

    @MockBean
    private PersonFeignClient personFeignClient;

    private final String accessToken = "Bearer token";

    @Test
    public void createAppealForPatient_success() throws Exception {

        doNothing().when(auditMessageService).sendAudit(anyString(), anyString(), anyString());

        String user = "cf29361a-c9ed-4644-a6dc-db639774860e";
        CreateAppealForPatientRequest request = new CreateAppealForPatientRequest(
                1L, 1L, 1L, DMS);

        JwtAuthentication jwtInfoToken = new JwtAuthentication();
        jwtInfoToken.setUserId(UUID.fromString(user));
        jwtInfoToken.setRoles(Set.of(new Role("DOCTOR")));
        jwtInfoToken.setAuthenticated(true);
        when(jwtProvider.getTokenFromRequest("Bearer token")).thenReturn("token");
        when(jwtProvider.validateAccessToken("token")).thenReturn(true);
        when(jwtProvider.getAuthentication("token")).thenReturn(jwtInfoToken);

        GetCurrentPatientInformation person = new GetCurrentPatientInformation(1,
                "FirstName",
                "LastName",
                "Patronymic",
                LocalDate.now());

        when(personFeignClient.getCurrentPersonById(1L)).thenReturn(person);

        MvcResult result = mockMvc.perform(
                        post(
                                "/api/medical/doctor/appeal/create")
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("Authorization", accessToken)
                                .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.appealStatus", Is.is("OPEN")))
                .andExpect(jsonPath("$.patient.patientId", Is.is(1)))
                .andExpect(jsonPath("$.patient.patientFirstName", Is.is("FirstName")))
                .andExpect(jsonPath("$.patient.patientLastname", Is.is("LastName")))
                .andExpect(jsonPath("$.patient.patientPatronymic", Is.is("Patronymic")))
                .andExpect(jsonPath("$.disease.diseaseDepId", Is.is(1)))
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        System.out.println("Response Content: " + responseContent);
        GetAppealShortInfo response = objectMapper.readValue(responseContent, GetAppealShortInfo.class);

        List<VisitShortDto> visitShortDtoList = response.visits();
        assertEquals(1, visitShortDtoList.size());
        assertEquals(AppealStatus.OPEN, response.appealStatus());

        verify(auditMessageService, times(1)).sendAudit(anyString(), anyString(), anyString());
        verify(personFeignClient, times(1)).getCurrentPersonById(1);
    }

    @Test
    public void createAppealForPatient_noDoctor() throws Exception {
        String user = "cf29361a-c9ed-4644-a6dc-db639774860e";
        CreateAppealForPatientRequest request = new CreateAppealForPatientRequest(4L,
                1L, 1L, DMS);

        JwtAuthentication jwtInfoToken = new JwtAuthentication();
        jwtInfoToken.setUserId(UUID.fromString(user));
        jwtInfoToken.setRoles(Set.of(new Role("DOCTOR")));
        jwtInfoToken.setAuthenticated(true);
        when(jwtProvider.getTokenFromRequest("Bearer token")).thenReturn("token");
        when(jwtProvider.validateAccessToken("token")).thenReturn(true);
        when(jwtProvider.getAuthentication("token")).thenReturn(jwtInfoToken);


        mockMvc.perform(
                        post(
                                "/api/medical/doctor/appeal/create")
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("Authorization", accessToken)
                                .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().string("Доктор не найден"));
    }

    @Test
    public void createAppealForPatient_diseaseDepDoesntExistAndNotMatchesDoctorDepartment() throws Exception {
        String user = "cf29361a-c9ed-4644-a6dc-db639774860e";
        CreateAppealForPatientRequest request = new CreateAppealForPatientRequest(1L,
                1L, 4L, DMS);

        JwtAuthentication jwtInfoToken = new JwtAuthentication();
        jwtInfoToken.setUserId(UUID.fromString(user));
        jwtInfoToken.setRoles(Set.of(new Role("DOCTOR")));
        jwtInfoToken.setAuthenticated(true);
        when(jwtProvider.getTokenFromRequest("Bearer token")).thenReturn("token");
        when(jwtProvider.validateAccessToken("token")).thenReturn(true);
        when(jwtProvider.getAuthentication("token")).thenReturn(jwtInfoToken);

        mockMvc.perform(
                        post(
                                "/api/medical/doctor/appeal/create")
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("Authorization", accessToken)
                                .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().string("Заболевание не существует"));
    }

    @Test
    public void createAppealForPatient_patientDoesntExistsAndNotFromSameOrganizationAsDoctor() throws Exception {
        String user = "cf29361a-c9ed-4644-a6dc-db639774860e";
        CreateAppealForPatientRequest request = new CreateAppealForPatientRequest(1L,
                4L, 1L, DMS);

        JwtAuthentication jwtInfoToken = new JwtAuthentication();
        jwtInfoToken.setUserId(UUID.fromString(user));
        jwtInfoToken.setRoles(Set.of(new Role("DOCTOR")));
        jwtInfoToken.setAuthenticated(true);
        when(jwtProvider.getTokenFromRequest("Bearer token")).thenReturn("token");
        when(jwtProvider.validateAccessToken("token")).thenReturn(true);
        when(jwtProvider.getAuthentication("token")).thenReturn(jwtInfoToken);

        mockMvc.perform(
                        post(
                                "/api/medical/doctor/appeal/create")
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("Authorization", accessToken)
                                .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().string("Пациент не существует или находится с доктором в разных организациях"));
    }
}
