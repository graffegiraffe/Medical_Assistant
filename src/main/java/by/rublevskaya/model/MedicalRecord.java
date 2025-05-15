package by.rublevskaya.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "medical_records", schema = "medical_assistant")
public class MedicalRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId; // Ссылка на пользователя

    @Column(name = "doctor_id", nullable = false)
    private Long doctorId; // Ссылка на врача

    @Column(nullable = false)
    private String title;

    private LocalDate date;

    private String description;

    @Column(name = "doctor_notes")
    private String doctorNotes;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RecordType type; // ALLERGY, VACCINE, ILLNESS

    @CreationTimestamp
    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public enum RecordType {
        ALLERGY, VACCINE, ILLNESS
    }
}