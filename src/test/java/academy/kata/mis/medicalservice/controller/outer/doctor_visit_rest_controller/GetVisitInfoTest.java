package academy.kata.mis.medicalservice.controller.outer.doctor_visit_rest_controller;

import academy.kata.mis.medicalservice.ContextIT;
import academy.kata.mis.medicalservice.feign.PersonFeignClient;
import academy.kata.mis.medicalservice.feign.StructureFeignClient;
import academy.kata.mis.medicalservice.model.dto.auth.JwtAuthentication;
import academy.kata.mis.medicalservice.model.dto.auth.Role;
import academy.kata.mis.medicalservice.model.dto.department_organization_position_cabinet.DepartmentOrganizationPositionCabinetNameDto;
import academy.kata.mis.medicalservice.model.dto.doctor.DoctorShortDto;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value =
        "/scripts/controller/outer/doctor_visit_rest_controller/getVisitInfo.sql")
@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value =
        "/scripts/clear.sql")
public class GetVisitInfoTest extends ContextIT {
    @MockBean
    private JwtProvider jwtProvider;
    @MockBean
    private PersonFeignClient personFeignClient;
    @MockBean
    private StructureFeignClient structureFeignClient;
    private final String accessToken = "Bearer token";
    private final Set<Role> roles =
            Set.of(new Role("DOCTOR"), new Role("CHIEF_DOCTOR"), new Role("DIRECTOR"));
    private final String user = "123e4567-e89b-12d3-a456-426614174000";

    private void setupJwtTokenMock (String userId) throws Exception {
        JwtAuthentication jwtInfoToken = new JwtAuthentication();
        jwtInfoToken.setUserId(UUID.fromString(userId));
        jwtInfoToken.setRoles(roles);
        jwtInfoToken.setAuthenticated(true);

        when(jwtProvider.getTokenFromRequest("Bearer token")).thenReturn("token");
        when(jwtProvider.validateAccessToken("token")).thenReturn(true);
        when(jwtProvider.getAuthentication("token")).thenReturn(jwtInfoToken);
    }
    private void feignClientMocks () {
        when(structureFeignClient.getDepartmentOrganizationPositionCabinetNameDto(3001))
                .thenReturn(new DepartmentOrganizationPositionCabinetNameDto(
                        1001,
                        "terapist",
                        1001,
                        "organization name",
                        "position name",
                        "cabinet num"
                ));
        when(personFeignClient.getDoctorShortDtoByPersonIdAndDoctorId(2001, 1001))
                .thenReturn(new DoctorShortDto(
                        1001,
                        "DoctorFirstName",
                        "DoctorLastName",
                        "DoctorPatronymic",
                        "position name"
                ));
    }
    @Test
    public void getVisitInfoIT_success() throws Exception {
        long visitId = 1001L;
        setupJwtTokenMock(user);
        feignClientMocks();

        mockMvc.perform(
                        get("/api/medical/doctor/visit/info")
                                .param("visit_id", String.valueOf(visitId))
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("Authorization", accessToken)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.visitId", Is.is((int) visitId)))
                .andExpect(jsonPath("$.visitTime", Is.is("2023-07-01T12:00:00")))
                .andExpect(jsonPath("$.doctor.doctorId", Is.is(1001)))
                .andExpect(jsonPath("$.doctor.doctorFirstName", Is.is("DoctorFirstName")))
                .andExpect(jsonPath("$.doctor.doctorLastName", Is.is("DoctorLastName")))
                .andExpect(jsonPath("$.doctor.patronymic", Is.is("DoctorPatronymic")))
                .andExpect(jsonPath("$.doctor.doctorPositionName", Is.is("position name")))
                .andExpect(jsonPath("$.medicalServices[0].medicalServiceDepId", Is.is(1001)))
                .andExpect(jsonPath("$.medicalServices[0].serviceIdentifier", Is.is("ServiceIdentifier")))
                .andExpect(jsonPath("$.medicalServices[0].serviceName", Is.is("ServiceName")));
        verify(structureFeignClient, times(1))
                .getDepartmentOrganizationPositionCabinetNameDto(3001);
        verify(personFeignClient, times(1))
                .getDoctorShortDtoByPersonIdAndDoctorId(2001, 1001);
    }

    @Test
    public void getVisitInfoIT_VisitNotFound() throws Exception {
        setupJwtTokenMock(user);

        mockMvc.perform(
                get("/api/medical/doctor/visit/info")
                .param("visit_id", "1002")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", accessToken)
        )
                .andExpect(status().isNotFound());
    }

    @Test
    public void getVisitInfoIT_departmentsDoNotMatch () throws Exception {
        setupJwtTokenMock(user);

        mockMvc.perform(
                get("/api/medical/doctor/visit/info")
                .param("visit_id", "1004")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", accessToken)
        )
                .andExpect(status().isForbidden());
    }
}
