package by.rublevskaya.service;

import by.rublevskaya.dto.clinic.ClinicDto;
import by.rublevskaya.dto.clinic.ClinicResponseDto;
import by.rublevskaya.dto.clinic.ClinicUpdateDto;
import by.rublevskaya.exception.CustomException;
import by.rublevskaya.mapper.ClinicMapper;
import by.rublevskaya.model.Clinic;
import by.rublevskaya.repository.ClinicRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ClinicService {
    private final ClinicRepository clinicRepository;
    private final ClinicMapper clinicMapper;

    public List<ClinicResponseDto> getAllClinics() {
        log.info("Retrieving a list of all clinics.");
        List<Clinic> clinics = clinicRepository.findAll();
        if (clinics.isEmpty()) {
            log.warn("No clinics found in the database.");
            throw new CustomException("No clinics found.");
        }
        log.info("Successfully retrieved {} clinics.", clinics.size());
        return clinicMapper.toDtoList(clinics);
    }

    public ClinicResponseDto addClinic(ClinicDto clinicCreateDto) {
        log.info("Adding a new clinic. Name: {}, Address: {}", clinicCreateDto.getName(), clinicCreateDto.getAddress());
        Clinic clinic = clinicMapper.toEntity(clinicCreateDto);
        log.debug("Mapped ClinicDto to Clinic entity: {}", clinic);
        Clinic savedClinic = clinicRepository.save(clinic);
        log.info("Clinic successfully added with ID: {}", savedClinic.getId());
        return clinicMapper.toDto(savedClinic);
    }

    public String deleteClinicById(Long id) {
        log.info("Attempting to delete clinic with ID: {}", id);
        if (!clinicRepository.existsById(id)) {
            log.warn("Clinic with ID {} does not exist. Deletion aborted.", id);
            throw new CustomException("Clinic with ID " + id + " does not exist");
        }
        clinicRepository.deleteById(id);
        log.info("Clinic with ID {} has been successfully deleted.", id);
        return "Clinic with ID " + id + " has been successfully deleted.";
    }

    public ClinicResponseDto getClinicById(Long id) {
        log.info("Fetching details for clinic with ID: {}", id);
        Clinic clinic = clinicRepository.findById(id).orElseThrow(() -> {
            log.warn("Clinic with ID {} not found.", id);
            return new CustomException("Clinic with ID " + id + " not found");
        });
        log.info("Successfully retrieved details for clinic with ID: {}", id);
        return clinicMapper.toDto(clinic);
    }

    public ClinicResponseDto updateClinic(Long id, ClinicDto updateDto) {
        log.info("Updating clinic with ID: {}", id);
        Clinic clinic = clinicRepository.findById(id).orElseThrow(() -> {
            log.warn("Clinic with ID {} not found.", id);
            return new CustomException("Clinic with ID " + id + " not found");
        });
        log.debug("Updating clinic properties. New Name: {}, New Address: {}, New Phone: {}",
                updateDto.getName(), updateDto.getAddress(), updateDto.getPhone());

        clinic.setName(updateDto.getName());
        clinic.setAddress(updateDto.getAddress());
        clinic.setPhone(updateDto.getPhone());
        Clinic updatedClinic = clinicRepository.save(clinic);

        log.info("Clinic with ID {} successfully updated.", updatedClinic.getId());
        return clinicMapper.toResponseDto(updatedClinic);
    }

    public ClinicResponseDto partialUpdateClinic(Long id, ClinicUpdateDto updateDto) {
        log.info("Partially updating clinic with ID: {}", id);
        Clinic clinic = clinicRepository.findById(id).orElseThrow(() -> {
            log.warn("Clinic with ID {} not found.", id);
            return new CustomException("Clinic with ID " + id + " not found");
        });
        if (updateDto.getName() != null) {
            log.debug("Updating clinic name for ID {}: {}", id, updateDto.getName());
            clinic.setName(updateDto.getName());
        }
        if (updateDto.getAddress() != null) {
            log.debug("Updating clinic address for ID {}: {}", id, updateDto.getAddress());
            clinic.setAddress(updateDto.getAddress());
        }
        if (updateDto.getPhone() != null) {
            log.debug("Updating clinic phone for ID {}: {}", id, updateDto.getPhone());
            clinic.setPhone(updateDto.getPhone());
        }
        Clinic updatedClinic = clinicRepository.save(clinic);
        log.info("Clinic with ID {} successfully updated with partial fields.", updatedClinic.getId());
        return clinicMapper.toResponseDto(updatedClinic);
    }

    public List<ClinicResponseDto> getAllClinicsWithSorting(String field) {
        log.info("Retrieving all clinics sorted by field: {}", field);
        List<Clinic> sortedClinics = clinicRepository.findAll(Sort.by(Sort.Direction.ASC, field));
        if (sortedClinics.isEmpty()) {
            log.warn("No clinics found in the database for sorting by {}.", field);
            throw new CustomException("No clinics found.");
        }
        log.info("Successfully retrieved {} clinics sorted by {}.", sortedClinics.size(), field);
        return clinicMapper.toDtoList(sortedClinics);
    }

    public Page<ClinicResponseDto> getClinicsWithPagination(int page, int size) {
        log.info("Retrieving clinics with pagination. Page: {}, Size: {}", page, size);
        Page<Clinic> pagedClinics = clinicRepository.findAll(PageRequest.of(page, size));
        if (pagedClinics.isEmpty()) {
            log.warn("No clinics found on requested page: {}. Size: {}", page, size);
            throw new CustomException("No clinics found on the requested page.");
        }
        log.info("Successfully retrieved page {} with {} clinics.", page, pagedClinics.getContent().size());
        return pagedClinics.map(clinicMapper::toResponseDto);
    }
}