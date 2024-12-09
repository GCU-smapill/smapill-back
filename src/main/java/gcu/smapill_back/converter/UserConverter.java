package gcu.smapill_back.converter;

import gcu.smapill_back.domain.User;
import gcu.smapill_back.web.dto.UserRequestDTO;
import gcu.smapill_back.web.dto.UserResponseDTO;
import lombok.*;

import java.time.LocalDateTime;

@RequiredArgsConstructor
public class UserConverter {
    public static User toUserResultDTO(UserRequestDTO.UserJoinDTO request) {
        return User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .build();
    }

    public static UserResponseDTO.UserJoinResultDTO toJoinResultDTO(User user) {
        return UserResponseDTO.UserJoinResultDTO.builder()
                .userId(user.getId())
                .createdAt(LocalDateTime.now())
                .build();
    }
}
