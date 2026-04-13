package com.example.pianist.model.response;

import com.example.pianist.model.enums.Difficulty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LessonResponse {
    private Long id;
    private String title;
    private String content;
    private Integer level;
    private Integer orderNum;
    private Integer duration;
    private String audioFile;
    private String sheetMusicImage;
    private Difficulty difficulty;
    private Integer xpReward;
    private Boolean isActive;
    private LocalDateTime createdAt;
}