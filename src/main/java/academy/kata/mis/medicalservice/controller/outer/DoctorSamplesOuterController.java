package academy.kata.mis.medicalservice.controller.outer;

import academy.kata.mis.medicalservice.exceptions.LogicException;
import academy.kata.mis.medicalservice.model.dto.GetDiseaseSamplesWithServicesResponse;
import academy.kata.mis.medicalservice.service.DiseaseDepBusinessService;
import academy.kata.mis.medicalservice.service.DoctorBusinessService;
import academy.kata.mis.medicalservice.service.DoctorSamplesBusinessService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.UUID;

@Slf4j
@PreAuthorize("hasAnyAuthority('DOCTOR', 'CHIEF_DOCTOR', 'DIRECTOR')")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/medical/doctor/sample")
public class DoctorSamplesOuterController {

    private final DoctorSamplesBusinessService doctorSamplesBusinessService;
    private final DiseaseDepBusinessService diseaseDepBusinessService;
    private final DoctorBusinessService doctorBusinessService;

    /**
     * страница 3.2.4
     */
    @GetMapping("/info")
    public ResponseEntity<GetDiseaseSamplesWithServicesResponse> getSamples(
            Principal principal,
            @RequestParam(name = "doctor_id") long doctorId,
            @RequestParam(name = "disease_dep_id") long diseaseDepId) {
        String operation = "Врач получает свои шаблоны для заболевания";
        UUID authUserId = UUID.fromString(principal.getName());
        log.info("Пользователь: {}; {}; Заболевание: {}", authUserId, operation, diseaseDepId);

        if (!doctorBusinessService.existDoctorByUserIdAndDoctorId(authUserId, doctorId)) {
            log.error(String.format("Доктор с id=%s не найден",
                    doctorId));
            throw new LogicException("Авторизованный пользователь не является доктором");
        }
        if (!diseaseDepBusinessService.checkIsExistByIdAndDoctorId(diseaseDepId, doctorId)) {
            log.error(String.format("Заболевание с id=%s, не найдено", diseaseDepId));
            throw new LogicException("Заболевание не найдено");
        }

        GetDiseaseSamplesWithServicesResponse response =
                doctorSamplesBusinessService.getDiseaseSamplesWithServicesByDiseaseDepAndDoctor(doctorId, diseaseDepId);

        log.debug("{}; Успешно; principal {}", operation, authUserId);
        return ResponseEntity.ok(response);
    }

}
