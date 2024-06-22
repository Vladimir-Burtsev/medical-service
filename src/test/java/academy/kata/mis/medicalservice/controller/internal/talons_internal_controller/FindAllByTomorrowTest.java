package academy.kata.mis.medicalservice.controller.internal.talons_internal_controller;

import academy.kata.mis.medicalservice.ContextIT;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value =
        "/scripts/controller/internal/talons_internal_controller/findAllByTomorrowTest.sql")
@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/clear.sql")
public class FindAllByTomorrowTest extends ContextIT {

    @Test
    public void success() throws Exception {
        mockMvc.perform(
                get("/internal/medical/talons")
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("talons.length()").value(1))
                .andExpect(jsonPath("talons[0].talonId").value(1))
                .andExpect(jsonPath("talons[0].patient.id").value(1))
                .andExpect(jsonPath("talons[0].patient.personId").value(1))
                .andExpect(jsonPath("talons[0].doctor.id").value(1))
                .andExpect(jsonPath("talons[0].doctor.personId").value(1))
                .andExpect(jsonPath("talons[0].doctor.positionId").value(1))
                .andExpect(jsonPath("talons[0].doctor.positionId").value(1))
                .andExpect(jsonPath("talons[0].doctor.userId")
                        .value("cf29361a-c9ed-4644-a6dc-db639774850e"))
                .andExpect(jsonPath("talons[0].doctor.department.id").value(1))
                .andExpect(jsonPath("talons[0].doctor.department.organizationId").value(1));

    }
}
