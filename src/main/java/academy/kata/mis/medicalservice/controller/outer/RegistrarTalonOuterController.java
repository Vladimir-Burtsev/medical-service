package academy.kata.mis.medicalservice.controller.outer;

import academy.kata.mis.medicalservice.model.dto.CurrentDayTalonsByDepartmentResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@PreAuthorize("hasAuthority('REGISTARAR')")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/medical/registrar/talon")
public class RegistrarTalonOuterController {

    @GetMapping("/today")
    public ResponseEntity<CurrentDayTalonsByDepartmentResponse> getCurrentDayTalonsByDepartmentInfo(
            @RequestParam(name = "registrar_id") long registrarId) {
        //todo
        // проверить что регистратор существует
        // проверить что текущий авторизованный пользователь соответствует переданному регистратору
        // вернуть информацию о талонах на прием к врачам из отделения регистратора в текущий день

        return ResponseEntity.ok(null);
    }
}
