package academy.kata.mis.medicalservice.service.impl;

import academy.kata.mis.medicalservice.model.entity.Appeal;
import academy.kata.mis.medicalservice.model.entity.Doctor;
import academy.kata.mis.medicalservice.model.entity.Visit;
import academy.kata.mis.medicalservice.repository.VisitRepository;
import academy.kata.mis.medicalservice.service.VisitService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class VisitServiceImpl implements VisitService {
    private final VisitRepository visitRepository;

    @Override
    public Visit createPatientVisit(Doctor doctor, Appeal appeal) {
        return Visit.builder()
                .visitTime(LocalDate.now().atStartOfDay())
                .doctor(doctor)
                .appeal(appeal)
                .build();
    }

    @Override
    @Transactional
    public Visit save(Visit visit) {
        return visitRepository.save(visit);
    }

    @Override
    public Visit findVisitById(long id) {
        return visitRepository.findById(id).orElse(null);
    }
}
