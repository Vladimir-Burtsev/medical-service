package academy.kata.mis.medicalservice.controller.outer;

import academy.kata.mis.medicalservice.exceptions.AuthException;
import academy.kata.mis.medicalservice.exceptions.LogicException;
import academy.kata.mis.medicalservice.model.dto.GetCurrentDoctorPersonalInfoResponse;
import academy.kata.mis.medicalservice.model.dto.GetDoctorPersonalInfoResponse;
import academy.kata.mis.medicalservice.service.AuditMessageService;
import academy.kata.mis.medicalservice.service.DoctorBusinessService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
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
@RequestMapping("/api/medical/doctor")
public class DoctorOuterController {
    private final DoctorBusinessService doctorBusinessService;
    private final AuditMessageService auditMessageService;

    @GetMapping
    public ResponseEntity<GetDoctorPersonalInfoResponse> getCurrentDoctorInformation(Principal principal) {
        // вернуть всех докторов которыми является авторизованный пользователь

        return ResponseEntity.ok(null);
    }

    @GetMapping("/current")
    public ResponseEntity<GetCurrentDoctorPersonalInfoResponse> getCurrentDoctorInfo(
            @RequestParam(name = "doctor_id") long doctorId, Principal principal) {
        // проверить что доктор существует
        String operation = " Получение информации о докторе по его doctor_id";
        log.info("{}: {}", operation, doctorId);

        if (!doctorBusinessService.isDoctorExistsById(doctorId)) {
            log.error("{} Ошибка! Доктор с doctorId = {} не существует.", operation, doctorId);
            throw new LogicException("Доктор не найден!");
        }

        // проверить что текущий авторизованный доктор соответствует авторизованному пользователю
        UUID authUserId = UUID.fromString(principal.getName());
        if(!doctorBusinessService.existDoctorByUserIdAndDoctorId(authUserId, doctorId)){
            log.error("{} Ошибка! Доктор с doctorId = {} и userId = {}  не является авторизованным.",
                    operation, doctorId, authUserId);
            throw new AuthException("Доктор не авторизован!");
        }

        //первая часть - получить инфу из текущего МС. Все чего нет - просисать null
        GetCurrentDoctorPersonalInfoResponse response =
                doctorBusinessService.getCurrentDoctorPersonalInfoById(doctorId);


        //вторая часть - сходить во все микросервисы и получить недостающие чвсти и заменить null на данные
        return ResponseEntity.ok(response);
    }
}
