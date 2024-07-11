package academy.kata.mis.medicalservice.service.impl;

import academy.kata.mis.medicalservice.exceptions.LogicException;
import academy.kata.mis.medicalservice.feign.PersonFeignClient;
import academy.kata.mis.medicalservice.feign.StructureFeignClient;
import academy.kata.mis.medicalservice.model.dto.GetAppealShortInfo;
import academy.kata.mis.medicalservice.model.dto.GetCurrentPatientInformation;
import academy.kata.mis.medicalservice.model.dto.disease.DiseaseShortInfoDto;
import academy.kata.mis.medicalservice.model.dto.doctor.DoctorShortDto;
import academy.kata.mis.medicalservice.model.dto.doctor.convertor.DoctorConvertor;
import academy.kata.mis.medicalservice.model.dto.patient.convertor.PatientConvertor;
import academy.kata.mis.medicalservice.model.dto.visit.convertor.VisitConvertor;
import academy.kata.mis.medicalservice.model.entity.Appeal;
import academy.kata.mis.medicalservice.model.entity.DiseaseDep;
import academy.kata.mis.medicalservice.model.entity.Doctor;
import academy.kata.mis.medicalservice.model.entity.Patient;
import academy.kata.mis.medicalservice.model.entity.Visit;
import academy.kata.mis.medicalservice.model.enums.InsuranceType;
import academy.kata.mis.medicalservice.service.AppealBusinessService;
import academy.kata.mis.medicalservice.service.AppealService;
import academy.kata.mis.medicalservice.service.DiseaseDepService;
import academy.kata.mis.medicalservice.service.DiseaseService;
import academy.kata.mis.medicalservice.service.PatientService;
import academy.kata.mis.medicalservice.service.VisitService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AppealBusinessServiceImpl implements AppealBusinessService {
    private final AppealService appealService;
    private final VisitService visitService;
    private final PatientService patientService;
    private final DiseaseDepService diseaseDepService;
    private final PatientConvertor patientConvertor;
    private final VisitConvertor visitConvertor;
    private final PersonFeignClient personFeignClient;
    private final StructureFeignClient structureFeignClient;
    private final DiseaseService diseaseService;
    private final DoctorConvertor doctorConvertor;

    @Override
    @Transactional
    public GetAppealShortInfo createPatientVisit(Doctor doctor,
                                                 long diseaseDepId,
                                                 long patientId,
                                                 InsuranceType insuranceType) {
        Patient patient = patientService.getPatientById(patientId);
        DiseaseDep diseaseDep = diseaseDepService.getById(diseaseDepId);
        Appeal appeal = appealService.save(appealService.createPatientAppeal(diseaseDep, patient, insuranceType));
        Visit visit = visitService.save(visitService.createPatientVisit(doctor, appeal));
        GetCurrentPatientInformation currentPatient = personFeignClient.getCurrentPersonById(patient.getPersonId());

        DoctorShortDto doctorShortDto = doctorConvertor.entityToDoctorShortDtoWithPositionName(
                doctor.getId(),
                personFeignClient.getPersonFullNameDtoById(doctor.getPersonId()),
                structureFeignClient.getPositionNameById(doctor.getPositionId()));

        DiseaseShortInfoDto diseaseDepInfo = DiseaseShortInfoDto.builder()
                .diseaseDepId(diseaseDepId)
                .diseaseName(diseaseService.getById(diseaseService.getDiseaseByDiseaseDepId(diseaseDepId)).getName())
                .diseaseIdentifier(diseaseService.getById(diseaseService.getDiseaseByDiseaseDepId(diseaseDepId))
                        .getIdentifier())
                .build();

        return GetAppealShortInfo.builder()
                .appealId(appeal.getId())
                .appealStatus(appeal.getStatus())
                .patient(patientConvertor.currentPatientToPatientShortDto(currentPatient, patientId))
                .disease(diseaseDepInfo)
                .visits(List.of(visitConvertor.entityToVisitShortDto(visit, doctorShortDto)))
                .build();
    }

    @Override
    public Appeal isAppealExistAndPatientOwner(long appealId, long patientId) {
        Appeal appeal = Optional.ofNullable(appealService.getAppealById(appealId))
                .orElseThrow(() -> {
                    log.error("Заболевание не найдено; appealId:{}", appealId);
                    return new LogicException(String.format("Appeal with id %d not found", appealId));
                });
        if (appeal.getPatient().getId() != patientId) {
            log.error("Авторизованный пользователь не является владельцем заболевания; appealId:{}", appealId);
            throw new LogicException(String.format("Current user is not owner of appeal with id %d", appealId));
        }
        return appeal;
    }
}
