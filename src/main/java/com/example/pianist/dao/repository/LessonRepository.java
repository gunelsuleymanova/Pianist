package com.example.pianist.dao.repository;

import com.example.pianist.dao.entity.LessonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LessonRepository extends JpaRepository<LessonEntity, Long> {
    List<LessonEntity> findByLevelOrderByOrderNumAsc(Integer level);
    List<LessonEntity> findAllByOrderByOrderNumAsc();
}