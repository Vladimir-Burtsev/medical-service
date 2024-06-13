package academy.kata.mis.medicalservice.repository;

import academy.kata.mis.medicalservice.model.entity.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;



public interface OrganizationRepository extends JpaRepository<Organization, Long> {


    @Query("""
            SELECT t.organization.id
            FROM Department t
            WHERE t.id = :departmentId
            """)
    Long getOrganizationIdByDepartmentId(@Param("departmentId") Long departmentId);

}
