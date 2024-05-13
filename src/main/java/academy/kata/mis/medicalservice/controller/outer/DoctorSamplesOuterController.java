package academy.kata.mis.medicalservice.controller.outer;

import academy.kata.mis.medicalservice.exceptions.LogicException;
import academy.kata.mis.medicalservice.model.dto.GetDiseaseSamplesWithServicesResponse;
import academy.kata.mis.medicalservice.model.entity.DiseaseDep;
import academy.kata.mis.medicalservice.model.entity.Doctor;
import academy.kata.mis.medicalservice.service.DiseaseDepService;
import academy.kata.mis.medicalservice.service.DoctorSamplesBusinessService;
import academy.kata.mis.medicalservice.service.DoctorService;
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
    private final DoctorService doctorService;
    private final DiseaseDepService diseaseDepService;

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

        checkDoctorExistAndCurrent(doctorId, authUserId);
        checkDiseaseDepExist(doctorId, diseaseDepId);
        checkIsDoctorDepEqualsDiseaseDep(doctorId, diseaseDepId);

        GetDiseaseSamplesWithServicesResponse response =
                doctorSamplesBusinessService.getDiseaseSamplesWithServicesByDiseaseDep(doctorId, diseaseDepId);

        log.debug("");
        return ResponseEntity.ok(response);
    }

    private void checkDoctorExistAndCurrent(long doctorId, UUID userId) {
        //todo перенеси в бизнес сервис
        // проверить что действительно существует доктор с таким ид и таким юсер ид
        if (!doctorService.isExist(doctorId, userId)) {
            log.error(String.format("Доктор с id: %s, не найден", doctorId));
            throw new LogicException("");
        }
    }

    private void checkDiseaseDepExist(long doctorId, long diseaseDepId) {
        //todo перенеси в бизнес сервис
        if (!diseaseDepService.isExistAndSimpleDepartment(diseaseDepId, doctorId)) {
            log.error("");
            throw new LogicException("");
        }
    }

    private void checkIsDoctorDepEqualsDiseaseDep(long doctorId, long diseaseDepId) {
        if (false) {
            //todo поймет ли пользователь ответ?
            log.error("");
            throw new LogicException("Неверные данные");
        }
    }

}
