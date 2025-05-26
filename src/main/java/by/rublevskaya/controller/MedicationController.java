package by.rublevskaya.controller;

import by.rublevskaya.dto.medication.MedicationDto;
import by.rublevskaya.dto.medication.MedicationResponseDto;
import by.rublevskaya.service.MedicationCrudService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/medications")
@RequiredArgsConstructor
public class MedicationController {

    private final MedicationCrudService medicationCrudService;

    @PostMapping
    public ResponseEntity<MedicationResponseDto> createMedication(@Valid @RequestBody MedicationDto medicationDto) {
        log.info("Received request to create medication with data: {}", medicationDto);
        MedicationResponseDto responseDto = medicationCrudService.createMedication(medicationDto);
        log.info("Medication created successfully: {}", responseDto);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<MedicationResponseDto>> getMedicationsByUserId(@PathVariable Long userId) {
        log.info("Fetching medications for user ID: {}", userId);
        List<MedicationResponseDto> medications = medicationCrudService.getMedicationsByUserId(userId);
        log.info("Retrieved {} medications for user ID: {}", medications.size(), userId);
        return ResponseEntity.ok(medications);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicationResponseDto> getMedicationById(@PathVariable Long id) {
        log.info("Fetching medication with ID: {}", id);
        MedicationResponseDto medication = medicationCrudService.getMedicationById(id);
        if (medication != null) {
            log.info("Medication with ID: {} retrieved successfully: {}", id, medication);
        } else {
            log.warn("Medication with ID: {} not found", id);
        }
        return ResponseEntity.ok(medication);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MedicationResponseDto> updateMedication(
            @PathVariable Long id,
            @Valid @RequestBody MedicationDto medicationDto) {
        log.info("Received update request for medication with ID: {} using data: {}", id, medicationDto);
        MedicationResponseDto updatedMedication = medicationCrudService.updateMedication(id, medicationDto);
        log.info("Medication with ID: {} updated successfully: {}", id, updatedMedication);
        return ResponseEntity.ok(updatedMedication);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMedication(@PathVariable Long id) {
        log.info("Received request to delete medication with ID: {}", id);
        String responseMessage = medicationCrudService.deleteMedication(id);
        log.info("Medication with ID: {} deleted successfully", id);
        return ResponseEntity.ok(responseMessage);
    }
}