package academy.kata.mis.medicalservice.controller.outer;

import academy.kata.mis.medicalservice.exceptions.LogicException;
import academy.kata.mis.medicalservice.model.dto.AssignPatientToTalonRequest;
import academy.kata.mis.medicalservice.model.dto.GetAssignedPatientTalonsByDepartmentsResponse;
import academy.kata.mis.medicalservice.model.dto.GetAssignedTalonsByPatientResponse;
import academy.kata.mis.medicalservice.model.dto.GetTalonFullInformationResponse;
import academy.kata.mis.medicalservice.service.PatientBusinessService;
import academy.kata.mis.medicalservice.service.TalonBusinessService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.UUID;

@Slf4j
@PreAuthorize("hasAuthority('PATIENT')")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/medical/patient/talon")
public class PatientTalonOuterController {
    private final TalonBusinessService talonBusinessService;
    private final PatientBusinessService patientBusinessService;

    /**
     * страница 2
     */
    @GetMapping("/assigned")
    public ResponseEntity<GetAssignedTalonsByPatientResponse> getAssignedTalonsByPatient(
            @RequestParam(name = "patient_id") long patientId, Principal principal) {
        log.info("getAssignedTalonsByPatient: patientId = {}, userId = {}", patientId, principal.getName());

        if (!patientBusinessService.isPatientExistAndAuthenticatedUserPatient(patientId, principal.getName())) {
            throw new LogicException("Пациент с ID: " + patientId + " - не найден, или у вас нет прав доступа");
        }

        GetAssignedTalonsByPatientResponse response =
                talonBusinessService.getAllPatientTalonByPatientId(patientId);

        log.debug("getAssignedTalonsByPatient: patientId = {}, userId = {}, response: {}",
                patientId, principal.getName(), response);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/assigned/full")
    public ResponseEntity<GetAssignedPatientTalonsByDepartmentsResponse> getAssignedTalonsByPatientFull(
            @RequestParam(name = "patient_id") long patientId) {
        // проверить что пациент существует
        // проверить что авторизованный пользователь является этим пациентом
        // вернуть все талоны на которые записан пациент в разрезе отделений организации пациента

        return ResponseEntity.ok(null);
    }

    @GetMapping
    public ResponseEntity<GetTalonFullInformationResponse> getTalonFullInformation(
            @RequestParam(name = "patient_id") long patientId,
            @RequestParam(name = "talon_id") long talonId) {
        // проверить что пациент существует
        // проверить что авторизованный пользователь является этим пациентом
        // проверить что талон существует
        // проверить что талон принадлежит этому пациенту
        // вернуть все талоны на которые записан пациент

        return ResponseEntity.ok(null);
    }

    @PatchMapping("/unassign")
    public void cancelReservation(@RequestParam(name = "talon_id") Long talonId,
                                  Principal principal) {
        String operation = "Отмена записи на прием к врачу";
        log.info("{}; principal {}; talonID {}", operation, principal.getName(), talonId);

        if (!talonBusinessService.existsTalonByIdAndPatientUserId(talonId, UUID.fromString(principal.getName()))) {
            log.error("{}; ошибка: талон с указанным Id не найден у пользователя с UserId; talonId {}; UserId {}",
                    operation, talonId, principal.getName());
            throw new LogicException("Талон с Id = " + talonId + " у пользователя с userId = "
                    + principal.getName() + " не сущестует.");
        }

        talonBusinessService.cancelReservationTalon(talonId);

        log.debug("{}; Успешно; principal {}; talonID {}", operation, principal.getName(), talonId);
        //todo
        // отправить сообщение на почту пациенту что запись к врачу отменена пациентом
    }

    @PatchMapping("/assign")
    public void signUpForTalon(
            @RequestBody @NotNull AssignPatientToTalonRequest request) {
        // создать переменную в проперти - минуты до блокировки записи до приема
        // проверить что пациент существует
        // проверить что авторизованный пользователь является этим пациентом
        // проверить что талон существует
        // проверить что талон свободен
        // проверить что пациент и доктор - разные люди (что бы доктор не записался сам к себе)
        // проверить что до приема более 10 минут (использовать проперти)
        // записать пациента на указанный талон к врачу с пессимистичной блокировкой (может быть конкуренция)
        // отправить сообщение на почту пациенту о успешной записи к врачу
    }
}
