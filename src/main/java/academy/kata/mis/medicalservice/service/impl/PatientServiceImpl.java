package academy.kata.mis.medicalservice.service.impl;

import academy.kata.mis.medicalservice.model.entity.Patient;
import academy.kata.mis.medicalservice.repository.PatientRepository;
import academy.kata.mis.medicalservice.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {
    private final PatientRepository patientRepository;

    @Override
    public List<Patient> findAllByUserId(UUID userId) {
        return patientRepository.findAllByUserId(userId);
    }
}
