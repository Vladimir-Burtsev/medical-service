package academy.kata.mis.medicalservice.controller.outer;

import academy.kata.mis.medicalservice.model.dto.UpdateVisitRequest;
import academy.kata.mis.medicalservice.model.dto.visit.VisitDto;
import academy.kata.mis.medicalservice.service.*;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.UUID;

@Slf4j
@PreAuthorize("hasAnyAuthority('DOCTOR', 'CHIEF_DOCTOR', 'DIRECTOR')")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/medical/doctor/visit")
public class DoctorVisitRestController {
    private final VisitBusinessService visitBusinessService;
    private final VisitService visitService;
    private final DoctorBusinessService doctorBusinessService;
    /**
     * страница 3.2.4
     */

    //проверить что appeal существует
    //проверить что appeal и doctor из одного departament
    //получить информацию об оказанных medicalServiceDep
    //вернуть dto c информацией только из этого микросервиса
    @GetMapping("/info")
    public ResponseEntity<VisitDto> getVisitInfo(
            @RequestParam(name = "visit_id") long visitId, Principal principal) {
        if (visitService.existsVisitById(visitId)) {
            log.debug("Visit with visit_id {} already exists", visitId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        UUID doctorUUID = UUID.fromString(principal.getName());

        if(!doctorBusinessService.areDoctorsInSameDepartment(visitId, doctorUUID)) {
            log.debug("Doctor with UUID {} and visit with ID {} are not in the same department.", doctorUUID, visitId);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        VisitDto visitDto = visitBusinessService.getVisitInfo(visitId);
        return ResponseEntity.ok(visitDto);
        // ====
        // вторая задача - через фейгн клиенты собрать доп инфу из других микросервисов
    }

    /**
     * страница 3.2.4
     */
    @PatchMapping("/update")
    public ResponseEntity<VisitDto> updateVisit(
            @RequestBody @NotNull UpdateVisitRequest request) {
        // проверить что посещение существует
        // проверить что доктор существует и авторизованный пользователь это он
        // проверить что посещение принадлежит доктору
        // проверить что обращение к которому относится посещение не имеет статус BLOCKED
        // проверить что услуги есть и могут быть оказаны
        // отправить аудит

        return ResponseEntity.ok(null);
    }
}
