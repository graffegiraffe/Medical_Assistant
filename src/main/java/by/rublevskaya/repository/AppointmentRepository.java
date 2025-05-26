package by.rublevskaya.repository;

import by.rublevskaya.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByDoctorId(Long doctorId);
    List<Appointment> findByDateTimeBefore(LocalDateTime dateTime);
    List<Appointment> findByUserId(Long userId);
}