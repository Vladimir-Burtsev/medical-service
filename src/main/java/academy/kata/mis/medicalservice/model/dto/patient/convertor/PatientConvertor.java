package academy.kata.mis.medicalservice.model.dto.patient.convertor;

import academy.kata.mis.medicalservice.model.dto.GetCurrentPatientInformation;
import academy.kata.mis.medicalservice.model.dto.patient.PatientAndPersonIdDto;
import academy.kata.mis.medicalservice.model.dto.patient.PatientShortDto;
import academy.kata.mis.medicalservice.model.dto.person.PersonFullNameBirthdayDto;
import academy.kata.mis.medicalservice.model.entity.Patient;
import org.springframework.stereotype.Component;

@Component
public class PatientConvertor {

    public PatientShortDto currentPatientToPatientShortDto(GetCurrentPatientInformation currentPatient, Long patientId) {
        return PatientShortDto.builder()
                .patientId(patientId)
                .patientFirstName(currentPatient.firstName())
                .patientLastname(currentPatient.lastName())
                .patientPatronymic(currentPatient.patronymic())
                .birthday(currentPatient.birthday())
                .build();
    }

    public PatientShortDto personToPatientShortDto(Long patientId,
                                                   PersonFullNameBirthdayDto person) {
        return PatientShortDto.builder()
                .patientId(patientId)
                .patientFirstName(person.firstName())
                .patientLastname(person.lastName())
                .patientPatronymic(person.patronymic())
                .birthday(person.birthday())
                .build();
    }

    public PatientAndPersonIdDto entityToPatientAndPersonIdDto(Patient patient) {
        return PatientAndPersonIdDto.builder()
                .id(patient.getId())
                .personId(patient.getPersonId())
                .build();
    }
}
