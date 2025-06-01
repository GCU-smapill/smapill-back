package gcu.smapill_back.apiPayload.exception.handler;

import gcu.smapill_back.apiPayload.code.ErrorReasonDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(GeneralException.class)
  public ResponseEntity<ErrorReasonDTO> handleGeneralException(GeneralException e) {
    return ResponseEntity
            .status(e.getErrorReasonHttpStatus().getHttpStatus()) // ⬅ 여기서 httpStatus 직접 세팅
            .body(e.getErrorReasonHttpStatus());
  }
}