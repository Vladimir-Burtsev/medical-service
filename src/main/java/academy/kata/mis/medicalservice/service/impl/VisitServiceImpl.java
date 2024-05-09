package academy.kata.mis.medicalservice.service.impl;

import academy.kata.mis.medicalservice.model.entity.Appeal;
import academy.kata.mis.medicalservice.model.entity.Doctor;
import academy.kata.mis.medicalservice.model.entity.Visit;
import academy.kata.mis.medicalservice.repository.VisitRepository;
import academy.kata.mis.medicalservice.service.VisitService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
@Service
public class VisitServiceImpl implements VisitService {

    private final VisitRepository visitRepository;

    public VisitServiceImpl(VisitRepository visitRepository) {
        this.visitRepository = visitRepository;
    }

    @Override
    @Transactional
    public void createPatientVisit(Doctor doctor, Appeal appeal) {
        Visit visit = Visit.builder()
                .visitTime(LocalDate.now().atStartOfDay())
                .doctor(doctor)
                .appeal(appeal)
                .build();
        visitRepository.save(visit);
    }
}
