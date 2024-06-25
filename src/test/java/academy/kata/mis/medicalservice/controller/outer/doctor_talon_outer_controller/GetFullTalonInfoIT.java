package academy.kata.mis.medicalservice.controller.outer.doctor_talon_outer_controller;

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

    private final String accessToken = "Bearer token";
    private final String userId = "99efc3cd-c5e6-4469-ab40-ff97d3d98882";

    @Test
    public void getFullTalonInfo_success() throws Exception {
        long talonId = 1L;
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
                        .param("talon_id", String.valueOf(talonId))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.talonId", Is.is(1)))
                .andExpect(jsonPath("$.visitTime", Is.is("2024-06-26T13:00:00")))
                .andExpect(jsonPath("$.organization.organizationId", Is.is(1)))
                .andExpect(jsonPath("$.organization.organizationName").doesNotExist())
                .andExpect(jsonPath("$.department.departmentId", Is.is(1)))
                .andExpect(jsonPath("$.department.departmentName").doesNotExist())
                .andExpect(jsonPath("$.cabinetNumber").doesNotExist())
                .andExpect(jsonPath("$.doctor.doctorId", Is.is(1)))
                .andExpect(jsonPath("$.doctor.doctorFirstName").doesNotExist())
                .andExpect(jsonPath("$.doctor.doctorLastName").doesNotExist())
                .andExpect(jsonPath("$.doctor.patronymic").doesNotExist())
                .andExpect(jsonPath("$.doctor.doctorPositionName").doesNotExist())
                .andExpect(jsonPath("$.patient.patientId", Is.is(1)))
                .andReturn();
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
                                .param("talon_id", String.valueOf(talonId))
                )
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().string("Талон не найден!"));
    }

    @Test
    public void getFullTalonInfo_talonNotAssignedToAuthorizedDoctor() throws Exception {
        long talonId = 3L;
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
                                .param("talon_id", String.valueOf(talonId))
                )
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().string("Авторизованный пользователь не является доктором, которому принадлежит талон"));

    }
}
