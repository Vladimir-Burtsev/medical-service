package academy.kata.mis.medicalservice.controller.outer.doctor_outer_rest_controller;

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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/controller/outer/doctor_outer_rest_controller/getCurrentDoctorInformation.sql")
@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/clear.sql")
public class GetCurrentDoctorInformationIT extends ContextIT {


    @MockBean
    private JwtProvider jwtProvider;

    private final String accessToken = "Bearer token";

    @Test
    public void getCurrentDoctorInformation_success() throws Exception {


        //задаем нужное нам поведение при проверке токена
        JwtAuthentication jwtInfoToken = new JwtAuthentication();
        jwtInfoToken.setUserId(UUID.fromString("cf29361a-c9ed-4644-a6dc-db639774850e"));
        jwtInfoToken.setRoles(Set.of(new Role("PATIENT"), new Role("DOCTOR")));
        jwtInfoToken.setAuthenticated(true);
        when(jwtProvider.getTokenFromRequest("Bearer token")).thenReturn("token");
        when(jwtProvider.validateAccessToken("token")).thenReturn(true);
        when(jwtProvider.getAuthentication("token")).thenReturn(jwtInfoToken);

        //запустим тест
        mockMvc.perform(
                        get("/api/medical/doctor")
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("Authorization", accessToken)
                )
//                специально закомментировал строку ниже - помогает при разработке, но не забывайте комментировать а еще лучше - удалять
//               .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()))
                .andExpect(status().isOk())


                .andExpect(jsonPath("$.person.id", Is.is(100)))
                .andExpect(jsonPath("$.person.firstName").doesNotExist())
                .andExpect(jsonPath("$.person.lastName").doesNotExist())

                .andExpect(jsonPath("$.doctors.length()", Is.is(2)))

                .andExpect(jsonPath("$.doctors[0].employeeId", Is.is(501)))
                .andExpect(jsonPath("$.doctors[0].positionName").doesNotExist())

                .andExpect(jsonPath("$.doctors[0].organization.organizationId", Is.is(1001)))
                .andExpect(jsonPath("$.doctors[0].organization.name").doesNotExist())
                .andExpect(jsonPath("$.doctors[0].department.departmentId", Is.is(301)))
                .andExpect(jsonPath("$.doctors[0].department.name").doesNotExist())

                .andExpect(jsonPath("$.doctors[1].employeeId", Is.is(502)))
                .andExpect(jsonPath("$.doctors[1].positionName").doesNotExist())

                .andExpect(jsonPath("$.doctors[1].organization.organizationId", Is.is(1002)))
                .andExpect(jsonPath("$.doctors[1].organization.name").doesNotExist())
                .andExpect(jsonPath("$.doctors[1].department.departmentId", Is.is(302)))
                .andExpect(jsonPath("$.doctors[1].department.name").doesNotExist())
        ;


    }
}
