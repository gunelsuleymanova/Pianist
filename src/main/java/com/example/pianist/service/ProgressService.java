package com.example.pianist.service;

import com.example.pianist.dao.entity.UserEntity;
import com.example.pianist.dao.entity.UserProgress;
import com.example.pianist.dao.repository.UserProgressRepository;
import com.example.pianist.model.response.ProgressResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProgressService {

    private final UserService userService;
    private final UserProgressRepository userProgressRepository;

    public List<ProgressResponse> getMyProgress(String email) {
        UserEntity user = userService.findByEmail(email);
        return userProgressRepository.findByUserIdOrderByLessonOrderNumAsc(user.getId())
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // Streak məntiqi: dünən practice etmişsə artır, fasilə varsa sıfırla
    public void updateStreak(UserProgress progress) {
        LocalDateTime lastPracticed = progress.getLastPracticed();

        if (lastPracticed == null) {
            progress.setStreakDays(1);
            progress.setLastPracticed(LocalDateTime.now());
            return;
        }

        LocalDate lastDate = lastPracticed.toLocalDate();
        LocalDate today = LocalDate.now();

        if (lastDate.equals(today.minusDays(1))) {
            progress.setStreakDays((progress.getStreakDays() == null ? 0 : progress.getStreakDays()) + 1);
        } else if (lastDate.equals(today)) {
            progress.setLastPracticed(LocalDateTime.now());
            // Bu gün artıq edilib — dəyişmə
        } else {
            progress.setLastPracticed(LocalDateTime.now());
            progress.setStreakDays(1);
        }
    }

    private ProgressResponse toResponse(UserProgress p) {
        return ProgressResponse.builder()
                .id(p.getId())
                .lessonId(p.getLesson().getId())
                .lessonTitle(p.getLesson().getTitle())
                .lessonLevel(p.getLesson().getLevel())
                .completed(p.getCompleted())
                .attempts(p.getAttempts())
                .accuracy(p.getAccuracy())
                .stars(p.getStars())
                .scoreEarned(p.getScoreEarned())
                .streakDays(p.getStreakDays())
                .lastPracticed(p.getLastPracticed())
                .tempoBpm(p.getTempoBpm())
                .noteAccuracy(p.getNoteAccuracy())
                .rhythmScore(p.getRhythmScore())
                .practiceTime(p.getPracticeTime())
                .build();
    }
}