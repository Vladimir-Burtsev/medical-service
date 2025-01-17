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
        String operation = "Получение информации о докторах, которыми является авторизованный пользователь";
        log.info("{}; principal {}", operation, principal);

        GetDoctorPersonalInfoResponse response =
                doctorBusinessService.getDoctorInformationByUser(UUID.fromString(principal.getName()));

        log.debug("{}; Успешное получение информации о докторе; principal {}; {}", operation, principal, response);

        auditMessageService.sendAudit(
                principal.getName(), operation, "успешное получение информации о докторах");

        return ResponseEntity.ok(response);
    }

    @GetMapping("/current")
    public ResponseEntity<GetCurrentDoctorPersonalInfoResponse> getCurrentDoctorInfo(
            @RequestParam(name = "doctor_id") long doctorId, Principal principal) {

        UUID authUserId = UUID.fromString(principal.getName());
        String operation = "DoctorOuterController.getCurrentDoctorInfo()";
        log.info("{}: doctorId = {}, userId = {}", operation, doctorId, authUserId);

        if (!doctorBusinessService.isDoctorExistsById(doctorId)) {
            log.error("{}: Ошибка! Доктор с doctorId = {} не существует.", operation, doctorId);
            throw new LogicException("Доктор не найден!");
        }

        if (!doctorBusinessService.existDoctorByUserIdAndDoctorId(authUserId, doctorId)) {
            log.error("Ошибка в {}. Доктор с doctorId = {} и userId = {}  не является авторизованным.",
                    operation, doctorId, authUserId);
            throw new AuthException("Доктор не авторизован!");
        }

        GetCurrentDoctorPersonalInfoResponse response =
                doctorBusinessService.getCurrentDoctorPersonalInfoById(doctorId);

        log.debug("Успешно! medical-service {}: doctorId = {}, userId = {}; Response = {}",
                operation, doctorId, authUserId, response);

        return ResponseEntity.ok(response);
    }
}
