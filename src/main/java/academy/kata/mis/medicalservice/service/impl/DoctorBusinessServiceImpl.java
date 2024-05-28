package academy.kata.mis.medicalservice.service.impl;

import academy.kata.mis.medicalservice.service.DoctorBusinessService;
import academy.kata.mis.medicalservice.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DoctorBusinessServiceImpl implements DoctorBusinessService {

    private final DoctorService doctorService;

    @Override
    public boolean checkDoctorExistAndCurrent(long doctorId, UUID userId, long diseaseDepId) {
        return doctorService.isExistByIdAndUserId(doctorId, userId, diseaseDepId);
    }
}
