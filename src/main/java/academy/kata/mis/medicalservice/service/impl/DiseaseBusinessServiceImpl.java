package academy.kata.mis.medicalservice.service.impl;

import academy.kata.mis.medicalservice.model.dto.GetDiseaseDepShortInfoResponse;
import academy.kata.mis.medicalservice.service.DepartmentService;
import academy.kata.mis.medicalservice.service.DiseaseBusinessService;
import academy.kata.mis.medicalservice.service.DiseaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DiseaseBusinessServiceImpl implements DiseaseBusinessService {
    private final DiseaseService diseaseService;
    private final DepartmentService departmentService;

    @Override
    public String getDiseaseIdentifier(long diseaseDepId) {
        long diseaseId = diseaseService.getDiseaseByDiseaseDepId(diseaseDepId);
        return diseaseService.getById(diseaseId).getIdentifier();
    }

    public GetDiseaseDepShortInfoResponse getDiseaseDepShortInfoResponse(Long doctorId) {
        Long departmentId = departmentService.getDepartmentIdByDoctorId(doctorId);
//        diseaseService.getDiseaseShortInfoPagination();
        return null;
    }
}
