package academy.kata.mis.medicalservice.controller.outer.doctor_disease_outer_controller;

import academy.kata.mis.medicalservice.ContextIT;
import academy.kata.mis.medicalservice.model.dto.auth.JwtAuthentication;
import academy.kata.mis.medicalservice.model.dto.auth.Role;
import academy.kata.mis.medicalservice.util.JwtProvider;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Set;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql(executionPhase = Sql.ExecutionPhase
        .BEFORE_TEST_METHOD, value = "/scripts/controller/outer/doctor_disease_outer_controller/getSortOpenDiseaseByDoctorId.sql")
@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/clear.sql")
public class GetSortOpenDiseaseByDoctorIdTest extends ContextIT {
    @MockBean
    private JwtProvider jwtProvider;
    private final String accessToken = "Bearer token";
    private final String userId = "cf29361a-c9ed-4644-a6dc-db639774850e";

    //Проверяем отработку метода getSortOpenDiseaseByDoctorId, если не корректно даны doctorId и userId
    @Test
    public void getSortOpenDiseaseByDoctorId_LogicException() throws Exception {
        long doctorId = 2L;

        //задаем нужное нам поведение при проверке токена
        JwtAuthentication jwtInfoToken = new JwtAuthentication();
        jwtInfoToken.setUserId(UUID.fromString(userId));
        jwtInfoToken.setRoles(Set.of(new Role("DOCTOR")));
        jwtInfoToken.setAuthenticated(true);
        when(jwtProvider.getTokenFromRequest("Bearer token")).thenReturn("token");
        when(jwtProvider.validateAccessToken("token")).thenReturn(true);
        when(jwtProvider.getAuthentication("token")).thenReturn(jwtInfoToken);

        mockMvc.perform(
                        get("/api/medical/doctor/disease/find")
                                .param("doctor_id", String.valueOf(doctorId))
                                .header("Authorization", accessToken)
                )
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().string("Доктор не найден!"));
    }

    //Cортировка: identifier,IDENTIFIER_ASC. Пагинация: page - 2, size - 3.
    @Test
    public void getSortOpenDiseaseByDoctorId_identifier_IDENTIFIER_ASC() throws Exception {
        long doctorId = 1L;
        String identifier = "B";
        int page = 2;
        int size = 3;

        //задаем нужное нам поведение при проверке токена
        JwtAuthentication jwtInfoToken = new JwtAuthentication();
        jwtInfoToken.setUserId(UUID.fromString(userId));
        jwtInfoToken.setRoles(Set.of(new Role("DOCTOR")));
        jwtInfoToken.setAuthenticated(true);
        when(jwtProvider.getTokenFromRequest("Bearer token")).thenReturn("token");
        when(jwtProvider.validateAccessToken("token")).thenReturn(true);
        when(jwtProvider.getAuthentication("token")).thenReturn(jwtInfoToken);

        mockMvc.perform(
                        get("/api/medical/doctor/disease/find")
                                .param("doctor_id", String.valueOf(doctorId))
                                .param("identifier", identifier)
                                .param("page", String.valueOf(page))
                                .param("size", String.valueOf(size))
                                .header("Authorization", accessToken)
                )
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("diseases.length()").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("diseases[0].diseaseDepId").value(14))
                .andExpect(MockMvcResultMatchers.jsonPath("diseases[0].diseaseName").value("Bsc_disease4"))
                .andExpect(MockMvcResultMatchers.jsonPath("diseases[0].diseaseIdentifier").value("B4"))
                .andExpect(MockMvcResultMatchers.jsonPath("diseases[1].diseaseDepId").value(16))
                .andExpect(MockMvcResultMatchers.jsonPath("diseases[1].diseaseName").value("Bsc_disease6"))
                .andExpect(MockMvcResultMatchers.jsonPath("diseases[1].diseaseIdentifier").value("B6"))
        ;
    }

    //сортировка: diseaseName, IDENTIFIER_DESC. Пагинация: page - 1, size - 3.
    @Test
    public void getSortOpenDiseaseByDoctorId_diseaseName_IDENTIFIER_DESC() throws Exception {
        long doctorId = 1L;
        String diseaseName = "Bsc";
        String order = "IDENTIFIER_DESC";
        int page = 1;
        int size = 3;

        //задаем нужное нам поведение при проверке токена
        JwtAuthentication jwtInfoToken = new JwtAuthentication();
        jwtInfoToken.setUserId(UUID.fromString(userId));
        jwtInfoToken.setRoles(Set.of(new Role("DOCTOR")));
        jwtInfoToken.setAuthenticated(true);
        when(jwtProvider.getTokenFromRequest("Bearer token")).thenReturn("token");
        when(jwtProvider.validateAccessToken("token")).thenReturn(true);
        when(jwtProvider.getAuthentication("token")).thenReturn(jwtInfoToken);

        mockMvc.perform(
                        get("/api/medical/doctor/disease/find")
                                .param("doctor_id", String.valueOf(doctorId))
                                .param("disease_name", diseaseName)
                                .param("page", String.valueOf(page))
                                .param("size", String.valueOf(size))
                                .param("order", order)
                                .header("Authorization", accessToken)
                )
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("diseases.length()").value(3))
                .andExpect(MockMvcResultMatchers.jsonPath("diseases[0].diseaseDepId").value(16))
                .andExpect(MockMvcResultMatchers.jsonPath("diseases[0].diseaseName").value("Bsc_disease6"))
                .andExpect(MockMvcResultMatchers.jsonPath("diseases[0].diseaseIdentifier").value("B6"))
                .andExpect(MockMvcResultMatchers.jsonPath("diseases[1].diseaseDepId").value(14))
                .andExpect(MockMvcResultMatchers.jsonPath("diseases[1].diseaseName").value("Bsc_disease4"))
                .andExpect(MockMvcResultMatchers.jsonPath("diseases[1].diseaseIdentifier").value("B4"))
                .andExpect(MockMvcResultMatchers.jsonPath("diseases[2].diseaseDepId").value(13))
                .andExpect(MockMvcResultMatchers.jsonPath("diseases[2].diseaseName").value("Bsc_disease3"))
                .andExpect(MockMvcResultMatchers.jsonPath("diseases[2].diseaseIdentifier").value("B3"))
        ;
    }

    //сортировка: diseaseName, identifier, NAME_ASC. Пагинация: page - 3, size - 2.
    @Test
    public void getSortOpenDiseaseByDoctorId_diseaseName_identifier_NAME_ASC() throws Exception {
        long doctorId = 1L;
        String diseaseName = "Asc_disease5";
        String identifier = "A1.5";
        String order = "NAME_ASC";
        int page = 3;
        int size = 2;

        //задаем нужное нам поведение при проверке токена
        JwtAuthentication jwtInfoToken = new JwtAuthentication();
        jwtInfoToken.setUserId(UUID.fromString(userId));
        jwtInfoToken.setRoles(Set.of(new Role("DOCTOR")));
        jwtInfoToken.setAuthenticated(true);
        when(jwtProvider.getTokenFromRequest("Bearer token")).thenReturn("token");
        when(jwtProvider.validateAccessToken("token")).thenReturn(true);
        when(jwtProvider.getAuthentication("token")).thenReturn(jwtInfoToken);

        mockMvc.perform(
                        get("/api/medical/doctor/disease/find")
                                .param("doctor_id", String.valueOf(doctorId))
                                .param("disease_name", diseaseName)
                                .param("identifier", identifier)
                                .param("page", String.valueOf(page))
                                .param("size", String.valueOf(size))
                                .param("order", order)
                                .header("Authorization", accessToken)
                )
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("diseases.length()").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("diseases[0].diseaseDepId").value(9))
                .andExpect(MockMvcResultMatchers.jsonPath("diseases[0].diseaseName").value("Asc_disease5.4"))
                .andExpect(MockMvcResultMatchers.jsonPath("diseases[0].diseaseIdentifier").value("A1.5.4"))
                .andExpect(MockMvcResultMatchers.jsonPath("diseases[1].diseaseDepId").value(10))
                .andExpect(MockMvcResultMatchers.jsonPath("diseases[1].diseaseName").value("Asc_disease5.5"))
                .andExpect(MockMvcResultMatchers.jsonPath("diseases[1].diseaseIdentifier").value("A1.5.5"))
        ;
    }

    //сортировка: diseaseName, identifier, NAME_ASC. Пагинация: page - 2, size - 2.
    @Test
    public void getSortOpenDiseaseByDoctorId_diseaseName_identifier_NAME_DESC() throws Exception {
        long doctorId = 1L;
        String diseaseName = "Asc_disease5";
        String identifier = "A1.5";
        String order = "NAME_DESC";
        int page = 2;
        int size = 2;

        //задаем нужное нам поведение при проверке токена
        JwtAuthentication jwtInfoToken = new JwtAuthentication();
        jwtInfoToken.setUserId(UUID.fromString(userId));
        jwtInfoToken.setRoles(Set.of(new Role("DOCTOR")));
        jwtInfoToken.setAuthenticated(true);
        when(jwtProvider.getTokenFromRequest("Bearer token")).thenReturn("token");
        when(jwtProvider.validateAccessToken("token")).thenReturn(true);
        when(jwtProvider.getAuthentication("token")).thenReturn(jwtInfoToken);

        mockMvc.perform(
                        get("/api/medical/doctor/disease/find")
                                .param("doctor_id", String.valueOf(doctorId))
                                .param("disease_name", diseaseName)
                                .param("identifier", identifier)
                                .param("page", String.valueOf(page))
                                .param("size", String.valueOf(size))
                                .param("order", order)
                                .header("Authorization", accessToken)
                )
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("diseases.length()").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("diseases[0].diseaseDepId").value(8))
                .andExpect(MockMvcResultMatchers.jsonPath("diseases[0].diseaseName").value("Asc_disease5.3"))
                .andExpect(MockMvcResultMatchers.jsonPath("diseases[0].diseaseIdentifier").value("A1.5.3"))
                .andExpect(MockMvcResultMatchers.jsonPath("diseases[1].diseaseDepId").value(7))
                .andExpect(MockMvcResultMatchers.jsonPath("diseases[1].diseaseName").value("Asc_disease5.2"))
                .andExpect(MockMvcResultMatchers.jsonPath("diseases[1].diseaseIdentifier").value("A1.5.2"))
        ;
    }
}