package com.example.pianist.model.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class GameRequest {

    @NotNull(message = "Lesson ID boş ola bilməz")
    private Long lessonId;

    @Min(value = 1, message = "Xal minimum 1 olmalıdır")
    private Integer points;
}