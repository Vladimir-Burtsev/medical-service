package academy.kata.mis.medicalservice.controller.outer;

import academy.kata.mis.medicalservice.model.dto.GetCurrentDoctorPersonalInfoResponse;
import academy.kata.mis.medicalservice.model.dto.GetCurrentRegistrarInfoResponse;
import academy.kata.mis.medicalservice.model.dto.GetDoctorPersonalInfoResponse;
import academy.kata.mis.medicalservice.model.dto.GetRegistrarPersonalInfoResponse;
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
@PreAuthorize("hasAuthority('REGISTARAR')")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/medical/registrar")
public class RegistrarOuterController {

    @GetMapping
    public ResponseEntity<GetRegistrarPersonalInfoResponse> getCurrentRegistrarInformation(Principal principal) {

        //todo
        // вернуть всех медрегистраторов которыми является пользователь

        return ResponseEntity.ok(null);
    }

    @GetMapping("/current")
    public ResponseEntity<GetCurrentRegistrarInfoResponse> getCurrentDoctorInfo(
            @RequestParam(name = "registrar_id") long registrarId) {
        //todo
        // проверить что регистратор существует
        // проверить что текущий авторизованный пользователь соответствует переданному регистратору

        return ResponseEntity.ok(null);
    }
}
