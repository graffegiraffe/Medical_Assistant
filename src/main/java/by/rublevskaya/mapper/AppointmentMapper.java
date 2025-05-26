package by.rublevskaya.mapper;

import by.rublevskaya.dto.appointment.AppointmentDto;
import by.rublevskaya.dto.appointment.AppointmentResponseDto;
import by.rublevskaya.model.Appointment;
import by.rublevskaya.model.Doctor;
import by.rublevskaya.repository.DoctorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AppointmentMapper {
    private final DoctorRepository doctorRepository;

    @Autowired
    public AppointmentMapper(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    public Appointment toEntity(AppointmentDto dto) {
        log.info("Mapping AppointmentDto to Appointment entity: {}", dto);
        Appointment appointment = new Appointment();
        appointment.setUserId(dto.getUserId());
        appointment.setLicenseNumber(dto.getLicenseNumber());
        appointment.setDateTime(dto.getDateTime());
        appointment.setNotes(dto.getNotes());
        appointment.setCompleted(false);
        log.info("Mapped Appointment entity: {}", appointment);
        return appointment;
    }

    public AppointmentResponseDto toDto(Appointment appointment) {
        log.info("Mapping Appointment entity to AppointmentResponseDto: {}", appointment);
        AppointmentResponseDto dto = new AppointmentResponseDto();
        dto.setLicenseNumber(appointment.getLicenseNumber());
        dto.setDateTime(appointment.getDateTime());
        dto.setNotes(appointment.getNotes());
        dto.setCompleted(appointment.getCompleted());
        try {
            Doctor doctor = doctorRepository.findByLicenseNumber(appointment.getLicenseNumber())
                    .orElseThrow(() -> new RuntimeException("Doctor with license " + appointment.getLicenseNumber() + " not found"));
            log.info("Found doctor with license {}: {}", appointment.getLicenseNumber(), doctor);

            dto.setFullName(doctor.getFullName());
            dto.setClinicName(doctor.getClinicName());
            dto.setSpecialty(doctor.getSpecialty());
        } catch (RuntimeException e) {
            log.error("Error while finding doctor: {}", e.getMessage(), e);
            throw e;
        }
        log.info("Mapped AppointmentResponseDto: {}", dto);
        return dto;
    }
}