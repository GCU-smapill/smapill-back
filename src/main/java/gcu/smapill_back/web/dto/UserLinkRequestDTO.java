package gcu.smapill_back.web.dto;

import gcu.smapill_back.domain.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

public class UserLinkRequestDTO {
    @Getter
    public static class UserLinkJoinDTO {
        String userId;
        String password;
    }
}