package by.rublevskaya.mapper;

import by.rublevskaya.dto.healthmetric.HealthMetricDto;
import by.rublevskaya.dto.healthmetric.HealthMetricResponseDto;
import by.rublevskaya.model.HealthMetric;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class HealthMetricMapper {

    public HealthMetric toEntity(HealthMetricDto dto) {
        log.info("Mapping HealthMetricDto to HealthMetric entity: {}", dto);
        HealthMetric healthMetric = new HealthMetric();
        healthMetric.setUserId(dto.getUserId());
        healthMetric.setTimestamp(dto.getTimestamp());
        healthMetric.setBloodPressure(dto.getBloodPressure());
        healthMetric.setBloodSugar(dto.getBloodSugar());
        healthMetric.setTemperature(dto.getTemperature());
        healthMetric.setNotes(dto.getNotes());
        log.info("Mapped HealthMetric entity: {}", healthMetric);
        return healthMetric;
    }

    public HealthMetricResponseDto toResponseDto(HealthMetric healthMetric) {
        log.info("Mapping HealthMetric entity to HealthMetricResponseDto: {}", healthMetric);
        HealthMetricResponseDto response = new HealthMetricResponseDto();
        response.setTimestamp(healthMetric.getTimestamp());
        response.setBloodPressure(healthMetric.getBloodPressure());
        response.setBloodSugar(healthMetric.getBloodSugar());
        response.setTemperature(healthMetric.getTemperature());
        response.setNotes(healthMetric.getNotes());
        log.info("Mapped HealthMetricResponseDto: {}", response);
        return response;
    }

    public void updateEntity(HealthMetric healthMetric, HealthMetricDto dto) {
        log.info("Updating HealthMetric entity with HealthMetricDto. Entity: {}, Dto: {}", healthMetric, dto);
        healthMetric.setUserId(dto.getUserId());
        healthMetric.setTimestamp(dto.getTimestamp());
        healthMetric.setBloodPressure(dto.getBloodPressure());
        healthMetric.setBloodSugar(dto.getBloodSugar());
        healthMetric.setTemperature(dto.getTemperature());
        healthMetric.setNotes(dto.getNotes());
        log.info("Updated HealthMetric entity: {}", healthMetric);
    }

    public void updatePartialEntity(HealthMetric healthMetric, HealthMetricDto dto) {
        log.info("Partially updating HealthMetric entity with HealthMetricDto. Entity: {}, Dto: {}", healthMetric, dto);
        if (dto.getUserId() != null) {
            healthMetric.setUserId(dto.getUserId());
            log.info("Updated userId: {}", dto.getUserId());
        }
        if (dto.getTimestamp() != null) {
            healthMetric.setTimestamp(dto.getTimestamp());
            log.info("Updated timestamp: {}", dto.getTimestamp());
        }
        if (dto.getBloodPressure() != null) {
            healthMetric.setBloodPressure(dto.getBloodPressure());
            log.info("Updated bloodPressure: {}", dto.getBloodPressure());
        }
        if (dto.getBloodSugar() != null) {
            healthMetric.setBloodSugar(dto.getBloodSugar());
            log.info("Updated bloodSugar: {}", dto.getBloodSugar());
        }
        if (dto.getTemperature() != null) {
            healthMetric.setTemperature(dto.getTemperature());
            log.info("Updated temperature: {}", dto.getTemperature());
        }
        if (dto.getNotes() != null) {
            healthMetric.setNotes(dto.getNotes());
            log.info("Updated notes: {}", dto.getNotes());
        }
        log.info("Partially updated HealthMetric entity: {}", healthMetric);
    }
}
