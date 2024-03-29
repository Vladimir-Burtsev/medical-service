package academy.kata.mis.medicalservice.service.impl;

import academy.kata.mis.medicalservice.feign.PersonFeignClient;
import academy.kata.mis.medicalservice.feign.StructureFeignClient;
import academy.kata.mis.medicalservice.model.dto.GetCurrentPatientPersonalInformation;
import academy.kata.mis.medicalservice.model.dto.PatientPersonalInformation;
import academy.kata.mis.medicalservice.model.dto.feign.OrganizationDto;
import academy.kata.mis.medicalservice.model.dto.feign.PersonDto;
import academy.kata.mis.medicalservice.model.dto.patient.PatientDto;
import academy.kata.mis.medicalservice.model.dto.patient.convertor.PatientConvertor;
import academy.kata.mis.medicalservice.model.entity.Patient;
import academy.kata.mis.medicalservice.service.PatientBusinessService;
import academy.kata.mis.medicalservice.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PatientBusinessServiceImpl implements PatientBusinessService {
    private final PatientService patientService;
    private final PersonFeignClient personFeignClient;
    private final StructureFeignClient structureFeignClient;
    private final PatientConvertor patientConvertor;

    @Override
    public GetCurrentPatientPersonalInformation getPatientPersonalInformationByUser(UUID userId) {
        var patients = patientService.findAllByUserId(userId);
        //todo не эффективно - много лишних запросов
        long personId = patients.stream()
                .findFirst()
                .get()
                .getPersonId();
        PersonDto personDto = personFeignClient.getPersonById(personId);

        return GetCurrentPatientPersonalInformation.builder()
                .userId(userId)
                .person(personDto)
                .patients(createPatients(patients))
                .build();
    }

    @Override
    public List<PatientDto> findPatientInformationByUserId(UUID userId) {
        return patientService.findAllByUserId(userId).stream().
                map(patientConvertor::entityToPatientDto).collect(Collectors.toList());
    }

    private List<PatientPersonalInformation> createPatients(List<Patient> patients) {
        return patients.stream()
                .map(this::create)
                .toList();
    }

    private PatientPersonalInformation create(Patient patient) {
        return PatientPersonalInformation.builder()
                .patientId(patient.getId())
                .organization(createOrganization(patient.getOrganization().getId()))
                .build();
    }

    private OrganizationDto createOrganization(long organizationId) {
        return structureFeignClient.getOrganizationById(organizationId);
    }
}
