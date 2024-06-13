package academy.kata.mis.medicalservice.service.impl;


import academy.kata.mis.medicalservice.repository.OrganizationRepository;
import academy.kata.mis.medicalservice.service.OrganizationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrganizationServiceImpl implements OrganizationService {

    private OrganizationRepository organizationRepository;

    @Autowired
    public OrganizationServiceImpl(OrganizationRepository organizationRepository) {
        this.organizationRepository = organizationRepository;
    }

    @Override
    public Long getOrganizationIdByDepartmentId(Long departmentId) {
        return organizationRepository.getOrganizationIdByDepartmentId(departmentId);
    }
}
