package by.rublevskaya.controller;

import by.rublevskaya.dto.clinic.ClinicDto;
import by.rublevskaya.dto.clinic.ClinicResponseDto;
import by.rublevskaya.dto.clinic.ClinicUpdateDto;
import by.rublevskaya.service.ClinicService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/clinics")
@RequiredArgsConstructor
public class ClinicController {
    private final ClinicService clinicService;

    @GetMapping
    public ResponseEntity<List<ClinicResponseDto>> getAllClinics() {
        log.info("Fetching all clinics");
        List<ClinicResponseDto> clinics = clinicService.getAllClinics();
        log.info("Retrieved {} clinics", clinics.size());
        return ResponseEntity.ok(clinics);
    }

    @PostMapping
    public ResponseEntity<ClinicResponseDto> addClinic(@RequestBody @Valid ClinicDto clinicCreateDto) {
        log.info("Received request to add clinic: {}", clinicCreateDto);
        ClinicResponseDto createdClinic = clinicService.addClinic(clinicCreateDto);
        log.info("Clinic added successfully: {}", createdClinic);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdClinic);
    }

    @GetMapping("/sorted")
    public ResponseEntity<List<ClinicResponseDto>> getAllClinicsWithSorting(@RequestParam String field) {
        log.info("Fetching all clinics sorted by field: {}", field);
        List<ClinicResponseDto> sortedClinics = clinicService.getAllClinicsWithSorting(field);
        log.info("Retrieved {} sorted clinics by field: {}", sortedClinics.size(), field);
        return ResponseEntity.ok(sortedClinics);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteClinic(@PathVariable Long id) {
        log.info("Received request to delete clinic with ID: {}", id);
        String message = clinicService.deleteClinicById(id);
        log.info("Clinic with ID: {} deleted successfully", id);
        return ResponseEntity.ok(message);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClinicResponseDto> getClinicById(@PathVariable Long id) {
        log.info("Fetching clinic with ID: {}", id);
        ClinicResponseDto clinic = clinicService.getClinicById(id);
        if (clinic != null) {
            log.info("Clinic with ID: {} successfully retrieved: {}", id, clinic);
        } else {
            log.warn("Clinic with ID: {} not found", id);
        }
        return ResponseEntity.ok(clinic);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClinicResponseDto> updateClinic(@PathVariable Long id, @RequestBody @Valid ClinicDto updateDto) {
        log.info("Received request to update clinic with ID: {} using data: {}", id, updateDto);
        ClinicResponseDto updatedClinic = clinicService.updateClinic(id, updateDto);
        log.info("Clinic with ID: {} updated successfully: {}", id, updatedClinic);
        return ResponseEntity.ok(updatedClinic);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ClinicResponseDto> partialUpdateClinic(
            @PathVariable Long id,
            @RequestBody @Valid ClinicUpdateDto updateDto) {
        log.info("Received request for partial update of clinic with ID: {} using data: {}", id, updateDto);
        ClinicResponseDto updatedClinic = clinicService.partialUpdateClinic(id, updateDto);
        log.info("Clinic with ID: {} partially updated successfully: {}", id, updatedClinic);
        return ResponseEntity.ok(updatedClinic);
    }

    @GetMapping("/paginated")
    public ResponseEntity<Page<ClinicResponseDto>> getClinicsWithPagination(
            @RequestParam int page,
            @RequestParam int size) {
        log.info("Fetching paginated clinics for page: {}, size: {}", page, size);
        Page<ClinicResponseDto> paginatedClinics = clinicService.getClinicsWithPagination(page, size);
        log.info("Retrieved {} clinics on page: {}, with size: {}", paginatedClinics.getTotalElements(), page, size);
        return ResponseEntity.ok(paginatedClinics);
    }
}