package academy.kata.mis.medicalservice.controller.outer.doctor_visit_rest_controller;

import academy.kata.mis.medicalservice.ContextIT;
import academy.kata.mis.medicalservice.model.dto.auth.JwtAuthentication;
import academy.kata.mis.medicalservice.model.dto.auth.Role;
import academy.kata.mis.medicalservice.util.JwtProvider;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import java.util.Set;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value =
        "/scripts/controller/outer/doctor_visit_rest_controller/getVisitInfo.sql")
@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value =
        "/scripts/clear.sql")
public class GetVisitInfoTest extends ContextIT {
    @MockBean
    private JwtProvider jwtProvider;
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
    @Test
    public void getVisitInfoIT_success() throws Exception {

        setupJwtTokenMock(user);

        mockMvc.perform(
                        get("/api/medical/doctor/visit/info")
                                .param("visit_id", "1001")
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("Authorization", accessToken)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.visitId", Is.is(1001)))
                .andExpect(jsonPath("$.visitTime", Is.is("2023-07-01T12:00:00")))
                .andExpect(jsonPath("$.doctor.doctorId", Is.is(1001)))
                .andExpect(jsonPath("$.doctor.doctorFirstName", Is.is("FAKEDoctorFirstName")))
                .andExpect(jsonPath("$.doctor.doctorLastName", Is.is("FAKEDoctorLastName")))
                .andExpect(jsonPath("$.doctor.patronymic", Is.is("FAKEPatronymic")))
                .andExpect(jsonPath("$.doctor.doctorPositionName", Is.is("FAKEDoctorPositionName")))
                .andExpect(jsonPath("$.medicalServices[0].medicalServiceDepId", Is.is(1001)))
                .andExpect(jsonPath("$.medicalServices[0].serviceIdentifier", Is.is("ServiceIdentifier")))
                .andExpect(jsonPath("$.medicalServices[0].serviceName", Is.is("ServiceName")));
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
