package com.example.pianist.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProgressResponse {
    private Long id;
    private Long lessonId;
    private String lessonTitle;
    private Integer lessonLevel;
    private Boolean completed;
    private Integer attempts;
    private Double accuracy;
    private Integer stars;
    private Integer scoreEarned;
    private Integer streakDays;
    private LocalDateTime lastPracticed;
    private Integer tempoBpm;
    private Double noteAccuracy;
    private Double rhythmScore;
    private Integer practiceTime;
}