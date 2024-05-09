package academy.kata.mis.medicalservice.service;

public interface DiseaseDepBusinessService {
    boolean checkIsExistByIdAndDoctorId(long diseaseDepId, long doctorId);
}
