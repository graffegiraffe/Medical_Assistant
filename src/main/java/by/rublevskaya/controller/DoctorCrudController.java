package by.rublevskaya.controller;

import by.rublevskaya.dto.doctor.DoctorDto;
import by.rublevskaya.dto.doctor.DoctorResponseDto;
import by.rublevskaya.dto.doctor.DoctorUpdateDto;
import by.rublevskaya.service.DoctorCrudService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@RestController
@RequestMapping("/doctors")
@RequiredArgsConstructor
@Slf4j
public class DoctorCrudController {

    private final DoctorCrudService doctorCrudService;

    @PostMapping
    public ResponseEntity<DoctorResponseDto> createDoctor(@Valid @RequestBody DoctorDto registrationDto) {
        log.info("Received request to create doctor with data: {}", registrationDto);
        DoctorResponseDto createdDoctor = doctorCrudService.createDoctor(registrationDto);
        log.info("Doctor created successfully: {}", createdDoctor);
        return ResponseEntity.ok(createdDoctor);
    }

    @GetMapping
    public ResponseEntity<List<DoctorResponseDto>> getAllDoctors() {
        log.info("Fetching all doctors...");
        List<DoctorResponseDto> doctors = doctorCrudService.getAllDoctors();
        log.info("Retrieved {} doctors", doctors.size());
        return ResponseEntity.ok(doctors);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DoctorResponseDto> getDoctorById(@PathVariable Long id) {
        log.info("Fetching doctor with ID: {}", id);
        DoctorResponseDto doctor = doctorCrudService.getDoctorById(id);
        if (doctor != null) {
            log.info("Doctor with ID: {} successfully retrieved: {}", id, doctor);
        } else {
            log.warn("Doctor with ID: {} not found", id);
        }
        return ResponseEntity.ok(doctor);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DoctorDto> updateDoctor(@PathVariable Long id, @Valid @RequestBody DoctorDto dto) {
        log.info("Received request to update doctor with ID: {} using data: {}", id, dto);
        DoctorDto updatedDoctor = doctorCrudService.updateDoctor(id, dto);
        log.info("Doctor with ID: {} updated successfully: {}", id, updatedDoctor);
        return ResponseEntity.ok(updatedDoctor);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<DoctorDto> partialUpdateDoctor(@PathVariable Long id, @Valid @RequestBody DoctorUpdateDto dto) {
        log.info("Received request for partial update of doctor with ID: {} using data: {}", id, dto);
        DoctorDto updatedDoctor = doctorCrudService.partialUpdateDoctor(id, dto);
        log.info("Doctor with ID: {} partially updated successfully: {}", id, updatedDoctor);
        return ResponseEntity.ok(updatedDoctor);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDoctor(@PathVariable Long id) {
        log.info("Received request to delete doctor with ID: {}", id);
        doctorCrudService.deleteDoctor(id);
        log.info("Doctor with ID: {} deleted successfully", id);
        return ResponseEntity.ok("Doctor deleted successfully.");
    }
}