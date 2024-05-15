package academy.kata.mis.medicalservice.service;

import academy.kata.mis.medicalservice.model.entity.DiseaseDep;

public interface DiseaseDepService {

    boolean isExistById(long diseaseDepId);

    long getDiseaseDepDepartmentId(long diseaseDepId);

}
