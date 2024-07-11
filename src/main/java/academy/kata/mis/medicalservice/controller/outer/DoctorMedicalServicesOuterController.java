package academy.kata.mis.medicalservice.controller.outer;

import academy.kata.mis.medicalservice.model.dto.GetMedicalServicesDepResponse;
import academy.kata.mis.medicalservice.model.enums.MedicalServiceOrder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@PreAuthorize("hasAnyAuthority('DOCTOR', 'CHIEF_DOCTOR', 'DIRECTOR')")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/medical/doctor/service")
public class DoctorMedicalServicesOuterController {

    /**
     * страница 3.2.4
     */
    @GetMapping("/all")
    public ResponseEntity<GetMedicalServicesDepResponse> getMedicalServices(
            @RequestParam(name = "doctor_id") long doctorId
//            @RequestParam(name = "diseaseName", required = false, defaultValue = "") String diseaseName,
//            @RequestParam(name = "identifier", required = false, defaultValue = "") String identifier,
//            @RequestParam(name = "size", required = false, defaultValue = "10") long size,
//            @RequestParam(name = "page", required = false, defaultValue = "1") long page,
//            @RequestParam(name = "order", required = false, defaultValue = "IDENTIFIER_ASC") MedicalServiceOrder order
    ) {
        //1 часть вернуть все услуги отделения доктора без доп параметров
        //2 часть вернуть с параметрами diseaseName и identifier (startWith) diseaseName=пере identifier=A17
        //примеры:
        // переодонтит обычный идент=A170001
        // переодонтит слабый идент=A170102
        // переодонтит сильный идент=A173003
        // переопределенныйМетод сильный идент=A17123
        //
        //3 часть подключает пагинацию - size и page и сортировка

        // проверить что доктор существует и авторизованный пользователь это он
        // вернуть услуги которые может оказывать отделение доктора у которых статус 'open'
        // найти все услуги которые выполняют условия обоих паттернов поиска.
        // выполнить с пагинацией и сортировкой

        return ResponseEntity.ok(null);
    }
}
