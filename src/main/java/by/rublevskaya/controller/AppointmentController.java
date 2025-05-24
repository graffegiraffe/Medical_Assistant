package by.rublevskaya.controller;

import by.rublevskaya.dto.appointment.AppointmentDto;
import by.rublevskaya.dto.appointment.AppointmentResponseDto;
import by.rublevskaya.dto.appointment.AppointmentUpdateDto;
import by.rublevskaya.service.AppointmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;

    @PostMapping
    public ResponseEntity<AppointmentResponseDto> bookAppointment(@Valid @RequestBody AppointmentDto dto) {
        AppointmentResponseDto responseDto = appointmentService.bookAppointment(dto);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<AppointmentResponseDto>> getAppointmentsByUser(@PathVariable Long userId) {
        List<AppointmentResponseDto> userAppointments = appointmentService.getUserAppointments(userId);
        return ResponseEntity.ok(userAppointments);
    }

    @DeleteMapping("/{appointmentId}")
    public ResponseEntity<String> deleteAppointment(@PathVariable Long appointmentId) {
        appointmentService.deleteAppointment(appointmentId);
        return ResponseEntity.ok("Appointment deleted successfully.");
    }

    @DeleteMapping("/outdated")
    public ResponseEntity<String> deleteOutdatedAppointments() {
        appointmentService.deleteOutdatedAppointments();
        return ResponseEntity.ok("Outdated appointments deleted successfully.");
    }

    @PatchMapping("/{appointmentId}")
    public ResponseEntity<AppointmentResponseDto> partialUpdateAppointment(
            @PathVariable Long appointmentId,
            @RequestBody AppointmentUpdateDto updateDto) {
        AppointmentResponseDto responseDto = appointmentService.partialUpdateAppointment(appointmentId, updateDto);
        return ResponseEntity.ok(responseDto);
    }

    @PatchMapping("/{appointmentId}/complete")
    public ResponseEntity<String> completeAppointment(
            @PathVariable Long appointmentId,
            @RequestParam String doctorLicense) {
        appointmentService.completeAppointment(appointmentId, doctorLicense);
        return ResponseEntity.ok("Appointment marked as completed.");
    }
}

