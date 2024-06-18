package academy.kata.mis.medicalservice.repository;

import academy.kata.mis.medicalservice.model.entity.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrganizationRepository extends JpaRepository<Organization, Long> {

    //novikov
    @Query("""
            SELECT d.department.organization.id FROM Doctor d WHERE d.id = :id
            """)
    long getOrganizationIdByDoctorId(long id);
}
