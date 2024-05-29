package academy.kata.mis.medicalservice.controller.internal;

import academy.kata.mis.medicalservice.model.entity.Talon;
import academy.kata.mis.medicalservice.service.TalonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/internal/medical/talons")
public class TalonsInternalController {
    private final TalonService talonService;

    @GetMapping
    public ResponseEntity<List<Talon>> findAllByTomorrow() {
        log.info("findAllByTomorrow");

        List<Talon> response = talonService.getAllByTomorrow();

        log.debug("findAllByTomorrow; response: {}", response);

        return ResponseEntity.ok(response);
    }

}
