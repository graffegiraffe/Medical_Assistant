package by.rublevskaya.mapper;

import by.rublevskaya.dto.healthmetric.HealthMetricDto;
import by.rublevskaya.dto.healthmetric.HealthMetricResponseDto;
import by.rublevskaya.model.HealthMetric;
import org.springframework.stereotype.Component;

@Component
public class HealthMetricMapper {

    public HealthMetric toEntity(HealthMetricDto dto) {
        HealthMetric healthMetric = new HealthMetric();
        healthMetric.setUserId(dto.getUserId());
        healthMetric.setTimestamp(dto.getTimestamp());
        healthMetric.setBloodPressure(dto.getBloodPressure());
        healthMetric.setBloodSugar(dto.getBloodSugar());
        healthMetric.setTemperature(dto.getTemperature());
        healthMetric.setNotes(dto.getNotes());
        return healthMetric;
    }

    public HealthMetricResponseDto toResponseDto(HealthMetric healthMetric) {
        HealthMetricResponseDto response = new HealthMetricResponseDto();
        response.setTimestamp(healthMetric.getTimestamp());
        response.setBloodPressure(healthMetric.getBloodPressure());
        response.setBloodSugar(healthMetric.getBloodSugar());
        response.setTemperature(healthMetric.getTemperature());
        response.setNotes(healthMetric.getNotes());
        return response;
    }

    public void updateEntity(HealthMetric healthMetric, HealthMetricDto dto) {
        healthMetric.setUserId(dto.getUserId());
        healthMetric.setTimestamp(dto.getTimestamp());
        healthMetric.setBloodPressure(dto.getBloodPressure());
        healthMetric.setBloodSugar(dto.getBloodSugar());
        healthMetric.setTemperature(dto.getTemperature());
        healthMetric.setNotes(dto.getNotes());
    }

    public void updatePartialEntity(HealthMetric healthMetric, HealthMetricDto dto) {
        if (dto.getUserId() != null) {
            healthMetric.setUserId(dto.getUserId());
        }
        if (dto.getTimestamp() != null) {
            healthMetric.setTimestamp(dto.getTimestamp());
        }
        if (dto.getBloodPressure() != null) {
            healthMetric.setBloodPressure(dto.getBloodPressure());
        }
        if (dto.getBloodSugar() != null) {
            healthMetric.setBloodSugar(dto.getBloodSugar());
        }
        if (dto.getTemperature() != null) {
            healthMetric.setTemperature(dto.getTemperature());
        }
        if (dto.getNotes() != null) {
            healthMetric.setNotes(dto.getNotes());
        }
    }
}
