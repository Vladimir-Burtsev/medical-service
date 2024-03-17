package academy.kata.mis.medicalservice.controller.outer.patient_outer_rest_controller;

import academy.kata.mis.medicalservice.ContextIT;
import academy.kata.mis.medicalservice.feign.StructureFeignClient;
import academy.kata.mis.medicalservice.feign.PersonFeignClient;
import academy.kata.mis.medicalservice.model.dto.auth.JwtAuthentication;
import academy.kata.mis.medicalservice.model.dto.auth.Role;
import academy.kata.mis.medicalservice.model.dto.feign.OrganizationDto;
import academy.kata.mis.medicalservice.model.dto.feign.PersonDto;
import academy.kata.mis.medicalservice.service.AuditMessageService;
import academy.kata.mis.medicalservice.util.JwtProvider;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import java.util.Set;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/controller/outer/patient_outer_rest_controller/getCurrentPatientInformation.sql")
@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/clear.sql")
public class GetCurrentPatientInformationIT extends ContextIT {

    @MockBean
    private AuditMessageService auditMessageService;

    @MockBean
    private JwtProvider jwtProvider;

    @MockBean
    private StructureFeignClient structureFeignClient;

    @MockBean
    private PersonFeignClient personFeignClient;

    private String accessToken = "Bearer token";

    @Test
    public void GetCurrentPatientInformation_success() throws Exception {

        //заглушим отправку в аудит
        doNothing().when(auditMessageService).sendAudit(anyString(), anyString(), anyString());

        //задаем нужное нам поведение при проверке токена
        JwtAuthentication jwtInfoToken = new JwtAuthentication();
        jwtInfoToken.setUserId(UUID.fromString("cf29361a-c9ed-4644-a6dc-db639774850e"));
        jwtInfoToken.setRoles(Set.of(new Role("PATIENT"), new Role("DOCTOR")));
        jwtInfoToken.setAuthenticated(true);
        when(jwtProvider.getTokenFromRequest("Bearer token")).thenReturn("token");
        when(jwtProvider.validateAccessToken("token")).thenReturn(true);
        when(jwtProvider.getAuthentication("token")).thenReturn(jwtInfoToken);

        //задаем поведения клиента при запросе персоны
        PersonDto person = new PersonDto(1, "FirstName", "LastName");
        when(personFeignClient.getPersonById(1L)).thenReturn(person);

        //задаем поведения клиента при запросе организации
        OrganizationDto organization1 = new OrganizationDto(1L, "Organization1 mock name");
        OrganizationDto organization2 = new OrganizationDto(2L, "Organization2 mock name");
        when(structureFeignClient.getOrganizationById(1)).thenReturn(organization1);
        when(structureFeignClient.getOrganizationById(2)).thenReturn(organization2);

        //запустим тест
        mockMvc.perform(
                        get("/api/medical/patient")
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("Authorization", accessToken)
                )
//                специально закомментировал строку ниже - помогает при разработке, но не забывайте комментировать а еще лучше - удалять
//                .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId", Is.is("cf29361a-c9ed-4644-a6dc-db639774850e")))

                .andExpect(jsonPath("$.person.id", Is.is(1)))
                .andExpect(jsonPath("$.person.firstName", Is.is("FirstName")))
                .andExpect(jsonPath("$.person.lastName", Is.is("LastName")))

                .andExpect(jsonPath("$.patients.length()", Is.is(2)))
                .andExpect(jsonPath("$.patients[0].patientId", Is.is(1)))
                .andExpect(jsonPath("$.patients[0].organization.id", Is.is(1)))
                .andExpect(jsonPath("$.patients[0].organization.name", Is.is("Organization1 mock name")))
                .andExpect(jsonPath("$.patients[1].patientId", Is.is(2)))
                .andExpect(jsonPath("$.patients[1].organization.id", Is.is(2)))
                .andExpect(jsonPath("$.patients[1].organization.name", Is.is("Organization2 mock name")));

        //проверяем что была попытка отправить запрос в аудит сервис
        verify(auditMessageService, times(1)).sendAudit(anyString(), anyString(), anyString());

        //проверяем что была попытка отправить запрос сервис персон
        verify(personFeignClient, times(1)).getPersonById(1);

        //проверяем что была попытка отправить 2 запроса в сервис структуры
        verify(structureFeignClient, times(2)).getOrganizationById(anyLong());
    }
}
