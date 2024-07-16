package academy.kata.mis.medicalservice.controller.outer;

import academy.kata.mis.medicalservice.model.dto.UpdateVisitRequest;
import academy.kata.mis.medicalservice.model.dto.doctor.convertor.DoctorConvertor;
import academy.kata.mis.medicalservice.model.dto.service.convertor.MedicalServiceConverter;
import academy.kata.mis.medicalservice.model.dto.visit.VisitDto;
import academy.kata.mis.medicalservice.model.dto.visit.convertor.VisitConvertor;
import academy.kata.mis.medicalservice.model.entity.Appeal;
import academy.kata.mis.medicalservice.model.entity.Doctor;
import academy.kata.mis.medicalservice.model.entity.Visit;
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
    private final AppealService appealService;
    private final DoctorService doctorService;
    private final VisitService visitService;
    private final VisitConvertor visitConvertor;
    private final DoctorConvertor doctorConvertor;
    private final MedicalServiceConverter medicalServiceConverter;


    /**
     * страница 3.2.4
     */
    @GetMapping("/info")
    public ResponseEntity<VisitDto> getVisitInfo(
            @RequestParam(name = "visit_id") long visitId, Principal principal) {
        // проверить что посещение существует
        Appeal appeal = appealService.getAppealById(visitId);
        if (appeal == null) {
            return ResponseEntity.status(HttpStatus.NOT_EXTENDED).body(null);
        }
        // проверить что доктор и посещение из одного отделения
        String doctor = principal.getName();
        Doctor currentDoctor = doctorService.findDoctorByUUID(UUID.fromString(doctor)); //текущий доктор
        if (currentDoctor == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
        Visit visit = visitService.findVisitById(visitId);
        Doctor visitDoctor = visit.getDoctor(); // доктор проводящий приём в прошлый раз
        if (!currentDoctor.getDepartment().equals(visitDoctor.getDepartment())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
        // вернуть информацию о оказанных услугах
        VisitDto visitDto = visitConvertor.entityToVisitDto(visit,
                doctorConvertor.convertDoctorToDoctorShortDto(visitDoctor),
                medicalServiceConverter.convertMedicalServiceToMedicalServiceShortDto(visit.getMedicalServicesDep()));

        // первая задача - вернуть дто с инфой только из этого микросервиса
        return ResponseEntity.ok(visitDto);
        // ====
        // вторая задача - через фейгн клиенты собрать доп инфу из других микросервисов
    }

    //

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
