package academy.kata.mis.medicalservice.controller.outer;

import academy.kata.mis.medicalservice.model.dto.GetDiseaseSamplesWithServicesResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@PreAuthorize("hasAnyAuthority('DOCTOR', 'CHIEF_DOCTOR', 'DIRECTOR')")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/medical/doctor/sample")
public class DoctorSamplesOuterController {

    /**
     * страница 3.2.4
     */
    @GetMapping("/info")
    public ResponseEntity<GetDiseaseSamplesWithServicesResponse> getSamples(
            @RequestParam(name = "doctor_id") long doctorId,
            @RequestParam(name = "disease_dep_id") long diseaseDepId) {
        // проверить что доктор существует и авторизованный пользователь это он
        // проверить что заболевание существует и они с доктором в одном отделении
        // вернуть шаблоны с услугами

        return ResponseEntity.ok(null);
    }
}
