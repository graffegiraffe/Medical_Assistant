package by.rublevskaya.dto.medicalrecord;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDate;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MedicalRecordResponseDto {
    private String title;
    private LocalDate date;
    private String description;
    private String doctorNotes;
    private String type;
}
