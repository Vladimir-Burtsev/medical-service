package academy.kata.mis.medicalservice.controller.outer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@PreAuthorize("hasAuthority('DOCTOR')")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/medical/doctor/talon")
public class DoctorTalonOuterController {
}
