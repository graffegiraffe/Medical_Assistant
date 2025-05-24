package by.rublevskaya.mapper;

import by.rublevskaya.dto.appointment.AppointmentDto;
import by.rublevskaya.dto.appointment.AppointmentResponseDto;
import by.rublevskaya.model.Appointment;
import by.rublevskaya.model.Doctor;
import by.rublevskaya.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AppointmentMapper {
    private final DoctorRepository doctorRepository;

    @Autowired
    public AppointmentMapper(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    public Appointment toEntity(AppointmentDto dto) {
        Appointment appointment = new Appointment();
        appointment.setUserId(dto.getUserId());
        appointment.setLicenseNumber(dto.getLicenseNumber());
        appointment.setDateTime(dto.getDateTime());
        appointment.setNotes(dto.getNotes());
        appointment.setCompleted(false);
        return appointment;
    }

    public AppointmentResponseDto toDto(Appointment appointment) {
        AppointmentResponseDto dto = new AppointmentResponseDto();
        dto.setLicenseNumber(appointment.getLicenseNumber());
        dto.setDateTime(appointment.getDateTime());
        dto.setNotes(appointment.getNotes());
        dto.setCompleted(appointment.getCompleted());

        Doctor doctor = doctorRepository.findByLicenseNumber(appointment.getLicenseNumber())
                .orElseThrow(() -> new RuntimeException("Doctor with license " + appointment.getLicenseNumber() + " not found"));

        dto.setFullName(doctor.getFullName());
        dto.setClinicName(doctor.getClinicName());
        dto.setSpecialty(doctor.getSpecialty());

        return dto;
    }

}