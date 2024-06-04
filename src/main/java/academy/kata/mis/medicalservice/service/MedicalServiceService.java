package academy.kata.mis.medicalservice.service;

import academy.kata.mis.medicalservice.model.entity.MedicalService;

import java.util.Set;

public interface MedicalServiceService {

    Set<MedicalService> getMedicalServiceByServicesDepId(Set<Long> servicesDepId);
}
