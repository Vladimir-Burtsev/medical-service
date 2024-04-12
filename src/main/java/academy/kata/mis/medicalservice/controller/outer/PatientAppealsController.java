package academy.kata.mis.medicalservice.controller.outer;

import academy.kata.mis.medicalservice.model.dto.RedirectUrlResponse;
import academy.kata.mis.medicalservice.model.dto.GetPatientAppealFullInfoResponse;
import academy.kata.mis.medicalservice.model.dto.GetPatientAppealsResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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
@RequestMapping("/api/medical/patient/appeal")
public class PatientAppealsController {

    @GetMapping
    public ResponseEntity<GetPatientAppealsResponse> getAppeals(
            @RequestParam(name = "patient_id") long patientId,
            @RequestParam(name = "count", required = false, defaultValue = "10") long count,
            @RequestParam(name = "page", required = false, defaultValue = "1") long page) {
        //todo
        // проверить что пациент существует
        // проверить что авторизованный пользователь является этим пациентом
        // вернуть все заболевания пациента с пагинацией отсортированные по дате начала лечения (LIFO)

        return ResponseEntity.ok(null);
    }

    @GetMapping
    public ResponseEntity<GetPatientAppealFullInfoResponse> getAppealFullInfo(
            @RequestParam(name = "patient_id") long patientId,
            @RequestParam(name = "appeal_id") long appealId) {
        //todo
        // проверить что пациент существует
        // проверить что авторизованный пользователь является этим пациентом
        // проверить что заболевание существует
        // проверить что заболевание принадлежит пациенту
        // вернуть информацию по заболеванию. Отсортировать посещения в любом порядке

        return ResponseEntity.ok(null);
    }

    @GetMapping
    public ResponseEntity<RedirectUrlResponse> downloadAppealReport(
            @RequestParam(name = "patient_id") long patientId,
            @RequestParam(name = "appeal_id") long appealId,
            @RequestParam(name = "send_email", required = false, defaultValue = "false") boolean sendEmail) {
        //todo
        // проверить что пациент существует
        // проверить что авторизованный пользователь является этим пациентом
        // проверить что заболевание существует
        // проверить что заболевание принадлежит пациенту
        // собрать всю необходимую информацию и через кафку отправить в report service + uuid пользователя и если надо то email пользователя.
        // вернуть редирект с (url + uuid операции) в репорт сервис по которому можно постучаться и скачать файл

        //todo
        // в репорт сервисе
        // из запроса принять собственника отчета и если есть то email
        // собрать pdf документ и положить в базу данных nosql или minio (предложить решение)
        // если был передан email то отправть через message-service ссылку для скачивания отчета

        //todo
        // Написать эндпоинт по которому можно придти с uuid операции и uuid пользователя из jwt и забрать pdf если авторизованный пользователь совпадает с владельцем


        return ResponseEntity.status(HttpStatus.SEE_OTHER).body(null);
    }

}
