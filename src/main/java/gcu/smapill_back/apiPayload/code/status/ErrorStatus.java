package gcu.smapill_back.apiPayload.code.status;

import gcu.smapill_back.apiPayload.code.BaseErrorCode;
import gcu.smapill_back.apiPayload.code.ErrorReasonDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorStatus implements BaseErrorCode {

    _INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON500", "서버 에러, 관리자에게 문의 바랍니다."),
    _BAD_REQUEST(HttpStatus.BAD_REQUEST, "COMMON400", "잘못된 요청입니다."),
    _UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "COMMON401", "인증이 필요합니다."),
    _FORBIDDEN(HttpStatus.FORBIDDEN, "COMMON403", "금지된 요청입니다."),

    NO_USER_EXIST(HttpStatus.NOT_FOUND, "USER4001", "존재하지 않는 유저입니다."),
    PASSWORD_NOT_MATCH(HttpStatus.UNAUTHORIZED, "USER4002", "비밀번호가 일치하지 않습니다."),

    NO_PRESCRIPTION_EXIST(HttpStatus.NOT_FOUND, "PRESCRIPTION4001", "존재하지 않는 처방전입니다."),

    NO_USER_LINK_EXIST(HttpStatus.NOT_FOUND, "USERLINK4001", "존재하지 않는 연동정보입니다."),

    NO_SCHEDULE_EXIST(HttpStatus.NOT_FOUND, "SCHEDULE4001", "존재하지 않는 스케쥴입니다."),
    INVALID_DATA_RANGE(HttpStatus.BAD_REQUEST, "SCHEDULE 4002", "스케쥴 범위가 유효하지 않습니다."),

    WRONG_TYPE_SIGNATURE(HttpStatus.UNAUTHORIZED, "USER4011", "잘못된 JWT 서명입니다."),
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "USER4012", "토큰이 만료되었습니다."),
    WRONG_TYPE_TOKEN(HttpStatus.UNAUTHORIZED, "USER4013", "지원되지 않는 JWT 토큰입니다."),
    NOT_VALID_TOKEN(HttpStatus.UNAUTHORIZED, "USER4014", "유효한 토큰이 아닙니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public ErrorReasonDTO getReason() {
        return ErrorReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(false)
                .build();
    }

    @Override
    public ErrorReasonDTO getReasonHttpStatus() {
        return ErrorReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(false)
                .httpStatus(httpStatus)
                .build();
    }
}