package by.rublevskaya.service;

import by.rublevskaya.dto.medication.MedicationDto;
import by.rublevskaya.dto.medication.MedicationResponseDto;
import by.rublevskaya.exception.CustomException;
import by.rublevskaya.mapper.MedicationMapper;
import by.rublevskaya.model.Medication;
import by.rublevskaya.repository.MedicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MedicationCrudService {

    private final MedicationRepository medicationRepository;
    private final MedicationMapper medicationMapper;

    @Transactional
    public MedicationResponseDto createMedication(MedicationDto medicationDto) {
        Medication medication = medicationMapper.toEntity(medicationDto);
        boolean exists = medicationRepository.existsByUserIdAndNameAndDosageAndStartDateAndEndDate(
                medication.getUserId(),
                medication.getName(),
                medication.getDosage(),
                medication.getStartDate(),
                medication.getEndDate()
        );
        if (exists) {
            throw new CustomException(
                    "Duplicate medication with the same details already exists for this user.");
        }
        Medication savedMedication = medicationRepository.save(medication);
        return medicationMapper.toResponseDto(savedMedication);
    }

    @Transactional(readOnly = true)
    public List<MedicationResponseDto> getMedicationsByUserId(Long userId) {
        List<Medication> medications = medicationRepository.findByUserId(userId);
        return medications.stream()
                .map(medicationMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public MedicationResponseDto getMedicationById(Long id) {
        Medication medication = medicationRepository.findById(id)
                .orElseThrow(() -> new CustomException("Medication not found with ID " + id));
        return medicationMapper.toResponseDto(medication);
    }

    @Transactional
    public MedicationResponseDto updateMedication(Long id, MedicationDto medicationDto) {
        Medication medication = medicationRepository.findById(id)
                .orElseThrow(() -> new CustomException("Medication not found with ID " + id));
        medicationMapper.updateEntity(medication, medicationDto);
        Medication updatedMedication = medicationRepository.save(medication);
        return medicationMapper.toResponseDto(updatedMedication);
    }

    @Transactional
    public String deleteMedication(Long id) {
        if (!medicationRepository.existsById(id)) {
            throw new CustomException("Medication not found with ID " + id);
        }
        medicationRepository.deleteById(id);
        return "Medication with ID " + id + " has been successfully deleted.";
    }
}