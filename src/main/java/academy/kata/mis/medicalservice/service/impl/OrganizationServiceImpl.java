package academy.kata.mis.medicalservice.service.impl;

import academy.kata.mis.medicalservice.repository.OrganizationRepository;
import academy.kata.mis.medicalservice.service.OrganizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrganizationServiceImpl implements OrganizationService {

    private final OrganizationRepository organizationRepository;

    @Override
    public long getOrganizationIdByDoctorId(long id) {
       return organizationRepository.getOrganizationIdByDoctorId(id);
    }
}
