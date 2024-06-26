package academy.kata.mis.medicalservice.controller.outer.doctor_outer_rest_controller;

import academy.kata.mis.medicalservice.ContextIT;
import academy.kata.mis.medicalservice.feign.PersonFeignClient;
import academy.kata.mis.medicalservice.feign.StructureFeignClient;
import academy.kata.mis.medicalservice.model.dto.auth.JwtAuthentication;
import academy.kata.mis.medicalservice.model.dto.auth.Role;
import academy.kata.mis.medicalservice.model.dto.feign.PersonDto;
import academy.kata.mis.medicalservice.model.dto.positions.PositionsDepartmentOrganizationDto;
import academy.kata.mis.medicalservice.service.AuditMessageService;
import academy.kata.mis.medicalservice.util.JwtProvider;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import java.util.Set;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/controller/outer/doctor_outer_rest_controller/getCurrentDoctorInformation.sql")
@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/clear.sql")
public class GetCurrentDoctorInformationIT extends ContextIT {


    @MockBean
    private JwtProvider jwtProvider;

    @MockBean
    private AuditMessageService auditMessageService;

    @MockBean
    private StructureFeignClient structureFeignClient;

    @MockBean
    private PersonFeignClient personFeignClient;

    private final String accessToken = "Bearer token";

    @Test
    public void getCurrentDoctorInformation_success() throws Exception {


        //заглушим отправку в аудит
        doNothing().when(auditMessageService).sendAudit(anyString(), anyString(), anyString());

        //задаем нужное нам поведение при проверке токена
        JwtAuthentication jwtInfoToken = new JwtAuthentication();
        jwtInfoToken.setUserId(UUID.fromString("cf29361a-c9ed-4644-a6dc-db639774850e"));
        jwtInfoToken.setRoles(Set.of(new Role("PATIENT"), new Role("DOCTOR"), new Role("DIRECTOR")));
        jwtInfoToken.setAuthenticated(true);
        when(jwtProvider.getTokenFromRequest("Bearer token")).thenReturn("token");
        when(jwtProvider.validateAccessToken("token")).thenReturn(true);
        when(jwtProvider.getAuthentication("token")).thenReturn(jwtInfoToken);

        PersonDto person = new PersonDto(100, "FirstName", "LastName");
        when(personFeignClient.getPersonById(100L)).thenReturn(person);

        //задаем поведения клиента при запросах должности, организации и департамента


//        PositionDto PositionDto602 = new PositionDto(602L, "Position602 mock name");
//        PositionDto PositionDto603 = new PositionDto(603L, "Position603 mock name");
////        when(structureFeignClient.getPositionNameById(601)).thenReturn(PositionDto601);
////        when(structureFeignClient.getPositionNameById(602)).thenReturn(PositionDto602);
////        when(structureFeignClient.getPositionNameById(603)).thenReturn(PositionDto603);
////
//        OrganizationDto organization1001 = new OrganizationDto(1001L, "Organization1001 mock name");
//        OrganizationDto organization1002 = new OrganizationDto(1002L, "Organization1002 mock name");
////        when(structureFeignClient.getOrganizationById(1001)).thenReturn(organization1001);
////        when(structureFeignClient.getOrganizationById(1002)).thenReturn(organization1002);
////
//        DepartmentDto departmentDto301 = new DepartmentDto(301L, "Department301 mock name");
//        DepartmentDto departmentDto302 = new DepartmentDto(302L, "Department302 mock name");
//        DepartmentDto departmentDto401 = new DepartmentDto(401L, "Department401 mock name");
////        when(structureFeignClient.getDepartmentById(301)).thenReturn(departmentDto301);
////        when(structureFeignClient.getDepartmentById(302)).thenReturn(departmentDto302);
////        when(structureFeignClient.getDepartmentById(401)).thenReturn(departmentDto401);

        PositionsDepartmentOrganizationDto positionsDepartmentOrganization601 = new PositionsDepartmentOrganizationDto(601L, "Position601 mock name", 301L, "Department301 mock name", 1001L, "Organization1001 mock name");
        PositionsDepartmentOrganizationDto positionsDepartmentOrganization602 = new PositionsDepartmentOrganizationDto(602L, "Position602 mock name", 302L, "Department302 mock name", 1001L, "Organization1001 mock name");
        PositionsDepartmentOrganizationDto positionsDepartmentOrganization603 = new PositionsDepartmentOrganizationDto(603L, "Position603 mock name", 401L, "Department401 mock name", 1002L, "Organization1002 mock name");

        when(structureFeignClient.getPositionsDepartmentOrganizationByPositionId(601L, 301L, 1001L)).thenReturn(positionsDepartmentOrganization601);
        when(structureFeignClient.getPositionsDepartmentOrganizationByPositionId(602L, 302L, 1001L)).thenReturn(positionsDepartmentOrganization602);
        when(structureFeignClient.getPositionsDepartmentOrganizationByPositionId(603L, 401L, 1002L)).thenReturn(positionsDepartmentOrganization603);

        //запустим тест
        mockMvc.perform(
                        get("/api/medical/doctor")
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("Authorization", accessToken)
                )
//                специально закомментировал строку ниже - помогает при разработке, но не забывайте комментировать а еще лучше - удалять
                .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()))
                .andExpect(status().isOk())


                .andExpect(jsonPath("$.person.id", Is.is(100)))
                .andExpect(jsonPath("$.person.firstName", Is.is("FirstName")))
                .andExpect(jsonPath("$.person.lastName", Is.is("LastName")))

                .andExpect(jsonPath("$.doctors.length()", Is.is(3)))

                .andExpect(jsonPath("$.doctors[0].employeeId", Is.is(501)))
                .andExpect(jsonPath("$.doctors[0].positionName", Is.is("Position601 mock name")))

                .andExpect(jsonPath("$.doctors[0].organization.organizationId", Is.is(1001)))
                .andExpect(jsonPath("$.doctors[0].organization.organizationName", Is.is("Organization1001 mock name")))
                .andExpect(jsonPath("$.doctors[0].department.departmentId", Is.is(301)))
                .andExpect(jsonPath("$.doctors[0].department.departmentName", Is.is("Department301 mock name")))

                .andExpect(jsonPath("$.doctors[1].employeeId", Is.is(502)))
                .andExpect(jsonPath("$.doctors[1].positionName", Is.is("Position602 mock name")))
                .andExpect(jsonPath("$.doctors[1].organization.organizationId", Is.is(1001)))
                .andExpect(jsonPath("$.doctors[1].organization.organizationName", Is.is("Organization1001 mock name")))
                .andExpect(jsonPath("$.doctors[1].department.departmentId", Is.is(302)))
                .andExpect(jsonPath("$.doctors[1].department.departmentName", Is.is("Department302 mock name")))

                .andExpect(jsonPath("$.doctors[2].employeeId", Is.is(503)))
                .andExpect(jsonPath("$.doctors[2].positionName", Is.is("Position603 mock name")))
                .andExpect(jsonPath("$.doctors[2].organization.organizationId", Is.is(1002)))
                .andExpect(jsonPath("$.doctors[2].organization.organizationName", Is.is("Organization1002 mock name")))
                .andExpect(jsonPath("$.doctors[2].department.departmentId", Is.is(401)))
                .andExpect(jsonPath("$.doctors[2].department.departmentName", Is.is("Department401 mock name")))
        ;
        //проверяем что была попытка отправить запрос в аудит сервис
        verify(auditMessageService, times(1)).sendAudit(anyString(), anyString(), anyString());

        //проверяем что была попытка отправить запрос сервис персон
        verify(personFeignClient, times(1)).getPersonById(100);

        //проверяем что была попытка отправить 3 попытки запроса структурного сервиса
        verify(structureFeignClient, times(3)).getPositionsDepartmentOrganizationByPositionId(anyLong(), anyLong(), anyLong());


    }

    @Test
    public void getCurrentDoctorInformation_AuthorizationFail() throws Exception {

        // Мокирование неавторизованного пользователя
        JwtAuthentication jwtInfoToken = new JwtAuthentication();
        jwtInfoToken.setAuthenticated(false);
        when(jwtProvider.getTokenFromRequest("Bearer token")).thenReturn("token");
        when(jwtProvider.validateAccessToken("token")).thenReturn(false);

        // Вызов метода контроллера
        mockMvc.perform(
                        get("/api/medical/doctor")
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("Authorization", "Bearer token")
                )
                .andExpect(status().isUnauthorized());

        // Проверка логирования
        verifyNoInteractions(auditMessageService);
        verifyNoInteractions(personFeignClient);
        verifyNoInteractions(structureFeignClient);


    }


}