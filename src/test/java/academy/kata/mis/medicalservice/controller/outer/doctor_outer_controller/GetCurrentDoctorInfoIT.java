//novikov
package academy.kata.mis.medicalservice.controller.outer.doctor_outer_controller;

import academy.kata.mis.medicalservice.ContextIT;
import academy.kata.mis.medicalservice.model.dto.auth.JwtAuthentication;
import academy.kata.mis.medicalservice.model.dto.auth.Role;
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

@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value =
        "/scripts/controller/outer/doctor_outer_controller/getCurrentDoctorInfo.sql")
@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/clear.sql")
public class GetCurrentDoctorInfoIT extends ContextIT {

    @MockBean
    private AuditMessageService auditMessageService;

    @MockBean
    private JwtProvider jwtProvider;


    private final String accessToken = "Bearer token";

    @Test
    public void GetCurrentDoctorInfo_success() throws Exception{

        doNothing().when(auditMessageService).sendAudit(anyString(), anyString(), anyString());

        String user = "63fcae3f-ae3c-48e8-b073-b91a2af624b5";

        JwtAuthentication jwtInfoToken = new JwtAuthentication();
        jwtInfoToken.setUserId(UUID.fromString(user));
        jwtInfoToken.setRoles(Set.of(new Role("DOCTOR"), new Role("DIRECTOR")));
        jwtInfoToken.setAuthenticated(true);
        when(jwtProvider.getTokenFromRequest("Bearer token")).thenReturn("token");
        when(jwtProvider.validateAccessToken("token")).thenReturn(true);
        when(jwtProvider.getAuthentication("token")).thenReturn(jwtInfoToken);

        mockMvc.perform(
                get("/api/medical/doctor/current")
                        .param("doctor_id", "1000")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", accessToken)
        )
               .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.doctor.doctorId", Is.is(1000)))
                .andExpect(jsonPath("$.doctor.doctorFirstName").doesNotExist())
                .andExpect(jsonPath("$.doctor.doctorLastName").doesNotExist())
                .andExpect(jsonPath("$.doctor.patronymic").doesNotExist())
                .andExpect(jsonPath("$.doctor.doctorPositionName").doesNotExist())
                .andExpect(jsonPath("$.organization.organizationId", Is.is(1)))
                .andExpect(jsonPath("$.organization.organizationName").doesNotExist())
                .andExpect(jsonPath("$.department.departmentId", Is.is(10)))
                .andExpect(jsonPath("$.department.departmentName").doesNotExist())
                .andExpect(jsonPath("$.cabinetNumber").doesNotExist())
                .andReturn();
    }
}
