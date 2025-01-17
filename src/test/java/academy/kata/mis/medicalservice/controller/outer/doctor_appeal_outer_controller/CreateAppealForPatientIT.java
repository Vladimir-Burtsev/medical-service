package academy.kata.mis.medicalservice.controller.outer.doctor_appeal_outer_controller;

import academy.kata.mis.medicalservice.ContextIT;
import academy.kata.mis.medicalservice.feign.PersonFeignClient;
import academy.kata.mis.medicalservice.feign.StructureFeignClient;
import academy.kata.mis.medicalservice.model.dto.CreateAppealForPatientRequest;
import academy.kata.mis.medicalservice.model.dto.GetCurrentPatientInformation;
import academy.kata.mis.medicalservice.model.dto.PositionDto;
import academy.kata.mis.medicalservice.model.dto.auth.JwtAuthentication;
import academy.kata.mis.medicalservice.model.dto.auth.Role;
import academy.kata.mis.medicalservice.model.dto.person.PersonFullNameDto;
import academy.kata.mis.medicalservice.model.enums.AppealStatus;
import academy.kata.mis.medicalservice.service.AuditMessageService;
import academy.kata.mis.medicalservice.util.JwtProvider;
import org.hamcrest.Matchers;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

import static academy.kata.mis.medicalservice.model.enums.InsuranceType.DMS;
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

    @MockBean
    private StructureFeignClient structureFeignClient;

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
                LocalDate.of(2001, 1, 21));

        when(personFeignClient.getCurrentPersonById(1L)).thenReturn(person);

        PersonFullNameDto personFullNameDto = new PersonFullNameDto(1L, "DoctorFirstName",
                "DoctorLastName", "Patronymic");
        when(personFeignClient.getPersonFullNameDtoById(1L)).thenReturn(personFullNameDto);

        PositionDto positionDto = new PositionDto(1L, "DoctorPositionName");
        when(structureFeignClient.getPositionNameById(1L)).thenReturn(positionDto);

        mockMvc.perform(
                        post(
                                "/api/medical/doctor/appeal/create")
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("Authorization", accessToken)
                                .content(objectMapper.writeValueAsString(request))
                )
                .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.appealId", Matchers.notNullValue()))
                .andExpect(jsonPath("$.appealStatus", Is.is(AppealStatus.OPEN.name())))
                .andExpect(jsonPath("$.patient.patientId", Is.is(1)))
                .andExpect(jsonPath("$.patient.patientFirstName", Is.is("FirstName")))
                .andExpect(jsonPath("$.patient.patientLastname", Is.is("LastName")))
                .andExpect(jsonPath("$.patient.patientPatronymic", Is.is("Patronymic")))
                .andExpect(jsonPath("$.patient.birthday", Is.is("2001-01-21")))
                .andExpect(jsonPath("$.disease.diseaseDepId", Is.is(1)))
                .andExpect(jsonPath("$.disease.diseaseName", Is.is("Covid-19")))
                .andExpect(jsonPath("$.disease.diseaseIdentifier", Is.is("Covid-19")))
                .andExpect(jsonPath("$.visits.length()", Is.is(1)))
                .andExpect(jsonPath("$.visits[0].visitId", Matchers.notNullValue()))
                .andExpect(jsonPath("$.visits[0].visitTime", Matchers.notNullValue()))
                .andExpect(jsonPath("$.visits[0].doctor.doctorId", Is.is(1)))
                .andExpect(jsonPath("$.visits[0].doctor.doctorFirstName", Is.is("DoctorFirstName")))
                .andExpect(jsonPath("$.visits[0].doctor.doctorLastName", Is.is("DoctorLastName")))
                .andExpect(jsonPath("$.visits[0].doctor.patronymic", Is.is("Patronymic")))
                .andExpect(jsonPath("$.visits[0].doctor.doctorPositionName", Is.is("DoctorPositionName")))
                .andReturn();

        verify(auditMessageService, times(1)).sendAudit(anyString(), anyString(), anyString());
        verify(personFeignClient, times(1)).getCurrentPersonById(1);
        verify(personFeignClient, times(1)).getPersonFullNameDtoById(1);
        verify(structureFeignClient, times(1)).getPositionNameById(1);
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
