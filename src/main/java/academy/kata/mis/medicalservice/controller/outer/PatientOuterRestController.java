package academy.kata.mis.medicalservice.controller.outer;

import academy.kata.mis.medicalservice.dto.GetCurrentPatientPersonalInformation;
import academy.kata.mis.medicalservice.service.PatientBusinessService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.UUID;

@Slf4j
@PreAuthorize("hasAuthority('PATIENT')")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/medical")
public class PatientOuterRestController {
    private final PatientBusinessService patientBusinessService;

    @GetMapping("/patient")
    public ResponseEntity<GetCurrentPatientPersonalInformation> getCurrentPatientInformation(Principal principal) {
        String operation = "Получение инфо о пациенте";
        log.info("{}; principal {}", operation, principal);

        GetCurrentPatientPersonalInformation response =
                patientBusinessService.getPatientPersonalInformationByUser(UUID.fromString(principal.getName()));

        log.debug("{}; Успешно; principal {}", operation, principal);
        return ResponseEntity.ok(response);
    }


}
