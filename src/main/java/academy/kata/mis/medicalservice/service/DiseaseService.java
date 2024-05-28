package academy.kata.mis.medicalservice.service;

import academy.kata.mis.medicalservice.model.entity.Disease;

public interface DiseaseService {
    Disease getById(long diseaseId);

    long getDiseaseByDiseaseDepId(long diseaseDepId);
}
