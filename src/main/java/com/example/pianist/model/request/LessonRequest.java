package com.example.pianist.model.request;

import com.example.pianist.model.enums.Difficulty;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class LessonRequest {

    @NotBlank(message = "Başlıq boş ola bilməz")
    @Size(min = 2, max = 100)
    private String title;

    private String content;

    @Min(1) @Max(10)
    private Integer level;

    private Integer orderNum;
    private Integer duration;
    private String audioFile;
    private String sheetMusicImage;

    @NotNull(message = "Çətinlik dərəcəsi boş ola bilməz")
    private Difficulty difficulty;

    @NotNull(message = "XP mükafatı boş ola bilməz")
    @Min(1) @Max(500)
    private Integer xpReward;
}