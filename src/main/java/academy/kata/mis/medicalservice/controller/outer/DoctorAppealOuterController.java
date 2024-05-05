package academy.kata.mis.medicalservice.controller.outer;

import academy.kata.mis.medicalservice.exceptions.LogicException;
import academy.kata.mis.medicalservice.model.dto.CreateAppealForPatientRequest;
import academy.kata.mis.medicalservice.model.dto.GetAppealShortInfo;
import academy.kata.mis.medicalservice.model.dto.GetPatientAppealShortInfoResponse;
import academy.kata.mis.medicalservice.model.entity.Department;
import academy.kata.mis.medicalservice.model.entity.Doctor;
import academy.kata.mis.medicalservice.service.AppealBusinessService;
import academy.kata.mis.medicalservice.service.DoctorBusinessService;
import academy.kata.mis.medicalservice.service.DoctorService;
import academy.kata.mis.medicalservice.service.PatientBusinessService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.UUID;

@Slf4j
@PreAuthorize("hasAnyAuthority('DOCTOR', 'CHIEF_DOCTOR', 'DIRECTOR')")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/medical/doctor/appeal")
public class DoctorAppealOuterController {
    private final DoctorService doctorService;
    private final DoctorBusinessService doctorBusinessService;
    private final AppealBusinessService appealBusinessService;
    private final PatientBusinessService patientBusinessService;


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
    public ResponseEntity<String> createAppealForPatient(
            @RequestBody @NotNull CreateAppealForPatientRequest request,
            Principal principal) {

        UUID doctorUUID = UUID.fromString(principal.getName());
        log.info("Doctor try to make appeal for patient {}", principal.getName());

        isDoctorExists(doctorUUID, request.doctorId());

        Doctor doctor = doctorService.findDoctorByUUID(doctorUUID);
        doctorBusinessService
                .isDiseaseDepExistsAndMatchesDoctorDepartment(
                        request.diseaseDepId(), doctor.getDepartment());

        checkIsPatientExistsAndFromSameOrganizationAsDoctor(request.patientId(),
                doctor.getDepartment());

        appealBusinessService.createPatientVisit(doctor, request.diseaseDepId(), request.patientId());

        // оставить до лучших времен
//        String message = String.format("Создание обращения пациента %s", request.patientId());
//        auditMessageService.sendAudit(doctorUUID.toString(), "create-appeal", message);

        return ResponseEntity.ok("Обращение создано");
    }

    private void isDoctorExists(UUID doctorUUID, long id) {
        if (!doctorService.findDoctorByUUID(doctorUUID).getId().equals(id)) {
            log.error("Доктор с id:{}; не найден.", doctorUUID);
            throw new LogicException("Доктор не найден");
        }
    }

    private void checkIsPatientExistsAndFromSameOrganizationAsDoctor(long patientId, Department department) {
        if (!patientBusinessService.isPatientExistsAndFromSameOrganizationAsDoctor(patientId, department)) {
            log.error("Пациент {}; не существует или находится с доктором в разных организациях",
                    patientId);
            throw new LogicException("Пациент не существует или находится с доктором в разных организациях");
        }
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
