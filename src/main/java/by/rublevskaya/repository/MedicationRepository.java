package by.rublevskaya.repository;

import by.rublevskaya.model.Medication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MedicationRepository extends JpaRepository<Medication, Long> {
    List<Medication> findByUserId(Long userId);
    boolean existsByUserIdAndNameAndDosageAndStartDateAndEndDate(
            Long userId, String name, String dosage, LocalDateTime startDate, LocalDateTime endDate);

}