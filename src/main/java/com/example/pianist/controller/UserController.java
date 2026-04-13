package com.example.pianist.controller;

import com.example.pianist.model.request.UserLoginRequest;
import com.example.pianist.model.request.UserRegisterRequest;
import com.example.pianist.model.response.ApiResponse;
import com.example.pianist.model.response.TokenResponse;
import com.example.pianist.model.response.UserProfileResponse;
import com.example.pianist.service.UserAuthService;
import com.example.pianist.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/piano")
public class UserController {

    private final UserAuthService userAuthService;
    private final UserService userService;

    @PostMapping("/save")
    public ResponseEntity<ApiResponse<Void>> register(@Valid @RequestBody UserRegisterRequest dto) {
        userAuthService.saveUser(dto);
        return ResponseEntity.ok(ApiResponse.ok("İstifadəçi qeydiyyatdan keçdi"));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<TokenResponse>> login(@Valid @RequestBody UserLoginRequest dto) {
        return ResponseEntity.ok(ApiResponse.ok("Giriş uğurlu oldu", userAuthService.login(dto)));
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserProfileResponse>> getMyProfile(
            @AuthenticationPrincipal String email) {
        return ResponseEntity.ok(ApiResponse.ok("Profil", userService.getMyProfile(email)));
    }

    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<UserProfileResponse>>> getAllUsers() {
        return ResponseEntity.ok(ApiResponse.ok("İstifadəçilər", userService.getAll()));
    }

    @DeleteMapping("/users/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok(ApiResponse.ok("İstifadəçi silindi"));
    }
}