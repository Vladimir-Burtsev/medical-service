package academy.kata.mis.medicalservice.controller.outer;

import academy.kata.mis.medicalservice.model.dto.GetDoctorTalonsResponse;
import academy.kata.mis.medicalservice.model.dto.GetDoctorsForDepartmentResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@Slf4j
@PreAuthorize("hasAuthority('PATIENT')")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/medical/patient/doctor")
public class PatientDoctorOuterController {

    @GetMapping("/all")
    public ResponseEntity<GetDoctorsForDepartmentResponse> getDoctors(
            @RequestParam(name = "patient_id") long patientId,
            @RequestParam(name = "department_id") long departmentId,
            @RequestParam(name = "date_start", required = false) LocalDateTime dateStart,
            @RequestParam(name = "date_end", required = false) LocalDateTime dateEnd)  {
        // создать переменную в проперти (если нету) - количество дней в пределах которых пациент видит свободные талоны
        // если не передана dateStart - используем now()
        // если не передана dateEnd - используем максимальную область видимости пациента
        // проверить что даты корректны - дата начала раньше даты конца
        // проверить что дата окончания не превышает области видимости пациента
        // проверить что пациент существует
        // проверить что авторизованный пользователь является этим пациентом
        // проверить что отделение существует
        // проверить что пациент и отделение из одной организации
        // вернуть всех докторов из отделения у которых есть свободные талоны в указанном диапазоне дней
        // результат отсортировать по убыванию свободных талонов у врача

        return ResponseEntity.ok(null);
    }

    @GetMapping("/talons")
    public ResponseEntity<GetDoctorTalonsResponse> getDoctorsTalons(
            @RequestParam(name = "patient_id") long patientId,
            @RequestParam(name = "department_id") long doctorId,
            @RequestParam(name = "date_start") LocalDateTime dateStart,
            @RequestParam(name = "date_end") LocalDateTime dateEnd,
            @RequestParam(name = "page", required = false, defaultValue = "1") long page,
            @RequestParam(name = "count",  required = false, defaultValue = "20") long count)  {
        // создать переменную в проперти (если нету) - количество дней в пределах которых пациент видит свободные талоны
        // проверить что даты корректны - дата начала раньше даты конца
        // проверить что дата окончания не превышает области видимости пациента
        // проверить что пациент существует
        // проверить что авторизованный пользователь является этим пациентом
        // проверить что доктор существует
        // проверить что пациент и доктор из одной организации
        // вернуть все свободные талоны доктора в указанном диапазоне дней
        // результат отсортировать по возрастанию даты
        // результат вывести с пагинацией

        return ResponseEntity.ok(null);
    }
}
