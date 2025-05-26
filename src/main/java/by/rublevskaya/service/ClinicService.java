package by.rublevskaya.service;

import by.rublevskaya.dto.clinic.ClinicDto;
import by.rublevskaya.dto.clinic.ClinicResponseDto;
import by.rublevskaya.dto.clinic.ClinicUpdateDto;
import by.rublevskaya.exception.CustomException;
import by.rublevskaya.mapper.ClinicMapper;
import by.rublevskaya.model.Clinic;
import by.rublevskaya.repository.ClinicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClinicService {
    private final ClinicRepository clinicRepository;
    private final ClinicMapper clinicMapper;

    public List<ClinicResponseDto> getAllClinics() {
        List<Clinic> clinics = clinicRepository.findAll();
        if (clinics.isEmpty()) {
            throw new CustomException("No clinics found.");
        }
        return clinicMapper.toDtoList(clinics);
    }

    public ClinicResponseDto addClinic(ClinicDto clinicCreateDto) {
        Clinic clinic = clinicMapper.toEntity(clinicCreateDto);
        Clinic savedClinic = clinicRepository.save(clinic);
        return clinicMapper.toDto(savedClinic);
    }

    public String deleteClinicById(Long id) {
        if (!clinicRepository.existsById(id)) {
            throw new CustomException("Clinic with ID " + id + " does not exist");
        }
        clinicRepository.deleteById(id);
        return "Clinic with ID " + id + " has been successfully deleted.";
    }

    public ClinicResponseDto getClinicById(Long id) {
        Clinic clinic = clinicRepository.findById(id).orElseThrow(
                () -> new CustomException("Clinic with ID " + id + " not found"));
        return clinicMapper.toDto(clinic);
    }

    public ClinicResponseDto updateClinic(Long id, ClinicDto updateDto) {
        Clinic clinic = clinicRepository.findById(id).orElseThrow(
                () -> new CustomException("Clinic with ID " + id + " not found"));

        clinic.setName(updateDto.getName());
        clinic.setAddress(updateDto.getAddress());
        clinic.setPhone(updateDto.getPhone());
        Clinic updatedClinic = clinicRepository.save(clinic);
        return clinicMapper.toResponseDto(updatedClinic);
    }

    public ClinicResponseDto partialUpdateClinic(Long id, ClinicUpdateDto updateDto) {
        Clinic clinic = clinicRepository.findById(id).orElseThrow(
                () -> new CustomException("Clinic with ID " + id + " not found"));

        if (updateDto.getName() != null) {
            clinic.setName(updateDto.getName());
        }
        if (updateDto.getAddress() != null) {
            clinic.setAddress(updateDto.getAddress());
        }
        if (updateDto.getPhone() != null) {
            clinic.setPhone(updateDto.getPhone());
        }
        Clinic updatedClinic = clinicRepository.save(clinic);
        return clinicMapper.toResponseDto(updatedClinic);
    }
}