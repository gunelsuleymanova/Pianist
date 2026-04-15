package com.example.pianist.service;

import com.example.pianist.dao.entity.LessonEntity;
import com.example.pianist.dao.entity.UserEntity;
import com.example.pianist.dao.entity.UserProgress;
import com.example.pianist.dao.repository.UserProgressRepository;
import com.example.pianist.dao.repository.UserRepository;
import com.example.pianist.model.request.GameRequest;
import com.example.pianist.model.response.UserProfileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GameService {

    private final UserService userService;
    private final ProgressService progressService;
    private final LessonService lessonService;
    private final UserRepository userRepository;
    private final UserProgressRepository userProgressRepository;

    @Transactional
    public UserProfileResponse win(String email, GameRequest request) {
        UserEntity user = userService.findByEmail(email);
        LessonEntity lesson = lessonService.findById(request.getLessonId());

        user.setScore(user.getScore() + request.getPoints());
        userRepository.save(user);

        saveProgress(user, lesson, true, request.getPoints());

        return userService.getMyProfile(email);
    }

    @Transactional
    public UserProfileResponse lose(String email, GameRequest request) {
        UserEntity user = userService.findByEmail(email);
        LessonEntity lesson = lessonService.findById(request.getLessonId());

        // Minimum 0 — mənfiyə düşmür
        int newScore = Math.max(0, user.getScore() - request.getPoints());
        user.setScore(newScore);
        userRepository.save(user);

        saveProgress(user, lesson, false, -request.getPoints());

        return userService.getMyProfile(email);
    }

    private void saveProgress(UserEntity user, LessonEntity lesson,
                              boolean completed, int scoreChange) {
        Optional<UserProgress> existing =
                userProgressRepository.findByUserIdAndLessonId(user.getId(), lesson.getId());

        UserProgress progress = existing.orElseGet(UserProgress::new);
        progress.setUser(user);
        progress.setLesson(lesson);
        progress.setCompleted(completed);
        progress.setScoreEarned(scoreChange);
        progress.setAttempts((progress.getAttempts() == null ? 0 : progress.getAttempts()) + 1);

        if(completed) {
            progressService.updateStreak(progress);
        }


        userProgressRepository.save(progress);
    }
}