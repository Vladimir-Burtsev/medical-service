package academy.kata.mis.medicalservice.service.impl;

import academy.kata.mis.medicalservice.model.entity.Appeal;
import academy.kata.mis.medicalservice.model.enums.AppealStatus;
import academy.kata.mis.medicalservice.model.enums.InsuranceType;
import academy.kata.mis.medicalservice.repository.DiseaseDepRepository;
import academy.kata.mis.medicalservice.service.AppealService;
import academy.kata.mis.medicalservice.service.PatientService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
@Service
public class AppealServiceImpl implements AppealService {

    private final DiseaseDepRepository diseaseDepRepository;

    private final PatientService patientService;

    public AppealServiceImpl(DiseaseDepRepository diseaseDepRepository, PatientService patientService) {
        this.diseaseDepRepository = diseaseDepRepository;
        this.patientService = patientService;
    }

    @Override
    public Appeal createPatientAppeal(Long diseaseDepId, Long patientId) {
        return Appeal.builder()
                .status(AppealStatus.OPEN)
                .insuranceType(InsuranceType.DMS)
                .openDate(LocalDate.now())
                .patient(patientService.getPatientById(patientId))
                .diseaseDep(diseaseDepRepository.getReferenceById(diseaseDepId))
                .build();
    }
}
