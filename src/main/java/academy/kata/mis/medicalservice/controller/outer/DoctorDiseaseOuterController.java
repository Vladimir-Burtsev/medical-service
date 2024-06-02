package academy.kata.mis.medicalservice.controller.outer;

import academy.kata.mis.medicalservice.exceptions.LogicException;
import academy.kata.mis.medicalservice.model.dto.GetDiseaseDepShortInfoResponse;
import academy.kata.mis.medicalservice.model.enums.DiseaseOrder;
import academy.kata.mis.medicalservice.service.DiseaseBusinessService;
import academy.kata.mis.medicalservice.service.DoctorBusinessService;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.UUID;

@Slf4j
@PreAuthorize("hasAnyAuthority('DOCTOR', 'CHIEF_DOCTOR', 'DIRECTOR')")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/medical/doctor/disease")
public class DoctorDiseaseOuterController {
    private final DoctorBusinessService doctorBusinessService;
    private final DiseaseBusinessService diseaseBusinessService;

    /**
     * страница 3.2.2
     */
    @GetMapping("/find")
    public ResponseEntity<GetDiseaseDepShortInfoResponse> getSortOpenDiseaseByDoctorId(
            @RequestParam(name = "doctor_id") long doctorId,
            @RequestParam(name = "disease_name", required = false, defaultValue = "") String diseaseName,
            @RequestParam(name = "identifier", required = false, defaultValue = "") String identifier,
            @RequestParam(name = "order", required = false, defaultValue = "IDENTIFIER_ASC") DiseaseOrder order,
            @RequestParam(name = "page", required = false, defaultValue = "1") @Min(1) int page,
            @RequestParam(name = "size", required = false, defaultValue = "10") @Min(1) @Max(30) int size,
            Principal principal) {
        String operation = "Возвращение всех заболеваний отделения.";
        UUID userId = UUID.fromString(principal.getName());
        log.info("{} doctorId = {}, userId = {}. Сортировка: diseaseName - {}, identifier - {}, order - {}." +
                 " Пагинация: page - {}, size - {}",
                 operation, doctorId, userId, diseaseName, identifier, order, page, size);

        if (!doctorBusinessService.existDoctorByUserIdAndDoctorId(userId, doctorId)) {
            log.error("{} Ошибка! Доктор с doctorId = {} и userId = {} не существует.", operation, doctorId, userId);
            throw new LogicException("Доктор не найден!");
        }

        GetDiseaseDepShortInfoResponse response = diseaseBusinessService
                .getDiseaseDepShortInfoResponse(doctorId, diseaseName, identifier, order, page, size);

        log.debug("{} doctorId = {}, userId = {}. Успешно! " +
                  "Сортировка: diseaseName - {}, identifier - {}, order - {}. " +
                  "Пагинация: page - {}, size - {}. Response: {}.",
                  operation, doctorId, userId, diseaseName, identifier, order, page, size, response);

        // проверить что доктор существует +
        // проверить что авторизованный пользователь является доктором +
        // вернуть все заболевания отделения доктора которые имеют статус 'open' и попадают под два строковых паттерна
        // пример:
        // доктор может передать либо ни одного паттерна поиска, либо один либо два
        // если паттерн имени имеет значение "кар" то будут найдены все заболевания у которых имя начинается на "кар"
        // если паттерн идентификатора равен "А.01" то аналогично с идентификаторами
        // если оба паттерна то будут найдены результаты которые удовлетворяют обоим условиям
        // если нет ни одного то выведены все
        // учесть что надо выводить с учетом сортировки
        // так же надо учесть что все выводится с учетом пагинации

        return ResponseEntity.ok(response);
    }
}
