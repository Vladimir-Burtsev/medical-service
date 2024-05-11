package academy.kata.mis.medicalservice.controller.outer.doctor_samples_outer_controller;

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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/controller/outer/doctor_samples_outer_controller/getDiseaseSamplesWithServices.sql")
@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/clear.sql")
public class GetDiseaseSamplesWithServicesIT extends ContextIT {

    @MockBean
    JwtProvider jwtProvider;

    String accessToken = "Bearer token";

    @Test
    public void GetDiseaseSamplesWithServices_success() throws Exception {

        JwtAuthentication jwtInfoToken = new JwtAuthentication();
        jwtInfoToken.setUserId(UUID.fromString("cf29361a-c9ed-4644-a6dc-db639774850e"));
        jwtInfoToken.setRoles(Set.of(new Role("DOCTOR")));
        jwtInfoToken.setAuthenticated(true);
        when(jwtProvider.getTokenFromRequest("Bearer token")).thenReturn("token");
        when(jwtProvider.validateAccessToken("token")).thenReturn(true);
        when(jwtProvider.getAuthentication("token")).thenReturn(jwtInfoToken);

        mockMvc.perform(
                        get("/api/medical/doctor/sample/info")
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("Authorization", accessToken)
                                .param("doctor_id", "1")
                                .param("disease_dep_id", "1")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.samples.length()", Is.is(2)))
                .andExpect(jsonPath("$.samples[0].medicalServices.length()", Is.is(2)))
                .andExpect(jsonPath("$.samples[0].medicalServices[0].medicalServiceDepId", Is.is(1)))
                .andExpect(jsonPath("$.samples[0].medicalServices[0].serviceIdentifier",
                        Is.is("service_identifier_1")))
                .andExpect(jsonPath("$.samples[0].medicalServices[0].serviceName",
                        Is.is("Service Name 1")))
                .andExpect(jsonPath("$.samples[0].medicalServices[1].medicalServiceDepId", Is.is(2)))
                .andExpect(jsonPath("$.samples[0].medicalServices[1].serviceIdentifier",
                        Is.is("service_identifier_2")))
                .andExpect(jsonPath("$.samples[0].medicalServices[1].serviceName",
                        Is.is("Service Name 2")))
                .andExpect(jsonPath("$.samples[1].medicalServices.length()", Is.is(2)))
                .andExpect(jsonPath("$.samples[1].medicalServices[0].medicalServiceDepId", Is.is(1)))
                .andExpect(jsonPath("$.samples[1].medicalServices[0].serviceIdentifier",
                        Is.is("service_identifier_1")))
                .andExpect(jsonPath("$.samples[1].medicalServices[0].serviceName",
                        Is.is("Service Name 1")))
                .andExpect(jsonPath("$.samples[1].medicalServices[1].medicalServiceDepId", Is.is(2)))
                .andExpect(jsonPath("$.samples[1].medicalServices[1].serviceIdentifier",
                        Is.is("service_identifier_2")))
                .andExpect(jsonPath("$.samples[1].medicalServices[1].serviceName",
                        Is.is("Service Name 2")));
    }

    @Test
    public void GetDiseaseSamplesWithService_forbidden() throws Exception {
        JwtAuthentication jwtInfoToken = new JwtAuthentication();
        jwtInfoToken.setUserId(UUID.fromString("cf29361a-c9ed-4644-a6dc-db639774850e"));
        jwtInfoToken.setRoles(Set.of(new Role("PATIENT")));
        jwtInfoToken.setAuthenticated(true);
        when(jwtProvider.getTokenFromRequest("Bearer token")).thenReturn("token");
        when(jwtProvider.validateAccessToken("token")).thenReturn(true);
        when(jwtProvider.getAuthentication("token")).thenReturn(jwtInfoToken);
        mockMvc.perform(

                        get("/api/medical/doctor/sample/info")

                                .contentType(MediaType.APPLICATION_JSON)

                                .header("Authorization", accessToken)

                                .param("doctor_id", "1")

                                .param("disease_dep_id", "1")

                )

                .andExpect(status().is4xxClientError());
    }

    @Test
    public void GetDiseaseSamplesWithService_notFound() throws Exception {
        JwtAuthentication jwtInfoToken = new JwtAuthentication();
        jwtInfoToken.setUserId(UUID.fromString("cf29361a-c9ed-4644-a6dc-db639774850e"));
        jwtInfoToken.setRoles(Set.of(new Role("DOCTOR")));
        jwtInfoToken.setAuthenticated(true);
        when(jwtProvider.getTokenFromRequest("Bearer token")).thenReturn("token");
        when(jwtProvider.validateAccessToken("token")).thenReturn(true);
        when(jwtProvider.getAuthentication("token")).thenReturn(jwtInfoToken);
        mockMvc.perform(

                        get("/api/medical/doctor/sample/info")

                                .contentType(MediaType.APPLICATION_JSON)

                                .header("Authorization", accessToken)

                                .param("doctor_id", "2")

                                .param("disease_dep_id", "1")

                )

                .andExpect(status().is4xxClientError());
    }

}
