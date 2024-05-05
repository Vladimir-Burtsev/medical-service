package academy.kata.mis.medicalservice.service.impl;

import academy.kata.mis.medicalservice.model.entity.Appeal;
import academy.kata.mis.medicalservice.model.entity.Doctor;
import academy.kata.mis.medicalservice.repository.AppealRepository;
import academy.kata.mis.medicalservice.service.AppealBusinessService;
import academy.kata.mis.medicalservice.service.AppealService;
import academy.kata.mis.medicalservice.service.VisitService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AppealBusinessServiceImpl implements AppealBusinessService {
    AppealService appealService;
    AppealRepository appealRepository;
    VisitService visitService;

@Autowired
    public AppealBusinessServiceImpl(AppealService appealService, AppealRepository appealRepository, VisitService visitService) {
        this.appealService = appealService;
        this.appealRepository = appealRepository;
        this.visitService = visitService;
    }
    @Override
    public void createPatientVisit(Doctor doctor, long diseaseDepId, long patientId) {
        // вынести в appealBusinessService с 83 по 85 - одна транзакция,
        // переедет из доктор сервиса в визит сервис createPatientVisit
        Appeal appeal = appealService.createPatientAppeal(diseaseDepId, patientId);
        appealRepository.save(appeal);
        visitService.createPatientVisit(doctor, appeal);
        log.debug("Создание обращения успешно: {}", appeal);
    }
}
