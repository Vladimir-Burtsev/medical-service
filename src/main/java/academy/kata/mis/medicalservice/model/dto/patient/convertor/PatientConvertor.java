package academy.kata.mis.medicalservice.model.dto.patient.convertor;

import academy.kata.mis.medicalservice.model.dto.GetCurrentPatientInformation;
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

    public PatientShortDto currentPatientToPatientShortDto(GetCurrentPatientInformation currentPatient) {
        return PatientShortDto.builder()
                .patientId(currentPatient.id())
                .patientFirstName(currentPatient.firstName())
                .patientLastname(currentPatient.lastName())
                .patientPatronymic(currentPatient.patronymic())
                .birthday(currentPatient.birthday())
                .build();
    }
}
