package academy.kata.mis.medicalservice.service.impl;

import academy.kata.mis.medicalservice.model.entity.Appeal;
import academy.kata.mis.medicalservice.model.entity.DiseaseDep;
import academy.kata.mis.medicalservice.model.entity.Patient;
import academy.kata.mis.medicalservice.model.enums.AppealStatus;
import academy.kata.mis.medicalservice.model.enums.InsuranceType;
import academy.kata.mis.medicalservice.repository.AppealRepository;
import academy.kata.mis.medicalservice.service.AppealService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class AppealServiceImpl implements AppealService {
    private final AppealRepository appealRepository;

    @Override
    public Appeal getAppealById(long appealId) {
        return appealRepository.getAppealById(appealId);
    }

    @Override
    public Appeal createPatientAppeal(DiseaseDep diseaseDep, Patient patient, InsuranceType insuranceType) {
        return Appeal.builder()
                .status(AppealStatus.OPEN)
                .insuranceType(insuranceType)
                .openDate(LocalDate.now())
                .patient(patient)
                .diseaseDep(diseaseDep)
                .build();
    }

    @Override
    @Transactional
    public Appeal save(Appeal appeal) {
        return appealRepository.save(appeal);
    }
}
