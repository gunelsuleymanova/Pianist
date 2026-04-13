package com.example.pianist.dao.repository;

import com.example.pianist.dao.entity.UserProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserProgressRepository extends JpaRepository<UserProgress, Long> {
    List<UserProgress> findByUserIdOrderByLessonOrderNumAsc(Long userId);
    Optional<UserProgress> findByUserIdAndLessonId(Long userId, Long lessonId);
    Long countByUserIdAndCompletedTrue(Long userId);
}