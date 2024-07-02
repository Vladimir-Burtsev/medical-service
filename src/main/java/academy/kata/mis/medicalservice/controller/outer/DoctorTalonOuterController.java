package academy.kata.mis.medicalservice.controller.outer;

import academy.kata.mis.medicalservice.exceptions.LogicException;
import academy.kata.mis.medicalservice.model.dto.AssignPatientToTalonRequest;
import academy.kata.mis.medicalservice.model.dto.GetAllDoctorTalonsResponse;
import academy.kata.mis.medicalservice.model.dto.GetDoctorCurrentDayTalonsResponse;
import academy.kata.mis.medicalservice.model.dto.GetFullTalonInformationResponse;
import academy.kata.mis.medicalservice.service.DoctorBusinessService;
import academy.kata.mis.medicalservice.service.TalonBusinessService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.UUID;

@Slf4j
@PreAuthorize("hasAnyAuthority('DOCTOR', 'CHIEF_DOCTOR', 'DIRECTOR')")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/medical/doctor/talon")
public class DoctorTalonOuterController {

    private final TalonBusinessService talonBusinessService;
    private final DoctorBusinessService doctorBusinessService;

    /**
     * страница 3
     */
    @GetMapping
    public ResponseEntity<GetDoctorCurrentDayTalonsResponse> getCurrentDayTalons(
            @RequestParam(name = "doctor_id") long doctorId) {
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
            @RequestParam(name = "talon_id") long talonId,
            Principal principal) {

        UUID authUserId = UUID.fromString(principal.getName());
        String operation = "Получение информации о талоне; getFullTalonInfo";
        log.info("{}; authUserId - {}, talonId - {}", operation, authUserId, talonId);

        if (!talonBusinessService.existsTalonById(talonId)) {
            log.error("{}; Талон с talonId = {} не существует.", operation, talonId);
            throw new LogicException("Талон не найден!");
        }

        Long doctorId = doctorBusinessService.getDoctorIdByTalonId(talonId);
        if (!doctorBusinessService.existDoctorByUserIdAndDoctorId(authUserId, doctorId)) {
            log.error("{}; Авторизованный пользователь (userId = {}) не является доктором(doctorId = {}), " +
                    "которому принадлежит талон (talonId = {})", operation, authUserId, doctorId, talonId);
            throw new LogicException("Авторизованный пользователь не является доктором, которому принадлежит талон");
        }

        // вернуть полную информацию о талоне (пациент может отсутствовать)
        GetFullTalonInformationResponse response = talonBusinessService
                .getFullTalonInfoByIdAndDoctorId(talonId, doctorId);

        log.debug("{}; Успешно; response - {}", operation, response);
        return ResponseEntity.ok(response);
    }

    /**
     * страница 3.1.3
     */
    @PatchMapping("/assign")
    public ResponseEntity<GetFullTalonInformationResponse> assignPatientToTalon(
            @RequestBody @NotNull AssignPatientToTalonRequest request) {
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
        // проерить что талон существует
        // проверить что талон принадлежит доктору который является авторизованным пользователем
        // проверить что талон не занят
        // использовать пессимистичную блокировку для
        // отправить запись в аудит

        // написать нагрузочный тест
        // сохранить в базу 1 врача и создать ему 100 свободных талона
        // сохранить в базу 5 пациентов
        // создать тред пул из 10 потоков
        // итерируемся по талонам
        // для каждого талона итерируемся по пациентам
        // каждый пациент через тред пул пытается записаться на этот талон
        // тест производим не через контроллер а через бизнес сервис
        // используя верификацию репозитория талонов убедиться что метод вызывался только 100 раз
        // засечь время затраченное на запись на талоны

        return ResponseEntity.ok(null);
    }

    /**
     * страница 3.1.3
     */
    @PatchMapping("/unassign")
    public ResponseEntity<GetFullTalonInformationResponse> unAssignPatientFromTalon(
            @RequestParam(name = "talon_id") long talonId) {
        // проерить что талон существует
        // проверить что талон принадлежит доктору который является авторизованным пользователем
        // проверить что талон занят пациентом
        // снять пациента с приема к врачу
        // отправить запись в аудит
        // отправить письмо на почту пациенту с информацией о снятии с записи на прием

        return ResponseEntity.ok(null);
    }
}
