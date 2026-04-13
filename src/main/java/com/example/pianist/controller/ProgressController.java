package com.example.pianist.controller;

import com.example.pianist.model.response.ApiResponse;
import com.example.pianist.model.response.ProgressResponse;
import com.example.pianist.service.ProgressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/piano/progress")
@RequiredArgsConstructor
public class ProgressController {

    private final ProgressService progressService;

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<List<ProgressResponse>>> getMyProgress(
            @AuthenticationPrincipal String email) {
        return ResponseEntity.ok(
                ApiResponse.ok("Progress məlumatları", progressService.getMyProgress(email))
        );
    }
}