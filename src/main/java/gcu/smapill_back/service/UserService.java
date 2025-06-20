package gcu.smapill_back.service;

import gcu.smapill_back.apiPayload.code.status.ErrorStatus;
import gcu.smapill_back.apiPayload.exception.handler.UserHandler;
import gcu.smapill_back.config.jwt.JwtExceptionHandler;
import gcu.smapill_back.config.jwt.JwtUtil;
import gcu.smapill_back.converter.UserConverter;
import gcu.smapill_back.domain.User;
import gcu.smapill_back.repository.UserRepository;
import gcu.smapill_back.repository.UserLinkRepository;
import gcu.smapill_back.web.dto.UserRequestDTO;
import gcu.smapill_back.web.dto.UserResponseDTO;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Random;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserLinkRepository userLinkRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final RedisTemplate redisTemplate;

    @Transactional
    public User joinUser(UserRequestDTO.UserJoinDTO request) {
        LocalDate today = LocalDate.now();

        User newUser = UserConverter.toUserResultDTO(request);
        newUser.encodePassword(passwordEncoder.encode(request.getPassword()));
        System.out.println("User Password: " + newUser.getPassword());
        return userRepository.save(newUser);
    }

    @Transactional
    public void checkPassword(String password, String verifyPassword) {
        if (!password.equals(verifyPassword)) {
            throw new UserHandler(ErrorStatus.PASSWORD_NOT_MATCH);
        }
    }

    public UserResponseDTO.LoginJwtTokenDTO loginUser(UserRequestDTO.UserLoginDTO loginDTO) {
        String userId = loginDTO.getUserId();

        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new UserHandler(ErrorStatus.NO_USER_EXIST));

        if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
            throw new UserHandler(ErrorStatus.PASSWORD_NOT_MATCH);
        }

        String accessToken = JwtUtil.createAccessToken(user.getId(), user.getUserId());

        return UserResponseDTO.LoginJwtTokenDTO.builder()
                .grantType("Bearer")
                .accessToken(accessToken)
                .build();
    }

    public void logoutUser(String accessToken) {
        try {
            jwtUtil.validateToken(accessToken);
        } catch (JwtException e) {
            throw new JwtExceptionHandler(ErrorStatus.NOT_VALID_TOKEN.getMessage());
        }

        redisTemplate.opsForValue().set(accessToken, "logout");
    }

    @Transactional
    public void withdrawer(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserHandler(ErrorStatus.NO_USER_EXIST));

        // 유저와 연관된 userLink 삭제 (JPQL bulk delete)
        userLinkRepository.deleteAllByProtectorId(id);
        userLinkRepository.deleteAllByDependentId(id);

        // 유저 삭제
        userRepository.delete(user);
    }



    public UserResponseDTO.UserDetailResultDTO getUserDetail(String userId) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new UserHandler(ErrorStatus.NO_USER_EXIST));
        return UserConverter.toGetUserDetail(user);
    }

    @Transactional
    public UserResponseDTO.UserUpdateResultDTO updateMode(Long id, String mode) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserHandler(ErrorStatus.NO_USER_EXIST));

        user.setMode(mode);
        userRepository.save(user);

        return UserResponseDTO.UserUpdateResultDTO.builder()
                .mode(user.getMode().toString())
                .userId(user.getId())
                .createdAt(user.getCreatedAt())
                .build();
    }
}