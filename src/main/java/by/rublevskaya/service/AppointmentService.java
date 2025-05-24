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
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final AppointmentMapper appointmentMapper;
    private final DoctorRepository doctorRepository;

    public AppointmentResponseDto bookAppointment(AppointmentDto dto) {
        // Найти врача по номеру лицензии
        Doctor doctor = doctorRepository.findByLicenseNumber(dto.getLicenseNumber())
                .orElseThrow(() -> new CustomException("Doctor with this license does not exist."));

        Appointment appointment = appointmentMapper.toEntity(dto);

        // Установить ID врача на основе найденной лицензии
        appointment.setDoctorId(doctor.getId());

        // Проверка расписания (как раньше)
        List<Appointment> doctorAppointments = appointmentRepository.findByDoctorId(doctor.getId());
        if (doctorAppointments.stream().anyMatch(a -> overlapCheck(dto.getDateTime(), a.getDateTime()))) {
            throw new CustomException("The doctor is not available at this time.");
        }

        Appointment savedAppointment = appointmentRepository.save(appointment);
        return appointmentMapper.toDto(savedAppointment);
    }


    public List<AppointmentResponseDto> getUserAppointments(Long userId) {
        List<Appointment> appointments = appointmentRepository.findByUserId(userId);
        return appointments.stream()
                .map(appointmentMapper::toDto)
                .toList();
    }

    public void deleteAppointment(Long appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new CustomException("Appointment not found."));
        appointmentRepository.delete(appointment);
    }

    private boolean overlapCheck(LocalDateTime requestedTime, LocalDateTime existingTime) {
        return !requestedTime.isBefore(existingTime.minusMinutes(40)) &&
                !requestedTime.isAfter(existingTime.plusMinutes(40));
    }

    public void deleteOutdatedAppointments() {
        List<Appointment> outdatedAppointments = appointmentRepository.findByDateTimeBefore(LocalDateTime.now());
        appointmentRepository.deleteAll(outdatedAppointments);
    }

    @Transactional
    public AppointmentResponseDto partialUpdateAppointment(Long appointmentId, AppointmentUpdateDto updateDto) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new CustomException("Appointment not found."));

        if (updateDto.getDateTime() != null) {
            List<Appointment> doctorAppointments = appointmentRepository.findByDoctorId(appointment.getDoctorId());
            if (doctorAppointments.stream().anyMatch(a -> overlapCheck(updateDto.getDateTime(), a.getDateTime()))) {
                throw new CustomException("The doctor is not available for the new selected time.");
            }

            appointment.setDateTime(updateDto.getDateTime());
        }
        if (updateDto.getNotes() != null) {
            appointment.setNotes(updateDto.getNotes());
        }

        Appointment updatedAppointment = appointmentRepository.save(appointment);
        return appointmentMapper.toDto(updatedAppointment);
    }

    @Transactional
    public void completeAppointment(Long appointmentId, String doctorLicense) {
        // Найти запись по ID
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new CustomException("Appointment not found."));

        // Убедиться, что врач помечает именно свою запись
        if (!appointment.getLicenseNumber().equals(doctorLicense)) {
            throw new CustomException("You are not authorized to complete this appointment.");
        }

        // Установить статус завершения
        appointment.setCompleted(true);
        appointmentRepository.save(appointment);
    }

}