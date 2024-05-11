package academy.kata.mis.medicalservice.service;

import academy.kata.mis.medicalservice.model.entity.DiseaseDep;

public interface DiseaseDepService {

    DiseaseDep findDiseaseDepById(long diseaseDepId);

    boolean isExistById(long diseaseDepId);
}
