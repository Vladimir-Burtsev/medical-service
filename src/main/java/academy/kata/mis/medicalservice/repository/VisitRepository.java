package academy.kata.mis.medicalservice.repository;

import academy.kata.mis.medicalservice.model.entity.Visit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VisitRepository extends JpaRepository<Visit, Long> {
}
