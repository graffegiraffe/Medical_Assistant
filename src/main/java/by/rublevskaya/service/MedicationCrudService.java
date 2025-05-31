package by.rublevskaya.service;

import by.rublevskaya.dto.medication.MedicationDto;
import by.rublevskaya.dto.medication.MedicationResponseDto;
import by.rublevskaya.exception.CustomException;
import by.rublevskaya.mapper.MedicationMapper;
import by.rublevskaya.model.Medication;
import by.rublevskaya.repository.MedicationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class MedicationCrudService {

    private final MedicationRepository medicationRepository;
    private final MedicationMapper medicationMapper;

    @Transactional
    public MedicationResponseDto createMedication(MedicationDto medicationDto) {
        log.info("Creating a new medication for User ID: {}, Name: {}, Dosage: {}",
                medicationDto.getUserId(), medicationDto.getName(), medicationDto.getDosage());
        Medication medication = medicationMapper.toEntity(medicationDto);
        log.debug("Mapped MedicationDto to Medication entity: {}", medication);
        boolean exists = medicationRepository.existsByUserIdAndNameAndDosageAndStartDateAndEndDate(
                medication.getUserId(),
                medication.getName(),
                medication.getDosage(),
                medication.getStartDate(),
                medication.getEndDate()
        );
        if (exists) {
            log.warn("Duplicate medication detected for User ID: {}, Name: {}, Dosage: {}, Start Date: {}, End Date: {}",
                    medication.getUserId(), medication.getName(), medication.getDosage(),
                    medication.getStartDate(), medication.getEndDate());
            throw new CustomException(
                    "Duplicate medication with the same details already exists for this user.");
        }
        Medication savedMedication = medicationRepository.save(medication);
        log.info("Medication successfully created with ID: {}", savedMedication.getId());
        return medicationMapper.toResponseDto(savedMedication);
    }

    @Transactional(readOnly = true)
    public List<MedicationResponseDto> getMedicationsByUserId(Long userId) {
        log.info("Fetching medications for User ID: {}", userId);
        List<Medication> medications = medicationRepository.findByUserId(userId);
        return medications.stream()
                .map(medicationMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public MedicationResponseDto getMedicationById(Long id) {
        log.info("Fetching medication with ID: {}", id);
        Medication medication = medicationRepository.findById(id).orElseThrow(() -> {
            log.warn("Medication not found with ID: {}", id);
            return new CustomException("Medication not found with ID " + id);
        });
        log.info("Successfully fetched medication with ID: {}", id);
        return medicationMapper.toResponseDto(medication);
    }

    @Transactional
    public MedicationResponseDto updateMedication(Long id, MedicationDto medicationDto) {
        log.info("Updating medication with ID: {}", id);
        Medication medication = medicationRepository.findById(id).orElseThrow(() -> {
            log.warn("Medication not found with ID: {}", id);
            return new CustomException("Medication not found with ID " + id);
        });
        log.debug("Updating medication properties for ID: {}. New Data: {}", id, medicationDto);
        medicationMapper.updateEntity(medication, medicationDto);
        Medication updatedMedication = medicationRepository.save(medication);
        log.info("Medication successfully updated with ID: {}", updatedMedication.getId());
        return medicationMapper.toResponseDto(updatedMedication);
    }

    @Transactional
    public String deleteMedication(Long id) {
        log.info("Attempting to delete medication with ID: {}", id);
        if (!medicationRepository.existsById(id)) {
            log.warn("Medication not found with ID: {}", id);
            throw new CustomException("Medication not found with ID " + id);
        }
        medicationRepository.deleteById(id);
        log.info("Medication with ID {} has been successfully deleted.", id);
        return "Medication with ID " + id + " has been successfully deleted.";
    }
}