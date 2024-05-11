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

        Doctor doctor = checkDoctorExist(doctorId);
        DiseaseDep diseaseDep = checkDiseaseDepExist(diseaseDepId);

        GetDiseaseSamplesWithServicesResponse response =
                doctorSamplesBusinessService.getDiseaseSamplesWithServicesByDiseaseDep(
                        doctor,
                        diseaseDep,
                        authUserId
                );


        return ResponseEntity.ok(response);
    }

    private Doctor checkDoctorExist(long doctorId) {
        Doctor doctor;
        try {
            doctor = doctorService.findDoctorById(doctorId);
        } catch (LogicException e) {
            log.error("Доктор с id: {}, не найден", doctorId);
            throw e;
        }
        return doctor;
    }

    private DiseaseDep checkDiseaseDepExist(long diseaseDepId) {
        DiseaseDep diseaseDep;
        try {
            diseaseDep = diseaseDepService.findDiseaseDepById(diseaseDepId);
        } catch (LogicException e) {
            log.error("Заболевание с id: {}, не найдено", diseaseDepId);
            throw e;
        }
        return diseaseDep;
    }

}
