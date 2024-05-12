package academy.kata.mis.medicalservice.repository;

import academy.kata.mis.medicalservice.model.entity.Appeal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppealRepository extends JpaRepository<Appeal, Long> {
    Appeal getAppealById(long appealId);
}
