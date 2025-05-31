package by.rublevskaya.service;

import by.rublevskaya.dto.appointment.AppointmentDto;
import by.rublevskaya.dto.appointment.AppointmentResponseDto;
import by.rublevskaya.dto.appointment.AppointmentUpdateDto;
import by.rublevskaya.exception.CustomException;
import by.rublevskaya.mapper.AppointmentMapper;
import by.rublevskaya.model.Appointment;
import by.rublevskaya.model.Doctor;
import by.rublevskaya.repository.AppointmentRepository;
import by.rublevskaya.repository.DoctorRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final AppointmentMapper appointmentMapper;
    private final DoctorRepository doctorRepository;

    @Transactional
    public AppointmentResponseDto bookAppointment(AppointmentDto dto) {
        log.info("Attempting to book an appointment for userId: {}, doctorId: {}, dateTime: {}",
                dto.getUserId(), dto.getDateTime());
        Doctor doctor = doctorRepository.findByLicenseNumber(dto.getLicenseNumber())
                .orElseThrow(() -> new CustomException("Doctor with this license does not exist."));

        Appointment appointment = appointmentMapper.toEntity(dto);
        appointment.setDoctorId(doctor.getId());

        List<Appointment> doctorAppointments = appointmentRepository.findByDoctorId(doctor.getId());
        if (doctorAppointments.stream().anyMatch(a -> overlapCheck(dto.getDateTime(), a.getDateTime()))) {
            throw new CustomException("The doctor is not available at this time.");
        }

        log.info("Successfully booked an appointment with ID: {}", appointment.getId());
        Appointment savedAppointment = appointmentRepository.save(appointment);
        return appointmentMapper.toDto(savedAppointment);
    }


    public List<AppointmentResponseDto> getUserAppointments(Long userId) {
        log.info("Fetching appointments for userId: {}", userId);
        List<Appointment> appointments = appointmentRepository.findByUserId(userId);
        log.info("Found {} appointments for userId: {}", appointments.size(), userId);
        return appointments.stream()
                .map(appointmentMapper::toDto)
                .toList();
    }

    public void deleteAppointment(Long appointmentId) {
        log.info("Attempting to delete appointment with ID: {}", appointmentId);
        if (!appointmentRepository.existsById(appointmentId)) {
            log.warn("Appointment with ID: {} does not exist", appointmentId);
            throw new CustomException("Appointment not found with ID: " + appointmentId);
        }
        appointmentRepository.deleteById(appointmentId);
        log.info("Successfully deleted appointment with ID: {}", appointmentId);
    }

    private boolean overlapCheck(LocalDateTime requestedTime, LocalDateTime existingTime) {
        log.debug("Checking for time overlap: requestedTime = {}, existingTime = {}", requestedTime, existingTime);
        return !requestedTime.isBefore(existingTime.minusMinutes(40)) &&
                !requestedTime.isAfter(existingTime.plusMinutes(40));
    }

    public void deleteOutdatedAppointments() {
        log.info("Starting cleanup of outdated appointments.");
        List<Appointment> outdatedAppointments = appointmentRepository.findByDateTimeBefore(LocalDateTime.now());
        appointmentRepository.deleteAll(outdatedAppointments);
    }

    @Transactional
    public AppointmentResponseDto partialUpdateAppointment(Long appointmentId, AppointmentUpdateDto updateDto) {
        log.info("Starting partial update of appointment with ID: {}. Update details: {}", appointmentId, updateDto);
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> {
                    log.warn("Appointment not found with ID: {}", appointmentId);
                    return new CustomException("Appointment not found with ID: " + appointmentId);
                });
        if (updateDto.getDateTime() != null) {
            log.info("Updating appointment dateTime from {} to {}", appointment.getDateTime(), updateDto.getDateTime());
            appointment.setDateTime(updateDto.getDateTime());
        }
        if (updateDto.getNotes() != null) {
            log.info("Updating appointment notes from '{}' to '{}'", appointment.getNotes(), updateDto.getNotes());
            appointment.setNotes(updateDto.getNotes());
        }
        Appointment updatedAppointment = appointmentRepository.save(appointment);
        log.info("Successfully updated appointment with ID: {}", updatedAppointment.getId());
        return appointmentMapper.toDto(updatedAppointment);
    }

    @Transactional
    public void completeAppointment(Long appointmentId, String doctorLicense) {
        log.info("Attempting to complete appointment with ID: {}. Doctor license: {}", appointmentId, doctorLicense);

        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> {
                    log.warn("Appointment with ID: {} not found. Unable to complete.", appointmentId);
                    return new CustomException("Appointment not found.");
                });
        log.info("Appointment found with ID: {}. Current status: completed = {}", appointment.getId(), appointment.getCompleted());

        if (!appointment.getLicenseNumber().equals(doctorLicense)) {
            log.warn("Unauthorized attempt to complete appointment. Doctor license: {}, Appointment ID: {}", doctorLicense, appointmentId);
            throw new CustomException("You are not authorized to complete this appointment.");
        }
        log.info("Doctor license verification passed. Doctor: {}, Appointment ID: {}", doctorLicense, appointmentId);
        appointment.setCompleted(true);
        appointmentRepository.save(appointment);
        log.info("Appointment with ID: {} successfully marked as completed by doctor with license: {}", appointmentId, doctorLicense);
    }
}