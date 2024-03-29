package academy.kata.mis.medicalservice.controller.internal;

import academy.kata.mis.medicalservice.service.PatientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/internal/medical/patient")
public class PatientInternalController {
    private final PatientService patientService;

    @GetMapping
    public ResponseEntity<Boolean> isUserPatientOfOrganization
            (@RequestParam String userId, @RequestParam long organizationId) {
        String operation = "Проверка, является пользователь - пациентом организации";
        log.info("{}; principal {}; organizationId {}", operation, userId, organizationId);

        Boolean response = patientService
                .existsPatientByUserIdAndOrganizationId(UUID.fromString(userId), organizationId);

        log.debug("{}; Успешно; principal {}; organizationId {}; {}", operation, userId, organizationId, response);
        return ResponseEntity.ok(response);
    }
}
