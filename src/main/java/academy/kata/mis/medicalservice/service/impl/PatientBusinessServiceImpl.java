package academy.kata.mis.medicalservice.service.impl;

import academy.kata.mis.medicalservice.feign.PersonFeignClient;
import academy.kata.mis.medicalservice.feign.StructureFeignClient;
import academy.kata.mis.medicalservice.model.dto.GetCurrentPatientPersonalInfoResponse;
import academy.kata.mis.medicalservice.model.dto.feign.OrganizationDto;
import academy.kata.mis.medicalservice.model.dto.feign.PersonDto;
import academy.kata.mis.medicalservice.model.dto.patient.PatientPersonalInformation;
import academy.kata.mis.medicalservice.model.entity.Patient;
import academy.kata.mis.medicalservice.service.PatientBusinessService;
import academy.kata.mis.medicalservice.service.PatientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class PatientBusinessServiceImpl implements PatientBusinessService {
    private final PatientService patientService;
    private final PersonFeignClient personFeignClient;
    private final StructureFeignClient structureFeignClient;

    @Override
    public GetCurrentPatientPersonalInfoResponse getPatientPersonalInformationByUser(UUID userId) {
        var patients = patientService.findAllByUserId(userId);
        //todo не эффективно - много лишних запросов
        long personId = patients.stream()
                .findFirst()
                .get()
                .getPersonId();
        PersonDto personDto = personFeignClient.getPersonById(personId);

        return GetCurrentPatientPersonalInfoResponse.builder()
                .userId(userId)
                .person(personDto)
                .patients(createPatients(patients))
                .build();
    }

    @Override
    public boolean isPatientExistsAndFromSameOrganizationAsDoctor(long patientId, long doctorId) {
        return patientService.isPatientExistsAndFromSameOrganizationAsDoctor(patientId, doctorId);
    }

    @Override
    public boolean isPatientExistAndAuthenticatedUserPatient(long patientId, UUID userId) {
        return patientService.isPatientExistAndUserIdIsPatientUserId(patientId, userId);
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

    public UUID getUserId(long patientId) {
        return patientService.getPatientUserIdByPatientId(patientId);
    }
}
