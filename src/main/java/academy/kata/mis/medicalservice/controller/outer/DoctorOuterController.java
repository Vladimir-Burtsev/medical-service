package academy.kata.mis.medicalservice.controller.outer;

import academy.kata.mis.medicalservice.model.dto.GetCurrentDoctorPersonalInfoResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@Slf4j
@PreAuthorize("hasAuthority('DOCTOR')")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/medical/doctor")
public class DoctorOuterController {

    @GetMapping
    public ResponseEntity<GetCurrentDoctorPersonalInfoResponse> getCurrentDoctorInformation(Principal principal) {

        //todo

        return ResponseEntity.ok(null);
    }
}
