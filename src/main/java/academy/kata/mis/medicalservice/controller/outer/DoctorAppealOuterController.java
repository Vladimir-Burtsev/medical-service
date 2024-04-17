package academy.kata.mis.medicalservice.controller.outer;

import academy.kata.mis.medicalservice.model.dto.CreateAppealForPatientRequest;
import academy.kata.mis.medicalservice.model.dto.GetAppealShortInfo;
import academy.kata.mis.medicalservice.model.dto.GetPatientAppealShortInfoResponse;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@PreAuthorize("hasAnyAuthority('DOCTOR', 'CHIEF_DOCTOR', 'DIRECTOR')")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/medical/doctor/appeal")
public class DoctorAppealOuterController {

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
            @RequestBody @NotNull CreateAppealForPatientRequest request) {
        //todo
        // проверить что доктор существует
        // проверить что авторизованный пользователь является доктором
        // проверить что заболевание отделения существует и совпадает с отделением доктора
        // проерить что пациент существует и они с доктором из одной организации
        // создать обращение пациенту
        // создать посещение связанное с доктором и заболеванием
        // сделать запись в аудит

        return ResponseEntity.ok(null);
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
