package academy.kata.mis.medicalservice.repository;

import academy.kata.mis.medicalservice.model.entity.Appeal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AppealRepository extends JpaRepository<Appeal, Long> {
    Appeal getAppealById(long appealId);
    @Query("SELECT CASE WHEN COUNT(a) > 0 THEN TRUE ELSE FALSE END FROM Appeal a JOIN a.visits v WHERE v.id = :visitId")
    boolean existsAppealByVisitId(@Param("visitId") Long visitId);
}

