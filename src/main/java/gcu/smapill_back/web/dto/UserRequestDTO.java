package gcu.smapill_back.web.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

public class UserRequestDTO {
    @Getter
    public static class UserJoinDTO {

        @NotNull(message = "이름을 입력하세요")
        @Length(max = 20)
        @Pattern(
                regexp = "^[ㄱ-ㅎㅏ-ㅣ가-힣]{1,20}$",
                message = "이름은 1~20자의 한글로 입력해주세요"
        )
        private String name;

        @NotBlank(message = "이메일을 입력하세요")
        @Length(max = 40)
        @Pattern(
                regexp = "\\w+@\\w+\\.\\w+(\\.\\w+)?",
                message = "이메일 형식을 올바르게 입력해주세요"
        )
        private String email;

        @NotBlank(message = "비밀번호를 입력하세요")
        @Pattern(
                regexp = "^[a-zA-Z0-9~!@#$%^&*()]{8,30}",
                message = "비밀번호는 영어 대소문자, 숫자, 특수 문자가 포함되어야 합니다"
        )
        @Length(min = 8, max = 20)
        private String password;
    }

    @Getter
    public static class UserLoginDTO {

        @NotBlank(message = "이메일을 입력하세요")
        @Pattern(
                regexp = "\\w+@\\w+\\.\\w+(\\.\\w+)?",
                message = "이메일 형식을 올바르게 입력해주세"
        )
        private String email;

        @NotBlank(message = "비밀번호를 입력하세요")
        @Length(min = 8, max = 20)
        @Pattern(
                regexp = "^[a-zA-Z0-9~!@#$%^&*()]{8,30}",
                message = "비밀번호는 영어 대소문자, 숫자, 특수 문자가 포함되어야 합니다"
        )
        private String password;
    }
}
