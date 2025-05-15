package by.rublevskaya.controller;

import by.rublevskaya.dto.medication.MedicationDto;
import by.rublevskaya.dto.medication.MedicationResponseDto;
import by.rublevskaya.service.MedicationCrudService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/medications")
@RequiredArgsConstructor
public class MedicationController {

    private final MedicationCrudService medicationCrudService;

    @PostMapping
    public ResponseEntity<MedicationResponseDto> createMedication(@Valid @RequestBody MedicationDto medicationDto) {
        MedicationResponseDto responseDto = medicationCrudService.createMedication(medicationDto);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<MedicationResponseDto>> getMedicationsByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(medicationCrudService.getMedicationsByUserId(userId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicationResponseDto> getMedicationById(@PathVariable Long id) {
        return ResponseEntity.ok(medicationCrudService.getMedicationById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MedicationResponseDto> updateMedication(
            @PathVariable Long id,
            @Valid @RequestBody MedicationDto medicationDto) {
        return ResponseEntity.ok(medicationCrudService.updateMedication(id, medicationDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMedication(@PathVariable Long id) {
        medicationCrudService.deleteMedication(id);
        return ResponseEntity.noContent().build();
    }
}