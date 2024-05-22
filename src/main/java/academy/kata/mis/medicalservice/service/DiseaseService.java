package academy.kata.mis.medicalservice.service;

import academy.kata.mis.medicalservice.model.entity.Disease;
import academy.kata.mis.medicalservice.model.entity.DiseaseDep;

public interface DiseaseService {
    Disease getById(long diseaseId);
}
