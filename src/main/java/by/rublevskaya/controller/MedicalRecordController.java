package by.rublevskaya.controller;

import by.rublevskaya.dto.medicalrecord.MedicalRecordDto;
import by.rublevskaya.dto.medicalrecord.MedicalRecordResponseDto;
import by.rublevskaya.service.MedicalRecordCrudService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/medicalrecords")
@RequiredArgsConstructor
public class MedicalRecordController {

    private final MedicalRecordCrudService medicalRecordCrudService;

    @PostMapping
    public ResponseEntity<MedicalRecordResponseDto> createMedicalRecord(@Valid @RequestBody MedicalRecordDto dto) {
        return ResponseEntity.ok(medicalRecordCrudService.createMedicalRecord(dto));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<MedicalRecordResponseDto>> getMedicalRecordsByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(medicalRecordCrudService.getMedicalRecordsByUserId(userId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicalRecordResponseDto> getMedicalRecordById(@PathVariable Long id) {
        return ResponseEntity.ok(medicalRecordCrudService.getMedicalRecordById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MedicalRecordResponseDto> updateMedicalRecord(
            @PathVariable Long id, @Valid @RequestBody MedicalRecordDto dto) {
        return ResponseEntity.ok(medicalRecordCrudService.updateMedicalRecord(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMedicalRecord(@PathVariable Long id) {
        String responseMessage = medicalRecordCrudService.deleteMedicalRecord(id);
        return ResponseEntity.ok(responseMessage);
    }
}