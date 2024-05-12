package academy.kata.mis.medicalservice.model.dto.patient.convertor;

import academy.kata.mis.medicalservice.model.dto.patient.PatientShortDto;
import academy.kata.mis.medicalservice.model.entity.Patient;
import org.springframework.stereotype.Component;

@Component
public class PatientConvertor {
    public PatientShortDto entityToPatientShortDto(Patient patient) {
        return PatientShortDto.builder()
                .patientId(patient.getId())
                .patientFirstName(null)
                .patientLastname(null)
                .patientPatronymic(null)
                .birthday(null)
                .build();
    }
}
