package by.rublevskaya.service;

import by.rublevskaya.dto.healthmetric.HealthMetricDto;
import by.rublevskaya.dto.healthmetric.HealthMetricResponseDto;
import by.rublevskaya.exception.CustomException;
import by.rublevskaya.mapper.HealthMetricMapper;
import by.rublevskaya.model.HealthMetric;
import by.rublevskaya.repository.HealthMetricRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HealthMetricCrudService {

    private final HealthMetricRepository healthMetricRepository;
    private final HealthMetricMapper healthMetricMapper;

    @Transactional
    public HealthMetricResponseDto createHealthMetric(HealthMetricDto dto) {
        HealthMetric healthMetric = healthMetricMapper.toEntity(dto);
        HealthMetric saved = healthMetricRepository.save(healthMetric);
        return healthMetricMapper.toResponseDto(saved);
    }

    @Transactional(readOnly = true)
    public List<HealthMetricResponseDto> getMetricsByUserId(Long userId) {
        return healthMetricRepository.findByUserId(userId)
                .stream()
                .map(healthMetricMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public HealthMetricResponseDto getMetricById(Long id) {
        HealthMetric healthMetric = healthMetricRepository.findById(id)
                .orElseThrow(() -> new CustomException("Health metric not found with ID " + id));
        return healthMetricMapper.toResponseDto(healthMetric);
    }

    @Transactional
    public HealthMetricResponseDto updateFullMetric(Long id, HealthMetricDto dto) {
        HealthMetric existingMetric = healthMetricRepository.findById(id)
                .orElseThrow(() -> new CustomException("Health metric not found with ID " + id));
        healthMetricMapper.updateEntity(existingMetric, dto);
        HealthMetric updatedMetric = healthMetricRepository.save(existingMetric);
        return healthMetricMapper.toResponseDto(updatedMetric);
    }

    @Transactional
    public HealthMetricResponseDto updatePartialMetric(Long id, HealthMetricDto dto) {
        HealthMetric existingMetric = healthMetricRepository.findById(id)
                .orElseThrow(() -> new CustomException("Health metric not found with ID " + id));
        healthMetricMapper.updatePartialEntity(existingMetric, dto);
        HealthMetric updatedMetric = healthMetricRepository.save(existingMetric);
        return healthMetricMapper.toResponseDto(updatedMetric);
    }

    @Transactional
    public String deleteMetric(Long id) {
        if (!healthMetricRepository.existsById(id)) {
            throw new CustomException("Health metric not found with ID " + id);
        }
        healthMetricRepository.deleteById(id);
        return "Health metric with ID " + id + " has been successfully deleted.";
    }
}