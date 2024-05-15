package academy.kata.mis.medicalservice.service;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DiseaseDepBusinessService {
    void checkDiseaseDepExist(long diseaseDepId);


    long getDiseaseDepDepartmentId(long diseaseDepId);

}
