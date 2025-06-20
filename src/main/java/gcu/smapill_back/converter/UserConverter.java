package gcu.smapill_back.converter;

import gcu.smapill_back.domain.User;
import gcu.smapill_back.domain.enums.ModeStatus;
import gcu.smapill_back.web.dto.UserRequestDTO;
import gcu.smapill_back.web.dto.UserResponseDTO;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RequiredArgsConstructor
public class UserConverter {
    public static User toUserResultDTO(UserRequestDTO.UserJoinDTO request) {
        return User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .mode(ModeStatus.NORMAL)
                .userId(request.getUserId())
                .phoneNumber(request.getPhoneNumber())
                .build();
    }

    public static UserResponseDTO.UserJoinResultDTO toJoinResultDTO(User user) {
        return UserResponseDTO.UserJoinResultDTO.builder()
                .id(user.getId())
                .userId(user.getUserId())
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static UserResponseDTO.UserUpdateResultDTO toUpdateUserResultDTO(User user) {
        return UserResponseDTO.UserUpdateResultDTO.builder()
                .userId(user.getId())
                .createdAt(LocalDateTime.now())
                .mode(user.getMode().toString())
                .build();
    }

    public static UserResponseDTO.UserProtectorDetailDTO toProtectorDetail(User user) {
        return UserResponseDTO.UserProtectorDetailDTO.builder()
                .id(user.getId())
                .userId(user.getUserId())
                .name(user.getName())
                .phoneNumber(user.getPhoneNumber())
                .build();
    }

    public static UserResponseDTO.UserDependentDetailDTO toDependentDetail(User user) {
        return UserResponseDTO.UserDependentDetailDTO.builder()
                .id(user.getId())
                .userId(user.getUserId())
                .name(user.getName())
                .phoneNumber(user.getPhoneNumber())
                .build();

    }

    public static UserResponseDTO.UserDetailResultDTO toGetUserDetail(User user) {
        return UserResponseDTO.UserDetailResultDTO.builder()
                .id(user.getId())
                .userId(user.getUserId())
                .name(user.getName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .createdAt(user.getCreatedAt())
                .build();
    }
}
