package academy.kata.mis.medicalservice.controller.outer;

import academy.kata.mis.medicalservice.exceptions.LogicException;
import academy.kata.mis.medicalservice.feign.PersonFeignClient;
import academy.kata.mis.medicalservice.model.dto.RedirectUrlResponse;
import academy.kata.mis.medicalservice.model.dto.GetPatientAppealFullInfoResponse;
import academy.kata.mis.medicalservice.model.dto.GetPatientAppealsResponse;
import academy.kata.mis.medicalservice.model.entity.Appeal;
import academy.kata.mis.medicalservice.model.entity.Patient;
import academy.kata.mis.medicalservice.service.AppealService;
import academy.kata.mis.medicalservice.service.PatientService;
import academy.kata.mis.medicalservice.service.ReportServiceSender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@PreAuthorize("hasAuthority('PATIENT')")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/medical/patient/appeal")
public class PatientAppealsOuterController {

    private final PatientService patientService;
    private final AppealService appealService;
    private final ReportServiceSender reportServiceSender;
    private final PersonFeignClient personFeignClient;


    @GetMapping("/all")
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

    @GetMapping("/report")
    public ResponseEntity<RedirectUrlResponse> downloadAppealReport(
            @RequestParam(name = "patient_id") long patientId,
            @RequestParam(name = "appeal_id") long appealId,
            @RequestParam(name = "send_email", required = false, defaultValue = "false") boolean sendEmail) {

        UUID userId = isPatientExistAndAuthenticatedUserPatient(patientId);
        Appeal appeal = isAppealExistAndPatientOwner(appealId, patientId);
        String email = sendEmail ? personFeignClient.getPersonContactByUserId(userId) : null;


        String info = String.format("""
                        Диагноз: %s
                        Стаус обращения: %s
                        Способ оплаты: %s
                        Сумма лечения: %s
                                       
                        Дата открытия: %s
                        Врач: %s
                        Услуги: %s
                                       
                        Дата закрытия: %s
                        Врач: %s""", appeal.getDiseaseDep().getDisease().getName(), appeal.getStatus(),
                appeal.getInsuranceType(), "moneyForAppeal", "1000", appeal.getOpenDate(), "doctorName",
                "apealServices", appeal.getClosedDate(), "doctorName");           //todo убрать загушки

        reportServiceSender.sendInReportService(userId, email, info);


        //todo
        // сходить и экономик сервис и узнать сумму лечения (заглушка)
        // собрать всю необходимую информацию и через кафку отправить в report service + uuid пользователя и если надо то email пользователя.
        // вернуть редирект с (url + uuid операции) в репорт сервис по которому можно постучаться и скачать файл

        //todo
        // в репорт сервисе
        // из запроса принять собственника отчета и если есть то email
        // собрать pdf документ и положить в базу данных postgres и minio (предложить решение)
        // если был передан email то отправть через message-service ссылку для скачивания отчета

        //todo
        // Написать эндпоинт по которому можно придти с uuid операции и uuid пользователя из jwt и забрать pdf если авторизованный пользователь совпадает с владельцем
        // вернуть byte[] и правильно указать Content-type
        // и надо подумать как очишать эту базу - всем файлам проставлять время дедлайна, каждые 3 часа запушкать шедулед таску которая убьет старые файлы
        // помним! шедулед таска будет запускаться в каждом инстансе а нам надо только один запуск (подойти к ментору)

        return ResponseEntity.status(HttpStatus.SEE_OTHER).body(null);
    }

    private UUID isPatientExistAndAuthenticatedUserPatient(long patientId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Patient patient1 = patientService.getPatientById(patientId);
        log.info("authentication: {}", authentication.getName());
        log.info( "patient: {}", patient1.getUserId());
        log.info(String.valueOf(UUID.fromString( authentication.getName()).equals(patient1.getUserId())));

        Optional.ofNullable(patientService.getPatientById(patientId))
                .filter(patient -> patient.getUserId().equals(UUID.fromString(authentication.getName())))

                .orElseThrow(() -> {
                    if (patientService.getPatientById(patientId) == null) {
                        log.error("Пациент не найден; patientId:{};", patientId);
                        return new LogicException(String.format("Patient with id %d not found", patientId));
                    } else {
                        log.error("Авторезированный пользователь не является текущим пациентом; patientId:{};", patientId);
                        return new LogicException(String.format("Current user is not patient with id %d", patientId));
                    }
                });

        return UUID.fromString(authentication.getName());
    }

    private Appeal isAppealExistAndPatientOwner(long appealId, long patientId) {
        Optional.ofNullable(appealService.getAppealById(appealId))
                .filter(appeal -> appeal.getPatient().getId() == patientId)
                .orElseThrow(() -> {
                    if (appealService.getAppealById(appealId) == null) {
                        log.error("Заболевание не найдено; appealId:{};", appealId);
                        return new LogicException(String.format("Appeal with id %d not found", appealId));
                    } else {
                        log.error("Авторезированный пользователь не является владельцем заболевания; appealId:{};", appealId);
                        return new LogicException(String.format("Current user is not owner of appeal with id %d", appealId));
                    }
                });
        return appealService.getAppealById(appealId);
    }

}

