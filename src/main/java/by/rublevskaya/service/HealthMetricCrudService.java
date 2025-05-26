package by.rublevskaya.service;

import by.rublevskaya.dto.healthmetric.HealthMetricDto;
import by.rublevskaya.dto.healthmetric.HealthMetricResponseDto;
import by.rublevskaya.exception.CustomException;
import by.rublevskaya.mapper.HealthMetricMapper;
import by.rublevskaya.model.HealthMetric;
import by.rublevskaya.repository.HealthMetricRepository;
import by.rublevskaya.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class HealthMetricCrudService {

    private final HealthMetricRepository healthMetricRepository;
    private final HealthMetricMapper healthMetricMapper;
    private final UserRepository userRepository;

    @Transactional
    public HealthMetricResponseDto createHealthMetric(HealthMetricDto dto) {
        log.info("Creating new health metric for user ID: {}", dto.getUserId());
        HealthMetric healthMetric = healthMetricMapper.toEntity(dto);
        log.debug("Mapped HealthMetricDto to HealthMetric entity: {}", healthMetric);
        HealthMetric saved = healthMetricRepository.save(healthMetric);
        log.info("Health metric created successfully with ID: {}", saved.getId());
        return healthMetricMapper.toResponseDto(saved);
    }

    @Transactional(readOnly = true)
    public List<HealthMetricResponseDto> getMetricsByUserId(Long userId) {
        log.info("Fetching health metrics for user ID: {}", userId);
        return healthMetricRepository.findByUserId(userId)
                .stream()
                .map(healthMetricMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public HealthMetricResponseDto getMetricById(Long id) {
        log.info("Fetching health metric with ID: {}", id);
        HealthMetric healthMetric = healthMetricRepository.findById(id).orElseThrow(() -> {
            log.warn("Health metric with ID {} not found.", id);
            return new CustomException("Health metric with ID " + id + " not found.");
        });
        log.info("Successfully fetched health metric with ID: {}", id);
        return healthMetricMapper.toResponseDto(healthMetric);
    }

    @Transactional
    public HealthMetricResponseDto updateFullMetric(Long id, HealthMetricDto dto) {
        log.info("Updating health metric with ID: {}", id);
        HealthMetric existingMetric = healthMetricRepository.findById(id)
                .orElseThrow(() -> new CustomException("Health metric not found with ID " + id));

        if (dto.getUserId() == null || dto.getUserId() <= 0) {
            throw new CustomException("Invalid user ID: " + dto.getUserId());
        }
        if (dto.getUserId() == null || !userRepository.existsById(dto.getUserId())) {
            throw new CustomException("User with ID " + dto.getUserId() + " does not exist");
        }
        if (dto.getBloodPressure() == null || dto.getBloodPressure().isEmpty()) {
            throw new CustomException("Blood pressure cannot be empty");
        }
        if (dto.getBloodSugar() == null) {
            throw new CustomException("Blood sugar cannot be null");
        }
        if (dto.getBloodSugar() <= 0) {
            throw new CustomException("Blood sugar must be a positive number");
        }
        if (dto.getTemperature() == null || dto.getTemperature() <= 0) {
            throw new CustomException("Temperature must be a positive number");
        }
        if (dto.getTimestamp() == null) {
            throw new CustomException("Timestamp cannot be null");
        }
        healthMetricMapper.updateEntity(existingMetric, dto);
        HealthMetric updatedMetric = healthMetricRepository.save(existingMetric);
        log.info("Successfully updated health metric with ID: {}", updatedMetric.getId());
        return healthMetricMapper.toResponseDto(updatedMetric);
    }

    @Transactional
    public HealthMetricResponseDto updatePartialMetric(Long id, HealthMetricDto dto) {
        log.info("Partially updating health metric with ID: {}", id);
        HealthMetric existingMetric = healthMetricRepository.findById(id)
                .orElseThrow(() ->{
                    log.warn("Health metric with ID {} not found. Partial update aborted.", id);
                    return new CustomException("Health metric with ID " + id + " not found.");
                });
        healthMetricMapper.updatePartialEntity(existingMetric, dto);
        HealthMetric updatedMetric = healthMetricRepository.save(existingMetric);
        log.info("Successfully partially updated health metric with ID: {}", updatedMetric.getId());
        return healthMetricMapper.toResponseDto(updatedMetric);
    }

    @Transactional
    public String deleteMetric(Long id) {
        log.info("Attempting to delete health metric with ID: {}", id);
        if (!healthMetricRepository.existsById(id)) {
            log.warn("Health metric with ID {} does not exist. Deletion aborted.", id);
            throw new CustomException("Health metric with ID " + id + " does not exist.");
        }
        healthMetricRepository.deleteById(id);
        log.info("Health metric with ID {} has been successfully deleted.", id);
        return "Health metric with ID " + id + " has been successfully deleted.";
    }
}