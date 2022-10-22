package br.com.fiap.belive_backend.advice;

import br.com.fiap.belive_backend.exception.UserNotFoundException;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.Date;
import java.util.stream.Collectors;

public class GlobalHandlerException {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(MethodArgumentNotValidException methodArgumentNotValidException) {
        HttpStatus errorCode = HttpStatus.BAD_REQUEST;

        String errorsMessage = methodArgumentNotValidException.getAllErrors().stream()
                .map(err -> ((FieldError) err).getField() + ": " + err.getDefaultMessage())
                .collect(Collectors.joining(", "));

        ErrorResponse errorResponse = ErrorResponse.builder()
                .error(errorCode.name())
                .timestamp(Date.from(Instant.now()))
                .message(errorsMessage)
                .status(errorCode.value())
                .build();

        return new ResponseEntity<>(errorResponse, errorCode);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUsernameNotFoundException(UsernameNotFoundException usernameNotFoundException){
        HttpStatus errorCode = HttpStatus.UNAUTHORIZED;

        ErrorResponse errorResponse = ErrorResponse.builder()
                .error(errorCode.name())
                .timestamp(Date.from(Instant.now()))
                .status(errorCode.value())
                .message(usernameNotFoundException.getLocalizedMessage())
                .build();

        return new ResponseEntity<>(errorResponse, errorCode);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException userNotFoundException){
        HttpStatus errorCode = HttpStatus.NOT_FOUND;

        ErrorResponse errorResponse = ErrorResponse.builder()
                .error(errorCode.name())
                .timestamp(Date.from(Instant.now()))
                .status(errorCode.value())
                .message(userNotFoundException.getLocalizedMessage())
                .build();

        return new ResponseEntity<>(errorResponse, errorCode);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception exception) {
        HttpStatus errorCode = HttpStatus.INTERNAL_SERVER_ERROR;
        ErrorResponse errorResponse = ErrorResponse.builder()
                .error(errorCode.name())
                .timestamp(Date.from(Instant.now()))
                .status(errorCode.value())
                .message(exception.getLocalizedMessage())
                .build();
        return new ResponseEntity<>(errorResponse, errorCode);
    }

    @Builder
    @Data
    public static class ErrorResponse {
        private String error;

        private Integer status;

        private String message;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
        private Date timestamp;
    }
}
