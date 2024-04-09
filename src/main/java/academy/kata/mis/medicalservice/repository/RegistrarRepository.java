package academy.kata.mis.medicalservice.repository;

import academy.kata.mis.medicalservice.model.entity.Registrar;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegistrarRepository extends JpaRepository<Registrar, Long> {
}
