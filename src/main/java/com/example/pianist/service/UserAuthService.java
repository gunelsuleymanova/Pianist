package com.example.pianist.service;


import com.example.pianist.config.JwtService;
import com.example.pianist.dao.entity.UserEntity;
import com.example.pianist.dao.repository.UserRepository;
import com.example.pianist.exceptions.InvalidCredentialsException;
import com.example.pianist.exceptions.UserNotFoundException;
import com.example.pianist.mapper.UserMapper;
import com.example.pianist.model.request.UserLoginRequest;
import com.example.pianist.model.request.UserRegisterRequest;
import com.example.pianist.model.response.TokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserAuthService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;


    public void saveUser(UserRegisterRequest dto) {
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Bu email artıq qeydiyyatdan keçib: " + dto.getEmail());
        }
        UserEntity user = userMapper.dtoToEntity(dto);
        String encodedPassword = passwordEncoder.encode(dto.getPassword());
        user.setPassword(encodedPassword);
        userRepository.save(user);
    }


    public TokenResponse login(UserLoginRequest request) {
        UserEntity user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UserNotFoundException("İstifadəçi tapılmadı!"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Şifrə yanlışdır!");
        }

        String accessToken = jwtService.generateAccessToken(user);
        ;
        String refreshToken = jwtService.generateRefreshToken(user);

        return new TokenResponse(accessToken, refreshToken);
    }

}

