package academy.kata.mis.medicalservice.service.impl;

import academy.kata.mis.medicalservice.repository.DepartmentRepository;
import academy.kata.mis.medicalservice.service.DepartmentService;
import org.springframework.stereotype.Service;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    private DepartmentRepository departmentRepository;

    public DepartmentServiceImpl(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Override
    public long getDepartmentIdByDoctorId(long id) {
        return departmentRepository.getDepartmentIdByDoctorId(id);
    }

    @Override
    public Long getDepartmentIdByTalonId(Long talonId) {
        return departmentRepository.getDepartmentIdByTalonId(talonId);
    }
}
