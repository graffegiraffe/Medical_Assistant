package by.rublevskaya.controller;

import by.rublevskaya.dto.doctor.DoctorDto;
import by.rublevskaya.dto.doctor.DoctorResponseDto;
import by.rublevskaya.dto.doctor.DoctorUpdateDto;
import by.rublevskaya.service.DoctorCrudService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/doctors")
@RequiredArgsConstructor
public class DoctorCrudController {

    private final DoctorCrudService doctorCrudService;

    @PostMapping
    public ResponseEntity<DoctorResponseDto> createDoctor(@Valid @RequestBody DoctorDto registrationDto) {
        DoctorResponseDto createdDoctor = doctorCrudService.createDoctor(registrationDto);
        return ResponseEntity.ok(createdDoctor);
    }


    @GetMapping
    public ResponseEntity<List<DoctorResponseDto>> getAllDoctors() {
        List<DoctorResponseDto> doctors = doctorCrudService.getAllDoctors();
        return ResponseEntity.ok(doctors);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DoctorResponseDto> getDoctorById(@PathVariable Long id) {
        DoctorResponseDto doctor = doctorCrudService.getDoctorById(id);
        return ResponseEntity.ok(doctor);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DoctorDto> updateDoctor(@PathVariable Long id, @Valid @RequestBody DoctorDto dto) {
        DoctorDto updatedDoctor = doctorCrudService.updateDoctor(id, dto);
        return ResponseEntity.ok(updatedDoctor);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<DoctorDto> partialUpdateDoctor(@PathVariable Long id, @Valid @RequestBody DoctorUpdateDto dto) {
        DoctorDto updatedDoctor = doctorCrudService.partialUpdateDoctor(id, dto);
        return ResponseEntity.ok(updatedDoctor);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDoctor(@PathVariable Long id) {
        doctorCrudService.deleteDoctor(id);
        return ResponseEntity.ok("Doctor deleted successfully.");
    }
}