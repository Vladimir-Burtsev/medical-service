package academy.kata.mis.medicalservice.service.impl;

import academy.kata.mis.medicalservice.model.entity.Doctor;
import academy.kata.mis.medicalservice.repository.DoctorRepository;
import academy.kata.mis.medicalservice.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {
    private final DoctorRepository doctorRepository;

    @Override
    public Doctor findDoctorById(long doctorId) {
        return doctorRepository.getReferenceById(doctorId);
    }
}
