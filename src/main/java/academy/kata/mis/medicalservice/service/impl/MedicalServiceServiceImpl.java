package academy.kata.mis.medicalservice.service.impl;

import academy.kata.mis.medicalservice.model.entity.MedicalService;
import academy.kata.mis.medicalservice.repository.MedicalServiceRepository;
import academy.kata.mis.medicalservice.service.MedicalServiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class MedicalServiceServiceImpl implements MedicalServiceService {

    private final MedicalServiceRepository medicalServiceRepository;

    public Set<MedicalService> getMedicalServiceByServicesDepId(Set<Long> servicesDepId) {
        return medicalServiceRepository.getMedicalServiceByServicesDepId(servicesDepId);
    }
}
