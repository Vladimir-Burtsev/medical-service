package academy.kata.mis.medicalservice.service.impl;

import academy.kata.mis.medicalservice.model.dto.GetAppealShortInfo;
import academy.kata.mis.medicalservice.model.entity.Appeal;
import academy.kata.mis.medicalservice.model.entity.Doctor;
import academy.kata.mis.medicalservice.repository.AppealRepository;
import academy.kata.mis.medicalservice.service.AppealBusinessService;
import academy.kata.mis.medicalservice.service.AppealService;
import academy.kata.mis.medicalservice.service.VisitService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Slf4j
public class AppealBusinessServiceImpl implements AppealBusinessService {
    private final AppealService appealService;
    private final AppealRepository appealRepository;
    private final VisitService visitService;

@Autowired
    public AppealBusinessServiceImpl(AppealService appealService, AppealRepository appealRepository, VisitService visitService) {
        this.appealService = appealService;
        this.appealRepository = appealRepository;
        this.visitService = visitService;
}
    @Override
    @Transactional
    public GetAppealShortInfo createPatientVisit(Doctor doctor, long diseaseDepId, long patientId) {
        Appeal appeal = appealService.createPatientAppeal(diseaseDepId, patientId);
        appealRepository.save(appeal);
        visitService.createPatientVisit(doctor, appeal);
        log.debug("Создание обращения успешно: doctor={}, patient={}, appeal={}",
                doctor.getId(), patientId, appeal);

        return GetAppealShortInfo.builder()
                .appealId(appeal.getId())
                .appealStatus(appeal.getStatus())
                .patient(null)
                .disease(null)
                .visits(null)
                .build();
    }
}
