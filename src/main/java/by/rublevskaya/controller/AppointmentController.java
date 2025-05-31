package by.rublevskaya.controller;

import by.rublevskaya.dto.appointment.AppointmentDto;
import by.rublevskaya.dto.appointment.AppointmentResponseDto;
import by.rublevskaya.dto.appointment.AppointmentUpdateDto;
import by.rublevskaya.service.AppointmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@RestController
@RequestMapping("/appointments")
@RequiredArgsConstructor
@Slf4j
public class AppointmentController {

    private final AppointmentService appointmentService;

    @PostMapping
    public ResponseEntity<AppointmentResponseDto> bookAppointment(@Valid @RequestBody AppointmentDto dto) {
        log.info("Received request to book appointment: {}", dto);
        AppointmentResponseDto responseDto = appointmentService.bookAppointment(dto);
        log.info("Appointment booked successfully: {}", responseDto);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<AppointmentResponseDto>> getAppointmentsByUser(@PathVariable Long userId) {
        log.info("Fetching appointments for user with ID: {}", userId);
        List<AppointmentResponseDto> userAppointments = appointmentService.getUserAppointments(userId);
        log.info("Retrieved {} appointments for user ID: {}", userAppointments.size(), userId);
        return ResponseEntity.ok(userAppointments);
    }

    @DeleteMapping("/{appointmentId}")
    public ResponseEntity<String> deleteAppointment(@PathVariable Long appointmentId) {
        log.info("Deleting appointment with ID: {}", appointmentId);
        appointmentService.deleteAppointment(appointmentId);
        log.info("Appointment deleted successfully.");
        return ResponseEntity.ok("Appointment deleted successfully.");
    }

    @DeleteMapping("/outdated")
    public ResponseEntity<String> deleteOutdatedAppointments() {
        log.info("Deleting outdated appointments.");
        appointmentService.deleteOutdatedAppointments();
        log.info("Outdated appointments deleted successfully.");
        return ResponseEntity.ok("Outdated appointments deleted successfully.");
    }

    @PatchMapping("/{appointmentId}")
    public ResponseEntity<AppointmentResponseDto> partialUpdateAppointment(
            @PathVariable Long appointmentId,
            @RequestBody AppointmentUpdateDto updateDto) {
        log.info("Received request to partially update appointment with ID: {} using data: {}", appointmentId, updateDto);
        AppointmentResponseDto responseDto = appointmentService.partialUpdateAppointment(appointmentId, updateDto);
        log.info("Appointment with ID: {} updated successfully. Updated data: {}", appointmentId, responseDto);
        return ResponseEntity.ok(responseDto);
    }

    @PatchMapping("/{appointmentId}/complete")
    public ResponseEntity<String> completeAppointment(
            @PathVariable Long appointmentId,
            @RequestParam String doctorLicense) {
        log.info("Marking appointment with ID: {} as completed by doctor with license: {}", appointmentId, doctorLicense);
        appointmentService.completeAppointment(appointmentId, doctorLicense);
        log.info("Appointment with ID: {} marked as completed successfully.", appointmentId);
        return ResponseEntity.ok("Appointment marked as completed.");

    }
}

