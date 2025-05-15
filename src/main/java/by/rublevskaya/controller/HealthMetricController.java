package by.rublevskaya.controller;

import by.rublevskaya.dto.healthmetric.HealthMetricDto;
import by.rublevskaya.dto.healthmetric.HealthMetricResponseDto;
import by.rublevskaya.service.HealthMetricCrudService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/healthmetrics")
@RequiredArgsConstructor
public class HealthMetricController {

    private final HealthMetricCrudService healthMetricCrudService;

    @PostMapping
    public ResponseEntity<HealthMetricResponseDto> createMetric(@Valid @RequestBody HealthMetricDto dto) {
        return ResponseEntity.ok(healthMetricCrudService.createHealthMetric(dto));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<HealthMetricResponseDto>> getMetricsByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(healthMetricCrudService.getMetricsByUserId(userId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<HealthMetricResponseDto> getMetricById(@PathVariable Long id) {
        return ResponseEntity.ok(healthMetricCrudService.getMetricById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<HealthMetricResponseDto> updateFullMetric(
            @PathVariable Long id, @Valid @RequestBody HealthMetricDto dto) {
        return ResponseEntity.ok(healthMetricCrudService.updateFullMetric(id, dto));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<HealthMetricResponseDto> updatePartialMetric(
            @PathVariable Long id, @RequestBody HealthMetricDto dto) {
        return ResponseEntity.ok(healthMetricCrudService.updatePartialMetric(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMetric(@PathVariable Long id) {
        healthMetricCrudService.deleteMetric(id);
        return ResponseEntity.noContent().build();
    }
}