package academy.kata.mis.medicalservice.repository;

import academy.kata.mis.medicalservice.model.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    @Query("""
             SELECT case when count(d) > 0 then true else false END
             from Doctor d
                JOIN DiseaseDep dd on d.department.id = dd.department.id
             where d.id = :doctorId
                and d.userId = :userId
                and dd.id = :diseaseDepId
            """)
    boolean existsByIdAndUserId(long doctorId,
                                UUID userId,
                                long diseaseDepId);
}
