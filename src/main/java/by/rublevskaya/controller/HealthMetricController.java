package by.rublevskaya.controller;

import by.rublevskaya.dto.healthmetric.HealthMetricDto;
import by.rublevskaya.dto.healthmetric.HealthMetricResponseDto;
import by.rublevskaya.service.HealthMetricCrudService;
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
@RequestMapping("/healthmetrics")
@RequiredArgsConstructor
@Slf4j
public class HealthMetricController {

    private final HealthMetricCrudService healthMetricCrudService;

    @PostMapping
    public ResponseEntity<HealthMetricResponseDto> createMetric(@Valid @RequestBody HealthMetricDto dto) {
        log.info("Received request to create health metric with data: {}", dto);
        HealthMetricResponseDto createdMetric = healthMetricCrudService.createHealthMetric(dto);
        log.info("Health metric created successfully: {}", createdMetric);
        return ResponseEntity.ok(createdMetric);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<HealthMetricResponseDto>> getMetricsByUserId(@PathVariable Long userId) {
        log.info("Fetching health metrics for user ID: {}", userId);
        List<HealthMetricResponseDto> metrics = healthMetricCrudService.getMetricsByUserId(userId);
        log.info("Retrieved {} health metrics for user ID: {}", metrics.size(), userId);
        return ResponseEntity.ok(metrics);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HealthMetricResponseDto> getMetricById(@PathVariable Long id) {
        log.info("Fetching health metric with ID: {}", id);
        HealthMetricResponseDto metric = healthMetricCrudService.getMetricById(id);
        if (metric != null) {
            log.info("Health metric with ID: {} retrieved successfully: {}", id, metric);
        } else {
            log.warn("Health metric with ID: {} not found", id);
        }
        return ResponseEntity.ok(metric);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HealthMetricResponseDto> updateFullMetric(
            @PathVariable Long id, @Valid @RequestBody HealthMetricDto dto) {
        log.info("Received full update request for health metric with ID: {} using data: {}", id, dto);
        HealthMetricResponseDto updatedMetric = healthMetricCrudService.updateFullMetric(id, dto);
        log.info("Health metric with ID: {} fully updated successfully: {}", id, updatedMetric);
        return ResponseEntity.ok(updatedMetric);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<HealthMetricResponseDto> updatePartialMetric(
            @PathVariable Long id, @RequestBody HealthMetricDto dto) {
        log.info("Received partial update request for health metric with ID: {} using data: {}", id, dto);
        HealthMetricResponseDto updatedMetric = healthMetricCrudService.updatePartialMetric(id, dto);
        log.info("Health metric with ID: {} partially updated successfully: {}", id, updatedMetric);
        return ResponseEntity.ok(updatedMetric);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMetric(@PathVariable Long id) {
        log.info("Received request to delete health metric with ID: {}", id);
        String message = healthMetricCrudService.deleteMetric(id);
        log.info("Health metric with ID: {} deleted successfully", id);
        return ResponseEntity.ok(message);
    }
}