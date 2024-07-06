package academy.kata.mis.medicalservice.controller.outer.doctor_talon_outer_controller;

import academy.kata.mis.medicalservice.ContextIT;
import academy.kata.mis.medicalservice.feign.PersonFeignClient;
import academy.kata.mis.medicalservice.feign.StructureFeignClient;
import academy.kata.mis.medicalservice.model.dto.auth.JwtAuthentication;
import academy.kata.mis.medicalservice.model.dto.auth.Role;
import academy.kata.mis.medicalservice.model.dto.department_organization_position_cabinet.DepartmentOrganizationPositionCabinetNameDto;
import academy.kata.mis.medicalservice.model.dto.person.PersonFullNameBirthdayDto;
import academy.kata.mis.medicalservice.model.dto.person.PersonsListDto;
import academy.kata.mis.medicalservice.util.JwtProvider;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
        value = "/scripts/controller/outer/doctor_talon_outer_controller/getFullTalonInfo.sql")
@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
        value = "/scripts/clear.sql")
public class GetFullTalonInfoIT extends ContextIT {

    @MockBean
    private JwtProvider jwtProvider;

    @MockBean
    private PersonFeignClient personFeignClient;

    @MockBean
    private StructureFeignClient structureFeignClient;

    private final String accessToken = "Bearer token";
    private final String userId = "99efc3cd-c5e6-4469-ab40-ff97d3d98882";

    @Test
    public void getFullTalonInfo_success() throws Exception {
        Long talonId = 500L;

        JwtAuthentication jwtInfoToken = new JwtAuthentication();
        jwtInfoToken.setUserId(UUID.fromString(userId));
        jwtInfoToken.setRoles(Set.of(new Role("DOCTOR")));
        jwtInfoToken.setAuthenticated(true);
        when(jwtProvider.getTokenFromRequest(accessToken)).thenReturn("token");
        when(jwtProvider.validateAccessToken("token")).thenReturn(true);
        when(jwtProvider.getAuthentication("token")).thenReturn(jwtInfoToken);

        when(structureFeignClient.getDepartmentOrganizationPositionCabinetNameDto(700L))
                .thenReturn(
                        new DepartmentOrganizationPositionCabinetNameDto(
                                200L,
                                "department name1",
                                100L,
                                "organization name1",
                                "position name1",
                                "cabinet name6"
                        )
                );

        when(personFeignClient.getPersonsListByIds(Set.of(602L, 600L)))
                .thenReturn(
                        new PersonsListDto(
                                List.of(
                                        new PersonFullNameBirthdayDto(
                                                602L,
                                                "doctor firstName",
                                                "doctor lastName",
                                                "doctor patronymic",
                                                LocalDate.parse("1959-11-20")
                                        ),
                                        new PersonFullNameBirthdayDto(
                                                600L,
                                                "patient firstName",
                                                "patient lastName",
                                                "patient patronymic",
                                                LocalDate.parse("1989-06-28")
                                        )
                                )
                        )
                );

        mockMvc.perform(
                get("/api/medical/doctor/talon/full/info")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", accessToken)
                        .param("talon_id", Long.toString(talonId))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.talonId", Is.is(talonId.intValue())))
                .andExpect(jsonPath("$.visitTime", Is.is("2024-06-26T13:00:00")))
                .andExpect(jsonPath("$.organization.organizationId", Is.is(100)))
                .andExpect(jsonPath("$.organization.organizationName", Is.is("organization name1")))
                .andExpect(jsonPath("$.department.departmentId", Is.is(200)))
                .andExpect(jsonPath("$.department.departmentName", Is.is("department name1")))
                .andExpect(jsonPath("$.cabinetNumber", Is.is("cabinet name6")))
                .andExpect(jsonPath("$.doctor.doctorId", Is.is(400)))
                .andExpect(jsonPath("$.doctor.doctorFirstName", Is.is("doctor firstName")))
                .andExpect(jsonPath("$.doctor.doctorLastName", Is.is("doctor lastName")))
                .andExpect(jsonPath("$.doctor.patronymic", Is.is("doctor patronymic")))
                .andExpect(jsonPath("$.doctor.doctorPositionName", Is.is("position name1")))
                .andExpect(jsonPath("$.patient.patientId", Is.is(300)))
                .andExpect(jsonPath("$.patient.patientFirstName", Is.is("patient firstName")))
                .andExpect(jsonPath("$.patient.patientLastname", Is.is("patient lastName")))
                .andExpect(jsonPath("$.patient.patientPatronymic", Is.is("patient patronymic")))
                .andExpect(jsonPath("$.patient.birthday", Is.is("1989-06-28")))
                .andReturn();

        verify(structureFeignClient, times(1)).getDepartmentOrganizationPositionCabinetNameDto(700L);
        verify(personFeignClient, times(1)).getPersonsListByIds(Set.of(602L, 600L));
    }

    @Test
    public void getFullTalonInfo_talonNotFound() throws Exception {
        long talonId = 4L;
        JwtAuthentication jwtInfoToken = new JwtAuthentication();
        jwtInfoToken.setUserId(UUID.fromString(userId));
        jwtInfoToken.setRoles(Set.of(new Role("DOCTOR")));
        jwtInfoToken.setAuthenticated(true);
        when(jwtProvider.getTokenFromRequest(accessToken)).thenReturn("token");
        when(jwtProvider.validateAccessToken("token")).thenReturn(true);
        when(jwtProvider.getAuthentication("token")).thenReturn(jwtInfoToken);

        mockMvc.perform(
                        get("/api/medical/doctor/talon/full/info")
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("Authorization", accessToken)
                                .param("talon_id", Long.toString(talonId))
                )
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().string("Талон не найден!"));
    }

    @Test
    public void getFullTalonInfo_talonNotAssignedToAuthorizedDoctor() throws Exception {
        long talonId = 502L;
        JwtAuthentication jwtInfoToken = new JwtAuthentication();
        jwtInfoToken.setUserId(UUID.fromString(userId));
        jwtInfoToken.setRoles(Set.of(new Role("DOCTOR")));
        jwtInfoToken.setAuthenticated(true);
        when(jwtProvider.getTokenFromRequest(accessToken)).thenReturn("token");
        when(jwtProvider.validateAccessToken("token")).thenReturn(true);
        when(jwtProvider.getAuthentication("token")).thenReturn(jwtInfoToken);

        mockMvc.perform(
                        get("/api/medical/doctor/talon/full/info")
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("Authorization", accessToken)
                                .param("talon_id", Long.toString(talonId))
                )
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().string("Авторизованный пользователь не является доктором, которому принадлежит талон"));

    }
}
