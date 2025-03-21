package gcu.smapill_back.web.dto;

import gcu.smapill_back.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class UserLinkResponseDTO {
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserLinkJoinResultDTO {
        Long userLinkId;
        LocalDateTime createdAt;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserLinkDetailResultDTO {
        LocalDate createdAt;
        List<Long> protectorIdList;
        //String email;
        //String name;
    }

    //@Builder
    //@Getter
    //@NoArgsConstructor
    //@AllArgsConstructor
    //public static class UserLinkDetailResultListDTO {
    //    List<UserLinkResponseDTO.UserLinkDetailResultDTO> userLinks;
    //}
}
