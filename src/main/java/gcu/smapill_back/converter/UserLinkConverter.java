package gcu.smapill_back.converter;

import gcu.smapill_back.domain.User;
import gcu.smapill_back.domain.mapping.UserLink;
import gcu.smapill_back.web.dto.UserLinkResponseDTO;
import gcu.smapill_back.web.dto.UserResponseDTO;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
public class UserLinkConverter {
    public static UserLink toUserLinkResultDTO(User dependent, User protector) {
        return UserLink.builder()
                .dependent(dependent)
                .protector(protector)
                .build();
    }

    public static UserLinkResponseDTO.UserLinkJoinResultDTO toJoinResultDTO(UserLink userLink) {
        return UserLinkResponseDTO.UserLinkJoinResultDTO.builder()
                .userLinkId(userLink.getId())
                .createdAt(LocalDateTime.now())
                .build();
    }
}

