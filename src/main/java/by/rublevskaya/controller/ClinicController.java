package by.rublevskaya.controller;

import by.rublevskaya.dto.clinic.ClinicDto;
import by.rublevskaya.dto.clinic.ClinicResponseDto;
import by.rublevskaya.dto.clinic.ClinicUpdateDto;
import by.rublevskaya.service.ClinicService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/clinics")
@RequiredArgsConstructor
public class ClinicController {
    private final ClinicService clinicService;

    @GetMapping
    public ResponseEntity<List<ClinicResponseDto>> getAllClinics() {
        List<ClinicResponseDto> clinics = clinicService.getAllClinics();
        return ResponseEntity.ok(clinics);
    }

    @PostMapping
    public ResponseEntity<ClinicResponseDto> addClinic(@RequestBody @Valid ClinicDto clinicCreateDto) {
        ClinicResponseDto createdClinic = clinicService.addClinic(clinicCreateDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdClinic);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteClinic(@PathVariable Long id) {
        String message = clinicService.deleteClinicById(id);
        return ResponseEntity.ok(message);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClinicResponseDto> getClinicById(@PathVariable Long id) {
        ClinicResponseDto clinic = clinicService.getClinicById(id);
        return ResponseEntity.ok(clinic);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClinicResponseDto> updateClinic(@PathVariable Long id, @RequestBody @Valid ClinicDto updateDto) {
        ClinicResponseDto updatedClinic = clinicService.updateClinic(id, updateDto);
        return ResponseEntity.ok(updatedClinic);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ClinicResponseDto> partialUpdateClinic(
            @PathVariable Long id,
            @RequestBody @Valid ClinicUpdateDto updateDto) {
        ClinicResponseDto updatedClinic = clinicService.partialUpdateClinic(id, updateDto);
        return ResponseEntity.ok(updatedClinic);
    }
}