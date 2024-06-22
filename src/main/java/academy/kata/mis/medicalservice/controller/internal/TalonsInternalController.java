package academy.kata.mis.medicalservice.controller.internal;

import academy.kata.mis.medicalservice.model.GetTalonsTomorrow;
import academy.kata.mis.medicalservice.service.TalonBusinessService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/internal/medical/talons")
public class TalonsInternalController {
    private final TalonBusinessService talonBusinessService;

    @GetMapping
    public ResponseEntity<GetTalonsTomorrow> findAllByTomorrow() {
        log.info("findAllByTomorrow");

        GetTalonsTomorrow response = talonBusinessService.getAllByTomorrow();

        log.debug("findAllByTomorrow; response: {}", response);

        return ResponseEntity.ok(response);
    }

}
