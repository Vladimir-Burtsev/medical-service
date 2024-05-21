package academy.kata.mis.medicalservice.controller.outer;

import academy.kata.mis.medicalservice.exceptions.LogicException;
import academy.kata.mis.medicalservice.model.dto.GetPatientAppealFullInfoResponse;
import academy.kata.mis.medicalservice.model.dto.GetPatientAppealsResponse;
import academy.kata.mis.medicalservice.model.entity.Appeal;
import academy.kata.mis.medicalservice.service.AppealBusinessService;
import academy.kata.mis.medicalservice.service.PatientBusinessService;
import academy.kata.mis.medicalservice.service.ReportServiceSender;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.security.Principal;
import java.util.UUID;

@Slf4j
@PreAuthorize("hasAuthority('PATIENT')")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/medical/patient/appeal")
public class PatientAppealsOuterController {

    private final PatientBusinessService patientBusinessService;
    private final AppealBusinessService appealBusinessService;
    private final ReportServiceSender reportServiceSender;

    @GetMapping("/all")
    public ResponseEntity<GetPatientAppealsResponse> getAppeals(
            @RequestParam(name = "patient_id") long patientId,
            @RequestParam(name = "count", required = false, defaultValue = "10") long count,
            @RequestParam(name = "page", required = false, defaultValue = "1") long page) {
        // проверить что пациент существует
        // проверить что авторизованный пользователь является этим пациентом
        // вернуть все заболевания пациента с пагинацией отсортированные по дате начала лечения (LIFO)

        return ResponseEntity.ok(null);
    }

    @GetMapping
    public ResponseEntity<GetPatientAppealFullInfoResponse> getAppealFullInfo(
            @RequestParam(name = "patient_id") long patientId,
            @RequestParam(name = "appeal_id") long appealId) {
        // проверить что пациент существует
        // проверить что авторизованный пользователь является этим пациентом
        // проверить что заболевание существует
        // проверить что заболевание принадлежит пациенту
        // вернуть информацию по заболеванию. Отсортировать посещения в любом порядке

        return ResponseEntity.ok(null);
    }

    @SneakyThrows
    @GetMapping("/report")
    public ResponseEntity<String> downloadAppealReport(
            @RequestParam(name = "patient_id") long patientId,
            @RequestParam(name = "appeal_id") long appealId,
            @RequestParam(name = "send_email", required = false, defaultValue = "false") boolean sendEmail,
            Principal principal) {
        log.info("downloadAppealReport: userId = {}, sendEmail = {}", patientId, sendEmail);

        if (!patientBusinessService.isPatientExistAndAuthenticatedUserPatient(patientId, principal.getName())) {
            throw new LogicException("Пациент с ID: " + patientId + " - не найден, или у вас нет прав доступа");
        }
        UUID userId = patientBusinessService.getUserId(patientId);
        Appeal appeal = appealBusinessService.isAppealExistAndPatientOwner(appealId, patientId);

        UUID operationId = UUID.randomUUID();
        reportServiceSender.sendInReportService(userId, sendEmail, appeal, operationId);
        Thread.sleep(3000);

        log.debug("downloadAppealReport: userId = {}, sendEmail = {}", userId, sendEmail);

        return !sendEmail ?
                ResponseEntity
                        .status(HttpStatus.SEE_OTHER)
                        .location(URI.create("http://localhost:8766/api/report/download?operation_id=" + operationId))
                        .build() :
                ResponseEntity
                        .status(HttpStatus.OK)
                        .body("Отчет отправлен на почту");
    }
}

