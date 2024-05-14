package academy.kata.mis.medicalservice.model.dto.talon.converter;

import academy.kata.mis.medicalservice.model.dto.talon.TalonWithDoctorShortDto;
import academy.kata.mis.medicalservice.model.entity.Talon;
import org.springframework.stereotype.Component;

@Component
public class TalonConverter {

    public TalonWithDoctorShortDto entityToTalonWithDoctorShortDto(Talon talon) {
        return TalonWithDoctorShortDto.builder()
                .talonId(talon.getId())
                .visitTime(talon.getTime())
                .doctorId(talon.getDoctor().getId())
                .doctorFirstName(null)
                .doctorLastName(null)
                .doctorPositionName(null)
                .doctorPatronymic(null)
                .cabinet(null)
                .build();
    }
}
