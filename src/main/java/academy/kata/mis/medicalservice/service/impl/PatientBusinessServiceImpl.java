package academy.kata.mis.medicalservice.service.impl;

import academy.kata.mis.medicalservice.exceptions.AuthException;
import academy.kata.mis.medicalservice.feign.PersonFeignClient;
import academy.kata.mis.medicalservice.feign.StructureFeignClient;
import academy.kata.mis.medicalservice.model.dto.GetCurrentPatientPersonalInfoResponse;
import academy.kata.mis.medicalservice.model.dto.auth.JwtAuthentication;
import academy.kata.mis.medicalservice.model.dto.feign.OrganizationDto;
import academy.kata.mis.medicalservice.model.dto.feign.PersonDto;
import academy.kata.mis.medicalservice.model.dto.patient.PatientPersonalInformation;
import academy.kata.mis.medicalservice.model.entity.Patient;
import academy.kata.mis.medicalservice.service.PatientBusinessService;
import academy.kata.mis.medicalservice.service.PatientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;

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

    @Override
    public String getPatientUserIdIfExist(long patientId) {
        return patientService.getPatientUserIdByPatientId(patientId)
                .orElseThrow(() -> {
                    log.error("Пациент не найден: PatientId: {}", patientId);
                    return new NoSuchElementException("Patient with id: " + patientId + " does not exist");
                });
    }

    @Override
    public void checkPatientIsAutUser(String userId) {
        UUID authUserId = ((JwtAuthentication) SecurityContextHolder.getContext().getAuthentication())
                .getUserId();
        if (!userId.equals(authUserId.toString())) {
            log.error("У авторизованного пользователя с Id: {} нет доступа к данным о пациенте", userId);
            throw new AuthException("User with id: " + authUserId + " does not have access");
        }
    }
}
