package academy.kata.mis.medicalservice.controller.outer;

import academy.kata.mis.medicalservice.model.dto.GetDiseaseSamplesWithServicesResponse;
import academy.kata.mis.medicalservice.model.entity.DiseaseDep;
import academy.kata.mis.medicalservice.model.entity.Doctor;
import academy.kata.mis.medicalservice.service.DiseaseDepService;
import academy.kata.mis.medicalservice.service.DoctorSamplesBusinessService;
import academy.kata.mis.medicalservice.service.DoctorService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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
        try {
            Doctor doctor = doctorService.findDoctorById(doctorId);
            DiseaseDep diseaseDep = diseaseDepService.findDiseaseDepById(diseaseDepId);
            UUID authUserId = UUID.fromString(principal.getName());
            if (doctor.getUserId().equals(authUserId)) {
                if (diseaseDep.getDepartment().equals(doctor.getDepartment())) {
                    GetDiseaseSamplesWithServicesResponse response =
                            doctorSamplesBusinessService.getDiseaseSamplesWithServicesByDiseaseDep(diseaseDep);
                    return ResponseEntity.ok(response);
                } else return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            } else return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
