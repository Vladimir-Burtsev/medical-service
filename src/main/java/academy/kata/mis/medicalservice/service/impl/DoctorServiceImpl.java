package academy.kata.mis.medicalservice.service.impl;

import academy.kata.mis.medicalservice.repository.DoctorRepository;
import academy.kata.mis.medicalservice.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {
    private final DoctorRepository doctorRepository;

    @Override
    public boolean isExistByIdAndUserId(long doctorId, UUID userId, long diseaseDepId) {
        return doctorRepository.existsByIdAndUserId(doctorId, userId, diseaseDepId);
    }
}
