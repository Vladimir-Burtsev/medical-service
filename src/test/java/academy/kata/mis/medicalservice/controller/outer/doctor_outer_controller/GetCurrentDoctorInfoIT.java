package academy.kata.mis.medicalservice.controller.outer.doctor_outer_controller;

import academy.kata.mis.medicalservice.ContextIT;
import academy.kata.mis.medicalservice.feign.PersonFeignClient;
import academy.kata.mis.medicalservice.feign.StructureFeignClient;
import academy.kata.mis.medicalservice.model.dto.auth.JwtAuthentication;
import academy.kata.mis.medicalservice.model.dto.auth.Role;
import academy.kata.mis.medicalservice.model.dto.department_organization_position_cabinet.DepartmentOrganizationPositionCabinetNameDto;
import academy.kata.mis.medicalservice.model.dto.doctor.DoctorShortDto;
import academy.kata.mis.medicalservice.util.JwtProvider;
import feign.FeignException;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import java.util.Set;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value =
        "/scripts/controller/outer/doctor_outer_controller/getCurrentDoctorInfo.sql")
@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/clear.sql")
public class GetCurrentDoctorInfoIT extends ContextIT {

    @MockBean
    private JwtProvider jwtProvider;

    @MockBean
    private PersonFeignClient personFeignClient;

    @MockBean
    private StructureFeignClient structureFeignClient;

    private final String accessToken = "Bearer token";
    private final Set<Role> roles =
            Set.of(new Role("DOCTOR"), new Role("CHIEF_DOCTOR"), new Role("DIRECTOR"));

    @Test
    public void GetCurrentDoctorInfo_success() throws Exception {

        String user = "63fcae3f-ae3c-48e8-b073-b91a2af624b5";
        Long doctorId = 1000L;
        Long personId = 1111L;
        JwtAuthentication jwtInfoToken = new JwtAuthentication();
        jwtInfoToken.setUserId(UUID.fromString(user));
        jwtInfoToken.setRoles(roles);
        jwtInfoToken.setAuthenticated(true);
        when(jwtProvider.getTokenFromRequest("Bearer token")).thenReturn("token");
        when(jwtProvider.validateAccessToken("token")).thenReturn(true);
        when(jwtProvider.getAuthentication("token")).thenReturn(jwtInfoToken);

        DepartmentOrganizationPositionCabinetNameDto feignDepartmentOrganizationPositionCabinetNameDto =
                new DepartmentOrganizationPositionCabinetNameDto(
                        10,
                        "department name10",
                        1,
                        "organization name1",
                        "position name",
                        "cabinet number");
        when(structureFeignClient.getDepartmentOrganizationPositionCabinetNameDto(100))
                .thenReturn(feignDepartmentOrganizationPositionCabinetNameDto);

        DoctorShortDto feignDoctorShortDto = new DoctorShortDto(doctorId, "doctorFirstName1111",
                "doctorLastName1111", "doctorPatronymic1111", null);
        when(personFeignClient.getDoctorShortDtoByPersonIdAndDoctorId(personId, doctorId))
                .thenReturn(feignDoctorShortDto);

        mockMvc.perform(
                        get("/api/medical/doctor/current")
                                .param("doctor_id", doctorId.toString())
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("Authorization", accessToken)
                )
//                .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.doctor.doctorId", Is.is(doctorId.intValue())))
                .andExpect(jsonPath("$.doctor.doctorFirstName", Is.is("doctorFirstName1111")))
                .andExpect(jsonPath("$.doctor.doctorLastName", Is.is("doctorLastName1111")))
                .andExpect(jsonPath("$.doctor.patronymic", Is.is("doctorPatronymic1111")))
                .andExpect(jsonPath("$.doctor.doctorPositionName", Is.is("position name")))
                .andExpect(jsonPath("$.organization.organizationId", Is.is(1)))
                .andExpect(jsonPath("$.organization.organizationName", Is.is("organization name1")))
                .andExpect(jsonPath("$.department.departmentId", Is.is(10)))
                .andExpect(jsonPath("$.department.departmentName", Is.is("department name10")))
                .andExpect(jsonPath("$.cabinetNumber", Is.is("cabinet number")))
                .andReturn();

        verify(structureFeignClient, times(1)).getDepartmentOrganizationPositionCabinetNameDto(100);
        verify(personFeignClient, times(1)).getDoctorShortDtoByPersonIdAndDoctorId(personId, doctorId);
    }

    @Test
    public void GetCurrentDoctorInfo_incorrectDoctorId() throws Exception {

        String user = "63fcae3f-ae3c-48e8-b073-b91a2af624b5";
        String answerException = "Доктор не найден!";
        String doctorId = "666";

        JwtAuthentication jwtInfoToken = new JwtAuthentication();
        jwtInfoToken.setUserId(UUID.fromString(user));
        jwtInfoToken.setRoles(roles);
        jwtInfoToken.setAuthenticated(true);
        when(jwtProvider.getTokenFromRequest("Bearer token")).thenReturn("token");
        when(jwtProvider.validateAccessToken("token")).thenReturn(true);
        when(jwtProvider.getAuthentication("token")).thenReturn(jwtInfoToken);

        mockMvc.perform(
                        get("/api/medical/doctor/current")
                                .param("doctor_id", doctorId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("Authorization", accessToken)
                )
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().string(answerException));
    }

    @Test
    public void GetCurrentDoctorInfo_doctorNotAuthorized() throws Exception {

        String user = "63fcae3f-ae3c-48e8-b073-b91a2af624b5";
        String answerException = "Доктор не авторизован!";
        String doctorId = "2000";

        JwtAuthentication jwtInfoToken = new JwtAuthentication();
        jwtInfoToken.setUserId(UUID.fromString(user));
        jwtInfoToken.setRoles(roles);
        jwtInfoToken.setAuthenticated(true);
        when(jwtProvider.getTokenFromRequest("Bearer token")).thenReturn("token");
        when(jwtProvider.validateAccessToken("token")).thenReturn(true);
        when(jwtProvider.getAuthentication("token")).thenReturn(jwtInfoToken);

        mockMvc.perform(
                        get("/api/medical/doctor/current")
                                .param("doctor_id", doctorId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("Authorization", accessToken)
                )
                .andExpect(status().isForbidden())
                .andExpect(content().string(answerException));
    }

    @Test
    public void GetCurrentDoctorInfo_positionNotExist() throws Exception {

        String user = "63fcae3f-ae3c-48e8-b073-b91a2af624b5";
        Long doctorId = 1000L;
        JwtAuthentication jwtInfoToken = new JwtAuthentication();
        jwtInfoToken.setUserId(UUID.fromString(user));
        jwtInfoToken.setRoles(roles);
        jwtInfoToken.setAuthenticated(true);
        when(jwtProvider.getTokenFromRequest("Bearer token")).thenReturn("token");
        when(jwtProvider.validateAccessToken("token")).thenReturn(true);
        when(jwtProvider.getAuthentication("token")).thenReturn(jwtInfoToken);

        when(structureFeignClient.getDepartmentOrganizationPositionCabinetNameDto(anyLong()))
                .thenThrow(FeignException.class);

        mockMvc.perform(
                        get("/api/medical/doctor/current")
                                .param("doctor_id", doctorId.toString())
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("Authorization", accessToken)
                )
                .andExpect(status().isUnprocessableEntity());

        verify(structureFeignClient, times(1)).getDepartmentOrganizationPositionCabinetNameDto(anyLong());
    }

    @Test
    public void GetCurrentDoctorInfo_personNotExist() throws Exception {

        String user = "63fcae3f-ae3c-48e8-b073-b91a2af624b5";
        Long doctorId = 1000L;
        Long personId = 666L;
        JwtAuthentication jwtInfoToken = new JwtAuthentication();
        jwtInfoToken.setUserId(UUID.fromString(user));
        jwtInfoToken.setRoles(roles);
        jwtInfoToken.setAuthenticated(true);
        when(jwtProvider.getTokenFromRequest("Bearer token")).thenReturn("token");
        when(jwtProvider.validateAccessToken("token")).thenReturn(true);
        when(jwtProvider.getAuthentication("token")).thenReturn(jwtInfoToken);

        DepartmentOrganizationPositionCabinetNameDto feignDepartmentOrganizationPositionCabinetNameDto =
                new DepartmentOrganizationPositionCabinetNameDto(
                        10,
                        "department name10",
                        1,
                        "organization name1",
                        "position name",
                        "cabinet number");
        when(structureFeignClient.getDepartmentOrganizationPositionCabinetNameDto(anyLong()))
                .thenReturn(feignDepartmentOrganizationPositionCabinetNameDto);

        when(personFeignClient.getDoctorShortDtoByPersonIdAndDoctorId(personId, doctorId)).thenThrow(FeignException.class);

        mockMvc.perform(
                        get("/api/medical/doctor/current")
                                .param("doctor_id", doctorId.toString())
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("Authorization", accessToken)
                )
                .andExpect(status().isUnprocessableEntity());

        verify(structureFeignClient, times(1)).getDepartmentOrganizationPositionCabinetNameDto(anyLong());
        verify(personFeignClient, times(1)).getDoctorShortDtoByPersonIdAndDoctorId(personId, doctorId);
    }
}
