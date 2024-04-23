package academy.kata.mis.medicalservice.controller.outer;

import academy.kata.mis.medicalservice.exceptions.LogicException;
import academy.kata.mis.medicalservice.model.dto.AssignPatientToTalonRequest;
import academy.kata.mis.medicalservice.model.dto.GetAssignedPatientTalonsByDepartmentsResponse;
import academy.kata.mis.medicalservice.model.dto.GetAssignedTalonsByPatientResponse;
import academy.kata.mis.medicalservice.model.dto.GetTalonFullInformationResponse;
import academy.kata.mis.medicalservice.model.entity.Talon;
import academy.kata.mis.medicalservice.service.PatientService;
import academy.kata.mis.medicalservice.service.TalonService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@PreAuthorize("hasAuthority('PATIENT')")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/medical/patient/talon")
public class PatientTalonOuterController {

    private final TalonService talonService;
    private final PatientService patientService;

    @GetMapping("/assigned")
    public ResponseEntity<GetAssignedTalonsByPatientResponse> getAssignedTalonsByPatient(
            @RequestParam(name = "patient_id") long patientId) {
        //todo
        // проверить что пациент существует
        // проверить что авторизованный пользователь является этим пациентом
        // вернуть все талоны на которые записан пациент

        return ResponseEntity.ok(null);
    }

    @GetMapping("/assigned/full")
    public ResponseEntity<GetAssignedPatientTalonsByDepartmentsResponse> getAssignedTalonsByPatientFull(
            @RequestParam(name = "patient_id") long patientId) {
        //todo
        // проверить что пациент существует
        // проверить что авторизованный пользователь является этим пациентом
        // вернуть все талоны на которые записан пациент в разрезе отделений организации пациента

        return ResponseEntity.ok(null);
    }

    @GetMapping
    public ResponseEntity<GetTalonFullInformationResponse> getTalonFullInformation(
            @RequestParam(name = "patient_id") long patientId,
            @RequestParam(name = "talon_id") long talonId) {
        //todo
        // проверить что пациент существует
        // проверить что авторизованный пользователь является этим пациентом
        // проверить что талон существует
        // проверить что талон принадлежит этому пациенту
        // вернуть все талоны на которые записан пациент

        return ResponseEntity.ok(null);
    }

    @PatchMapping("/unassign")
    public void cancelReservation(
            @RequestParam(name = "talon_id") Long talonId, Principal principal) {
        String operation = "Отмена записи на прием к врачу";
        log.info("{}; principal {}; talonID {}", operation, principal.getName(), talonId);

        Optional<Talon> talon = talonService.findById(talonId);
        //проверка что талон существует
        if (talon.isEmpty()) {
            log.error("{}; ошибка: талон с указанным Id не найден; talonId {}", operation, talonId);
            throw new LogicException("Талон с Id = " + talonId + " не сущестует.");
        }

        //проверка что талон принадлежит авторизованному пользователю
        UUID userId = UUID.fromString(principal.getName()); // Id авторизованного пользователя
        UUID userIdTalon = talon.get().getPatient().getUserId(); //Id пользователя талона
        if (!userIdTalon.equals(userId)) {
            log.error("{}; Ошибка: авторизованный пользователь не является владельцем талона; " +
                    "Id авторизованного пользователя {}; Id пользователя талона {}", operation, userId, talonId);
            throw new LogicException("Авторизованный пользователь не является владельцем талона.");
        }

        //Отмена записи к врачу
        talonService.cancelReservationTalon(talon.get());

        log.debug("{}; Успешно; principal {}; talonID {}", operation, principal.getName(), talonId);

        //todo
        // проверить что талон существует
        // проверить что талон принадлежит авторизованному пациенту
        // отменить запись к врачу (талон должен остаться без ссылки на пациента)
        // отправить сообщение на почту пациенту что запись к врачу отменена пациентом
    }

    @PatchMapping("/assign")
    public void signUpForTalon(
            @RequestBody @NotNull AssignPatientToTalonRequest request) {
        //todo
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
