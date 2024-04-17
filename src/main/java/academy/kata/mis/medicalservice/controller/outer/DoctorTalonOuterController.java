package academy.kata.mis.medicalservice.controller.outer;

import academy.kata.mis.medicalservice.model.dto.AssignPatientToTalonRequest;
import academy.kata.mis.medicalservice.model.dto.GetAllDoctorTalonsResponse;
import academy.kata.mis.medicalservice.model.dto.GetDoctorCurrentDayTalonsResponse;
import academy.kata.mis.medicalservice.model.dto.GetFullTalonInformationResponse;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Slf4j
@PreAuthorize("hasAnyAuthority('DOCTOR', 'CHIEF_DOCTOR', 'DIRECTOR')")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/medical/doctor/talon")
public class DoctorTalonOuterController {

    /**
     * страница 3
     */
    @GetMapping
    public ResponseEntity<GetDoctorCurrentDayTalonsResponse> getCurrentDayTalons(
            @RequestParam(name = "doctor_id") long doctorId) {
        //todo
        // проерить что доктор существует
        // проверить что совпадают доктор и авторизованный пользователь
        // вернуть все занятые талоны доктора на сегодняшний день отсортированные по дате

        return ResponseEntity.ok(null);
    }

    /**
     * страница 3.1.1
     */
    @GetMapping("/all")
    public ResponseEntity<GetAllDoctorTalonsResponse> getAllTalons(
            @RequestParam(name = "doctor_id") long doctorId,
            @RequestParam(name = "date_start", required = false) LocalDate dateStart,
            @RequestParam(name = "date_end", required = false) LocalDate dateEnd,
            @RequestParam(name = "size", required = false, defaultValue = "5") long size,
            @RequestParam(name = "page", required = false, defaultValue = "1") long page) {
        //todo
        // создать в проперти переменную - количество дней видимых доктором
        // проерить что доктор существует
        // проверить что совпадают доктор и авторизованный пользователь
        // если dateStart не передана то использовать сегодня
        // если dateEnd не передана то использовать максимальную область видимости доктора
        // проверить правильность дат - ограничение видимостью доктора и последовательность дат
        // вернуть все дни доктора с талонами в которых есть хотя бы один талон с пагинацией дней в переданном диапазоне дней
        //

        return ResponseEntity.ok(null);
    }

    /**
     * страница 3.1.2
     * страница 3.1.4
     */
    @GetMapping("/full/info")
    public ResponseEntity<GetFullTalonInformationResponse> getFullTalonInfo(
            @RequestParam(name = "talon_id") long talonId) {
        //todo
        // проерить что талон существует
        // проверить что талон принадлежит доктору который является авторизованным пользователем
        // вернуть полную информацию о талоне (пациент может отсутсвовать)

        return ResponseEntity.ok(null);
    }

    /**
     * страница 3.1.3
     */
    @PatchMapping("/assign")
    public ResponseEntity<GetFullTalonInformationResponse> assignPatientToTalon(
            @RequestBody @NotNull AssignPatientToTalonRequest request) {
        //todo
        // проерить что талон существует
        // проверить что талон принадлежит доктору который является авторизованным пользователем
        // проверить что пациент существует и они с доктором из одной организации
        // проверить что талон не занят
        // записать пациента на прием к врачу
        // использовать пессимистичную блокировку
        // отправить запись в аудит
        // отправить письмо на почту пациенту с информацией о записи на прием

        return ResponseEntity.ok(null);
    }

    /**
     * страница 3.1.2
     */
    @DeleteMapping
    public ResponseEntity<Void> deleteTalon(
            @RequestParam(name = "talon_id") long talonId) {
        //todo
        // проерить что талон существует
        // проверить что талон принадлежит доктору который является авторизованным пользователем
        // проверить что талон не занят
        // использовать пессимистичную блокировку
        // отправить запись в аудит

        return ResponseEntity.ok(null);
    }

    /**
     * страница 3.1.3
     */
    @PatchMapping("/unassign")
    public ResponseEntity<GetFullTalonInformationResponse> unAssignPatientFromTalon(
            @RequestParam(name = "talon_id") long talonId) {
        //todo
        // проерить что талон существует
        // проверить что талон принадлежит доктору который является авторизованным пользователем
        // проверить что талон занят пациентом
        // снять пациента с приема к врачу
        // отправить запись в аудит
        // отправить письмо на почту пациенту с информацией о снятии с записи на прием

        return ResponseEntity.ok(null);
    }
}
