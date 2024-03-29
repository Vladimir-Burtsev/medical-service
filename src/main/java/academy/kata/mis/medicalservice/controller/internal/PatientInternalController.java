package academy.kata.mis.medicalservice.controller.internal;

import academy.kata.mis.medicalservice.model.dto.patient.PatientDto;
import academy.kata.mis.medicalservice.service.PatientBusinessService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/internal/medical/patient")
public class PatientInternalController {
    private final PatientBusinessService patientBusinessService;

    @PostMapping
    public ResponseEntity<List<PatientDto>> getCurrentPatientInformation(@RequestParam String userId) {
        String operation = "Получение инфо о пациенте";
        log.info("{}; principal {}", operation, userId);

        List<PatientDto> response = patientBusinessService.findPatientInformationByUserId(UUID.fromString(userId));

        log.debug("{}; Успешно; principal {}", operation, userId);
        return ResponseEntity.ok(response);
    }
}
