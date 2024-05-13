package academy.kata.mis.medicalservice.controller.outer;

import academy.kata.mis.medicalservice.exceptions.AuthException;
import academy.kata.mis.medicalservice.exceptions.LogicException;
import academy.kata.mis.medicalservice.model.dto.AssignPatientToTalonRequest;
import academy.kata.mis.medicalservice.model.dto.GetAssignedPatientTalonsByDepartmentsResponse;
import academy.kata.mis.medicalservice.model.dto.GetAssignedTalonsByPatientResponse;
import academy.kata.mis.medicalservice.model.dto.GetTalonFullInformationResponse;
import academy.kata.mis.medicalservice.model.dto.auth.JwtAuthentication;
import academy.kata.mis.medicalservice.service.PatientService;
import academy.kata.mis.medicalservice.service.TalonBusinessService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.NoSuchElementException;
import java.util.UUID;

@Slf4j
@PreAuthorize("hasAuthority('PATIENT')")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/medical/patient/talon")
public class PatientTalonOuterController {
    private final TalonBusinessService talonBusinessService;
    private final PatientService patientService;

    /**
     * страница 2
     */
    @GetMapping("/assigned")
    public ResponseEntity<GetAssignedTalonsByPatientResponse> getAssignedTalonsByPatient(
            @RequestParam(name = "patient_id") long patientId) {
        log.info("Поиск пациента по интедефикатору для получения всех талонов пациента: {}", patientId);
        String patientUserId = checkPatientExist(patientId);

        checkPatientIsAutUser(patientUserId);

        GetAssignedTalonsByPatientResponse response =
                talonBusinessService.getAllPatientTalonByPatientId(patientId);

        log.debug("Талоны пользователя {} - получены", patientId);

        //после успешного мерджа в мастер второй частью задачи выполнить получение информации о докторе в других сервисах и попраавить тесты

        return ResponseEntity.ok(response);
    }

    private String checkPatientExist(long patientId) {
        return patientService.getPatientUserIdByPatientId(patientId)
                .orElseThrow(() -> {
                    log.error("Пациент не найден: PatientId: {}", patientId);
                    return new NoSuchElementException("Patient with id: " + patientId + " does not exist");
                });
    }

    private void checkPatientIsAutUser(String userId) {
        UUID authUserId = ((JwtAuthentication) SecurityContextHolder.getContext().getAuthentication())
                .getUserId();
        if (!userId.equals(authUserId.toString())) {
            log.error("У авторизованного пользователя с Id: {} нет доступа к данным о пациенте", userId);
            throw new AuthException("User with id: " + authUserId + " does not have access");
        }
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
