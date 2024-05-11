package academy.kata.mis.medicalservice.controller.outer;

import academy.kata.mis.medicalservice.model.dto.UpdateVisitRequest;
import academy.kata.mis.medicalservice.model.dto.visit.VisitDto;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@PreAuthorize("hasAnyAuthority('DOCTOR', 'CHIEF_DOCTOR', 'DIRECTOR')")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/medical/doctor/visit")
public class DoctorVisitRestController {

    /**
     * страница 3.2.4
     */
    @GetMapping("/info")
    public ResponseEntity<VisitDto> getVisitInfo(
            @RequestParam(name = "visit_id") long visitId) {
        // проверить что посещение существует
        // вернуть информацию о оказанных услугах

        return ResponseEntity.ok(null);
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
