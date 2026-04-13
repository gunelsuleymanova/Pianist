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
public class UserProfileResponse {
    private Long id;
    private String username;
    private String email;
    private Integer score;
    private Integer currentLevel;
    private String role;
    private Integer completedLessons;
    private LocalDateTime createdAt;
}