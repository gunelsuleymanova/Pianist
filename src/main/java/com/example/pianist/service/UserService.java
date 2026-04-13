package com.example.pianist.service;

import com.example.pianist.dao.entity.UserEntity;
import com.example.pianist.dao.repository.UserProgressRepository;
import com.example.pianist.dao.repository.UserRepository;
import com.example.pianist.exceptions.UserNotFoundException;
import com.example.pianist.model.response.UserProfileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserProgressRepository userProgressRepository;

    public UserProfileResponse getMyProfile(String email) {
        return toResponse(findByEmail(email));
    }

    public UserProfileResponse getById(Long id) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("İstifadəçi tapılmadı: ID=" + id));
        return toResponse(user);
    }

    public List<UserProfileResponse> getAll() {
        return userRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException("İstifadəçi tapılmadı: ID=" + id);
        }
        userRepository.deleteById(id);
    }

    public UserEntity findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("İstifadəçi tapılmadı"));
    }

    private UserProfileResponse toResponse(UserEntity user) {
        int completed = Math.toIntExact(
                userProgressRepository.countByUserIdAndCompletedTrue(user.getId())
        );
        return UserProfileResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .score(user.getScore())
                .currentLevel(user.getCurrentLevel())
                .role(user.getRole())
                .completedLessons(completed)
                .createdAt(user.getCreatedAt())
                .build();
    }
}