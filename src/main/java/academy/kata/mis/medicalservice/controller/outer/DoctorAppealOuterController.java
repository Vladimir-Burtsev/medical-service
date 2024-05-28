package academy.kata.mis.medicalservice.controller.outer;

import academy.kata.mis.medicalservice.exceptions.LogicException;
import academy.kata.mis.medicalservice.model.dto.CreateAppealForPatientRequest;
import academy.kata.mis.medicalservice.model.dto.GetAppealShortInfo;
import academy.kata.mis.medicalservice.model.dto.GetPatientAppealShortInfoResponse;
import academy.kata.mis.medicalservice.model.entity.Doctor;
import academy.kata.mis.medicalservice.service.AppealBusinessService;
import academy.kata.mis.medicalservice.service.AuditMessageService;
import academy.kata.mis.medicalservice.service.DiseaseDepBusinessService;
import academy.kata.mis.medicalservice.service.DoctorBusinessService;
import academy.kata.mis.medicalservice.service.PatientBusinessService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.UUID;

@Slf4j
@PreAuthorize("hasAnyAuthority('DOCTOR', 'CHIEF_DOCTOR', 'DIRECTOR')")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/medical/doctor/appeal")
public class DoctorAppealOuterController {
    private final DoctorBusinessService doctorBusinessService;
    private final DiseaseDepBusinessService diseaseDepBusinessService;
    private final AppealBusinessService appealBusinessService;
    private final PatientBusinessService patientBusinessService;
    private final AuditMessageService auditMessageService;

    /**
     * страница 3.2.1
     */
    @GetMapping("/patient/open")
    public ResponseEntity<GetPatientAppealShortInfoResponse> getOpenAppealsByPatient(
            @RequestParam(name = "doctor_id") long doctorId,
            @RequestParam(name = "patient_id") long patientId) {
        //todo
        // проверить что доктор существует
        // проверить что авторизованный пользователь является доктором
        // проерить что пациент существует и они с доктором из одной организации
        // вернуть открытые обращения пациента которые открыты в отделении доктора

        return ResponseEntity.ok(null);
    }

    /**
     * страница 3.2.2
     */
    @PostMapping("/create")
    public ResponseEntity<GetAppealShortInfo> createAppealForPatient(
            @RequestBody @NotNull CreateAppealForPatientRequest request,
            Principal principal) {

        UUID doctorUUID = UUID.fromString(principal.getName());
        log.info("createAppealForPatient: doctor: {}, patientId = {}", principal.getName(), request.patientId());

        Doctor doctor = doctorBusinessService.getDoctorIfExists(doctorUUID, request.doctorId());
        if (!diseaseDepBusinessService.checkIsExistByIdAndDoctorId(request.diseaseDepId(), doctor.getId())) {
            log.debug("Заболевание с id {} и доктором с id {} не существует",
                    request.diseaseDepId(), doctor.getId());
            throw new LogicException("Заболевание не существует");
        }

        if (!patientBusinessService.isPatientExistsAndFromSameOrganizationAsDoctor(request.patientId(),
                request.doctorId())) {
            log.error("Пациент {}; не существует или находится с доктором в разных организациях",
                    request.patientId());
            throw new LogicException("Пациент не существует или находится с доктором в разных организациях");
        }

        GetAppealShortInfo response = appealBusinessService
                .createPatientVisit(doctor, request.diseaseDepId(), request.patientId(), request.insuranceType());

        log.debug("createAppealForPatient: doctor: {}, patientId = {}; response={}", principal.getName(),
                request.patientId(), response);

        auditMessageService.sendAudit(doctorUUID.toString(), "create-appeal",
                "success; appeal=" + response);

        return ResponseEntity.ok(response);
    }


    /**
     * страница 3.2.3
     */
    @GetMapping("/info")
    public ResponseEntity<GetAppealShortInfo> getAppealShortInfo(
            @RequestParam(name = "doctor_id") long doctorId,
            @RequestParam(name = "patient_id") long patientId,
            @RequestParam(name = "appeal_id") long appealId) {
        //todo
        // проверить что доктор существует
        // проверить что авторизованный пользователь является доктором
        // проверить что обращение существует и совпадает с отделением доктора
        // проерить что пациент существует и это его заболевание
        // вернуть информацию о обращении

        return ResponseEntity.ok(null);
    }
}
