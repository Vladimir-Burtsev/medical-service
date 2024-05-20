package academy.kata.mis.medicalservice.service.impl;

import academy.kata.mis.medicalservice.exceptions.LogicException;
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

import java.security.Principal;
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
    public UUID isPatientExistAndAuthenticatedUserPatient(long patientId, Principal principal) {
        String userId = patientService.findUserIdById(patientId)
                .orElseThrow(() -> {
                    log.error("Пациент не найден; patientId:{};", patientId);
                    return new LogicException(String.format("Patient with id %d not found", patientId));
                });
        if (!userId.equals(principal.getName())) {
            log.error("Авторизованный пользователь не является текущим пациентом; patientId:{};", patientId);
            throw new LogicException(String.format("Current user is not patient with id %d", patientId));
        }
        return UUID.fromString(userId);
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
