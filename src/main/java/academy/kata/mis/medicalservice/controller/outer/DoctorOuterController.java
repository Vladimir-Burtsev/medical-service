package academy.kata.mis.medicalservice.controller.outer;

import academy.kata.mis.medicalservice.model.dto.GetCurrentDoctorPersonalInfoResponse;
import academy.kata.mis.medicalservice.model.dto.GetDoctorPersonalInfoResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@Slf4j
@PreAuthorize("hasAuthority('DOCTOR')")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/medical/doctor")
public class DoctorOuterController {

    @GetMapping
    public ResponseEntity<GetDoctorPersonalInfoResponse> getCurrentDoctorInformation(Principal principal) {

        //todo
        // вернуть всех докторов( + заведующих и главных) которыми является авторизованный пользователь

        return ResponseEntity.ok(null);
    }

    @GetMapping("/current")
    public ResponseEntity<GetCurrentDoctorPersonalInfoResponse> getCurrentDoctorInfo(
            @RequestParam(name = "doctor_id") long doctorId) {
        //todo
        // проверить что доктор существует
        // проверить что текущий авторизованный доктор соответствует авторизованному пользователю

        return ResponseEntity.ok(null);
    }
}
