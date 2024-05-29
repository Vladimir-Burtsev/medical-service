package academy.kata.mis.medicalservice.controller.outer;

import academy.kata.mis.medicalservice.exceptions.LogicException;
import academy.kata.mis.medicalservice.model.dto.AssignPatientToTalonRequest;
import academy.kata.mis.medicalservice.model.dto.GetAssignedPatientTalonsByDepartmentsResponse;
import academy.kata.mis.medicalservice.model.dto.GetAssignedTalonsByPatientResponse;
import academy.kata.mis.medicalservice.model.dto.GetTalonFullInformationResponse;
import academy.kata.mis.medicalservice.model.dto.talon.CancelTalonDto;
import academy.kata.mis.medicalservice.service.AuditMessageService;
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
    private final AuditMessageService auditMessageService;

    /**
     * страница 2
     */
    @GetMapping("/assigned")
    public ResponseEntity<GetAssignedTalonsByPatientResponse> getAssignedTalonsByPatient(
            @RequestParam(name = "patient_id") long patientId,
            Principal principal) {
        UUID userId = UUID.fromString(principal.getName());
        log.info("getAssignedTalonsByPatient: patientId = {}, userId = {}", patientId, userId);

        if (!patientBusinessService.isPatientExistAndAuthenticatedUserPatient(patientId, userId)) {
            throw new LogicException(
                    String.format("%s %s %s", "Пациент с ID:", patientId, "- не найден, или у вас нет прав доступа"));
        }

        GetAssignedTalonsByPatientResponse response =
                talonBusinessService.getAllPatientTalonByPatientId(patientId);

        log.debug("getAssignedTalonsByPatient: patientId = {}, userId = {}, response: {}",
                patientId, userId, response);
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
        String operation = "Отмена записи на прием к врачу. CancelReservation.";
        UUID userId = UUID.fromString(principal.getName());
        log.info("{}; principal {}; talonID {}", operation, userId, talonId);

        if (!talonBusinessService.existsTalonByIdAndPatientUserId(talonId, userId)) {
            log.error("{}; ошибка: талон с Id = {} у пользователя с userId = {} не существует.",
                    operation, talonId, userId);
            throw new LogicException("У авторизованного пользователя отсутствует указанный талон на прием к врачу.");
        }

        CancelTalonDto talonDto = talonBusinessService.cancelReservationTalon(talonId, userId);

        auditMessageService.sendAudit(userId.toString(), operation,
                operation + ". Успешно! " + talonDto.toString());

        log.debug("{}; Успешно; principal {}; talonID {}", operation, userId, talonId);
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
        // отправить запись в аудит
        // кешировать в Хазелкаст информацию по талонам с доктором и пациентом на время 3 минуты
        // - если кто то обращается к талону то продлить еще на 3 минуты
        // - если кто то записался на прием то актуализировать инфу к кеше
        // проверить работу эндпоинта в двух копиях микросервиса

        // отправить сообщение на почту пациенту о успешной записи к врачу с текстом говорем о том:
        //  что пациент {имя фамилия} записан на прием в учреждение {название учреждения} по адресу {такому то}
        //  в отделение {название} такой то кабинет {номер} к доктору {фио} в {время}
        // в мессадж сервисе создать ивент который принимает параметры и отправляет сообщение
        // это через кафку

        // написать нагрузочный тест
        // сделать его дисэйбл
        // сохранить в базу 1 врача и создать ему 100 свободных талона
        // сохранить в базу 5 пациентов
        // создать тред пул из 10 потоков
        // итерируемся по талонам
        // для каждого талона итерируемся по пациентам
        // каждый пациент через тред пул пытается записаться на этот талон
        // тест производим не через контроллер а через бизнес сервис
        // используя верификацию репозитория талонов убедиться что метод вызывался только 100 раз
        // засечь время затраченное на запись на талоны

    }
}
