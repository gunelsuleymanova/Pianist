package com.example.pianist.controller;

import com.example.pianist.model.request.LessonRequest;
import com.example.pianist.model.response.ApiResponse;
import com.example.pianist.model.response.LessonResponse;
import com.example.pianist.service.LessonService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/piano/lessons")
@RequiredArgsConstructor
public class LessonController {

    private final LessonService lessonService;

    // Bütün authenticated userlər görə bilər
    @GetMapping
    public ResponseEntity<ApiResponse<List<LessonResponse>>> getAll() {
        return ResponseEntity.ok(ApiResponse.ok("Dərslər", lessonService.getAllActive()));
    }

    @GetMapping("/level/{level}")
    public ResponseEntity<ApiResponse<List<LessonResponse>>> getByLevel(@PathVariable Integer level) {
        return ResponseEntity.ok(ApiResponse.ok("Dərslər", lessonService.getByLevel(level)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<LessonResponse>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.ok("Dərs", lessonService.getById(id)));
    }

    // Yalnız ADMIN
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<LessonResponse>> create(@Valid @RequestBody LessonRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok("Dərs yaradıldı", lessonService.create(request)));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<LessonResponse>> update(
            @PathVariable Long id, @Valid @RequestBody LessonRequest request) {
        return ResponseEntity.ok(ApiResponse.ok("Dərs yeniləndi", lessonService.update(id, request)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        lessonService.delete(id);
        return ResponseEntity.ok(ApiResponse.ok("Dərs deaktiv edildi"));
    }
}