package academy.kata.mis.medicalservice.controller.outer;

import academy.kata.mis.medicalservice.model.dto.GetCurrentDoctorPersonalInfoResponse;
import academy.kata.mis.medicalservice.model.dto.GetCurrentPatientPersonalInfoResponse;
import academy.kata.mis.medicalservice.model.dto.GetDoctorPersonalInfoResponse;
import academy.kata.mis.medicalservice.model.entity.Doctor;
import academy.kata.mis.medicalservice.service.AuditMessageService;
import academy.kata.mis.medicalservice.service.DoctorBusinessService;
import academy.kata.mis.medicalservice.service.PatientBusinessService;
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
@RequestMapping("/api/medical/doctor")
public class DoctorOuterController {

    private final DoctorBusinessService doctorBusinessService;

    private final AuditMessageService auditMessageService;
    private final UUID doctorUUID = UUID.fromString(log.getName());

    @GetMapping
    public ResponseEntity<GetDoctorPersonalInfoResponse> getCurrentDoctorInformation(Principal principal) {
        // вернуть всех докторов которыми является авторизованный пользователь


        return ResponseEntity.ok(null);
    }




    @GetMapping("/current")
    public ResponseEntity<GetCurrentDoctorPersonalInfoResponse> getCurrentDoctorInfo(
            @RequestParam(name = "doctor_id") long doctorId) {
        // проверить что доктор существует
        // проверить что текущий авторизованный доктор соответствует переданному доктору



        Doctor doctor = doctorBusinessService.getDoctorIfExists(doctorUUID, doctorId);



        return ResponseEntity.ok(null);
    }
}
