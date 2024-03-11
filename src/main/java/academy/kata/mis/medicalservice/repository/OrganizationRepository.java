package academy.kata.mis.medicalservice.repository;

import academy.kata.mis.medicalservice.model.entity.Organization;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrganizationRepository extends JpaRepository<Organization, Long> {
}
