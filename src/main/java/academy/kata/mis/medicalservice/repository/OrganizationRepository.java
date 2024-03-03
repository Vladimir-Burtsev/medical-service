package academy.kata.mis.medicalservice.repository;

import academy.kata.mis.medicalservice.model.Organization;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrganizationRepository extends JpaRepository<Organization, Long> {
}
