package com.example.pianist.dao.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_progress")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProgress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lesson_id", nullable = false)
    private LessonEntity lesson;

    private Boolean completed = false;
    private Integer attempts = 0;
    private Double accuracy = 0.0;    // 0-100
    private Integer stars = 0;        // 0-4 ulduz
    private Integer streakDays = 0;
    private LocalDateTime lastPracticed;

    private Integer scoreEarned = 0;  // + qazandı, - uduzdu

    // Piano-specific
    private Integer tempoBpm;
    private Double noteAccuracy;
    private Double rhythmScore;
    private Integer practiceTime;

    @CreationTimestamp
    private LocalDateTime createdAt;
}