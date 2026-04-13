package com.example.pianist.service;

import com.example.pianist.dao.entity.LessonEntity;
import com.example.pianist.dao.repository.LessonRepository;
import com.example.pianist.exceptions.ResourceNotFoundException;
import com.example.pianist.model.request.LessonRequest;
import com.example.pianist.model.response.LessonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LessonService {

    private final LessonRepository lessonRepository;

    public List<LessonResponse> getAllActive() {
        return lessonRepository.findAllByOrderByOrderNumAsc()
                .stream()
                .filter(l -> Boolean.TRUE.equals(l.getIsActive()))
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public List<LessonResponse> getByLevel(Integer level) {
        return lessonRepository.findByLevelOrderByOrderNumAsc(level)
                .stream()
                .filter(l -> Boolean.TRUE.equals(l.getIsActive()))
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public LessonResponse getById(Long id) {
        return toResponse(findById(id));
    }

    @Transactional
    public LessonResponse create(LessonRequest req) {
        LessonEntity lesson = new LessonEntity();
        map(req, lesson);
        return toResponse(lessonRepository.save(lesson));
    }

    @Transactional
    public LessonResponse update(Long id, LessonRequest req) {
        LessonEntity lesson = findById(id);
        map(req, lesson);
        return toResponse(lessonRepository.save(lesson));
    }

    @Transactional
    public void delete(Long id) {
        LessonEntity lesson = findById(id);
        lesson.setIsActive(false);
        lessonRepository.save(lesson);
    }

    public LessonEntity findById(Long id) {
        return lessonRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Dərs tapılmadı: ID=" + id));
    }

    private void map(LessonRequest req, LessonEntity e) {
        e.setTitle(req.getTitle());
        e.setContent(req.getContent());
        e.setLevel(req.getLevel());
        e.setOrderNum(req.getOrderNum());
        e.setDuration(req.getDuration());
        e.setAudioFile(req.getAudioFile());
        e.setSheetMusicImage(req.getSheetMusicImage());
        e.setDifficulty(req.getDifficulty());
        e.setXpReward(req.getXpReward());
    }

    private LessonResponse toResponse(LessonEntity e) {
        return LessonResponse.builder()
                .id(e.getId())
                .title(e.getTitle())
                .content(e.getContent())
                .level(e.getLevel())
                .orderNum(e.getOrderNum())
                .duration(e.getDuration())
                .audioFile(e.getAudioFile())
                .sheetMusicImage(e.getSheetMusicImage())
                .difficulty(e.getDifficulty())
                .xpReward(e.getXpReward())
                .isActive(e.getIsActive())
                .createdAt(e.getCreatedAt())
                .build();
    }
}