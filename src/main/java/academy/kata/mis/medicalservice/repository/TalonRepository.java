package academy.kata.mis.medicalservice.repository;

import academy.kata.mis.medicalservice.model.entity.Talon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TalonRepository extends JpaRepository<Talon, Long> {
}
