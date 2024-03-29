package academy.kata.mis.medicalservice.model.dto.patient.convertor;

import academy.kata.mis.medicalservice.model.dto.patient.PatientDto;
import academy.kata.mis.medicalservice.model.entity.Patient;
import org.springframework.stereotype.Component;

@Component
public class PatientConvertor {
    public PatientDto entityToPatientDto(Patient entity) {
        return PatientDto.builder()
                .id(entity.getId())
                .personId(entity.getPersonId())
                .userId(entity.getUserId())
                .organizationId(entity.getOrganization().getId())
                .build();
    }
}