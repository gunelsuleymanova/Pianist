package com.example.pianist.controller;

import com.example.pianist.model.request.GameRequest;
import com.example.pianist.model.response.ApiResponse;
import com.example.pianist.model.response.UserProfileResponse;
import com.example.pianist.service.GameService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/piano/game")
@RequiredArgsConstructor
/// oyyun neticesi ucun
public class GameController {

    private final GameService gameService;

    @PostMapping("/win")
    public ResponseEntity<ApiResponse<UserProfileResponse>> win(
            @AuthenticationPrincipal String email, /// avtomatik olaraq authenticated userin emailini alır
            @Valid @RequestBody GameRequest request) {
        return ResponseEntity.ok(
                ApiResponse.ok("+" + request.getPoints() + " xal!", gameService.win(email, request))
        );
    }

    @PostMapping("/lose")
    public ResponseEntity<ApiResponse<UserProfileResponse>> lose(
            @AuthenticationPrincipal String email,
            @Valid @RequestBody GameRequest request) {
        return ResponseEntity.ok(
                ApiResponse.ok("-" + request.getPoints() + " xal", gameService.lose(email, request))
        );
    }
}