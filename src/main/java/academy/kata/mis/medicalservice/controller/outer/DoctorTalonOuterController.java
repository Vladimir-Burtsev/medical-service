package academy.kata.mis.medicalservice.controller.outer;

import academy.kata.mis.medicalservice.model.dto.GetAllDoctorTalonsResponse;
import academy.kata.mis.medicalservice.model.dto.GetDoctorCurrentDayTalonsResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@Slf4j
@PreAuthorize("hasAuthority('DOCTOR')")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/medical/doctor/talon")
public class DoctorTalonOuterController {

    @GetMapping
    public ResponseEntity<GetDoctorCurrentDayTalonsResponse> getCurrentDayTalons(
            @RequestParam(name = "doctor_id") long doctorId) {
        //todo
        // проерить что доктор существует
        // проверить что совпадают доктор и авторизованный пользователь
        // вернуть все занятые талоны доктора на сегодняшний день отсортированные по дате

        return ResponseEntity.ok(null);
    }

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
        // если dateEnd не передана то использовать максимальную область видисомти доктора
        // проверить правильность дат - ограничение видимостью доктора и последовательность дат
        // вернуть все дни доктора с талонами в которых есть хотя бы один талон с пагинацией дней в переданном диапазоне дней
        //

        return ResponseEntity.ok(null);
    }

    @GetMapping("/full")
    public ResponseEntity<GetAllDoctorTalonsResponse> getFullTalonInfo(
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
        // если dateEnd не передана то использовать максимальную область видисомти доктора
        // проверить правильность дат - ограничение видимостью доктора и последовательность дат
        // вернуть все дни доктора с талонами в которых есть хотя бы один талон с пагинацией дней в переданном диапазоне дней
        //

        return ResponseEntity.ok(null);
    }
}
