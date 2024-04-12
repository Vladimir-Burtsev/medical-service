package academy.kata.mis.medicalservice.controller.outer;

import academy.kata.mis.medicalservice.model.dto.GetDepartmentsResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@PreAuthorize("hasAuthority('PATIENT')")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/medical/patient/department")
public class PatientDepartmentController {

    @GetMapping
    public ResponseEntity<GetDepartmentsResponse> getDepartments(
            @RequestParam(name = "patient_id") long patientId) {
        //todo
        // создать переменную в проперти (если нету) - количество дней в пределах которых пациент видит свободные талоны
        // проверить что пациент существует
        // проверить что авторизованный пользователь является этим пациентом
        // вернуть все отделения учреждения пациента в которых есть свободные талоны в пределах видимости пациента в этой организации

        return ResponseEntity.ok(null);
    }
}
