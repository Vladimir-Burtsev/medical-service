package academy.kata.mis.medicalservice.controller.outer;

import academy.kata.mis.medicalservice.model.dto.GetDiseaseDepShortInfoResponse;
import academy.kata.mis.medicalservice.model.dto.GetPatientAppealShortInfoResponse;
import academy.kata.mis.medicalservice.model.enums.DiseaseOrder;
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
@RequestMapping("/api/medical/doctor/disease")
public class DoctorDiseaseOuterController {

    /**
     * страница 3.2.2
     */
    @GetMapping("/find")
    public ResponseEntity<GetDiseaseDepShortInfoResponse> getOpenAppealsByPatient(
            @RequestParam(name = "doctor_id") long doctorId,
            @RequestParam(name = "disease_name", required = false, defaultValue = "") long diseaseName,
            @RequestParam(name = "identifier", required = false, defaultValue = "") long identifier,
            @RequestParam(name = "order", required = false, defaultValue = "IDENTIFIER_ASC") DiseaseOrder order,
            @RequestParam(name = "page", required = false, defaultValue = "1") long page,
            @RequestParam(name = "size", required = false, defaultValue = "10") long size) {
        //todo
        // проверить что доктор существует
        // проверить что авторизованный пользователь является доктором
        // вернуть все заболевания отделения доктора которые имеют статус 'open' и попадают под два строковых паттерна
        // пример:
        // доктор может передать либо ни одного паттерна поиска, либо один либо два
        // если паттерн имени имеет значение "кар" то будут найдены все заболевания у которых имя начинается на "кар"
        // если паттерн идентификатора равен "А.01" то аналогично с идентификаторами
        // если оба паттерна то будут найдены результаты которые удовлетворяют обоим условиям
        // если нет ни одного то выведены все
        // учесть что надо выводить с учетом сортировки
        // так же надо учесть что все выводится с учетом пагинации

        return ResponseEntity.ok(null);
    }
}
