package iuh.fit.se.apigateway.exception;

import iuh.fit.se.apigateway.dto.response.DataResponse;
import jakarta.validation.ConstraintViolation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.reactive.resource.NoResourceFoundException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.Objects;

@ControllerAdvice
@Slf4j
public class GlobalException {
    private static final String MIN_ATTRIBUTE = "min";
    private static final String MAX_ATTRIBUTE = "max";

    // Handle all exception
    @ExceptionHandler(Exception.class)
    public ResponseEntity<DataResponse> handleException() {
        DataResponse dataResponse = DataResponse.builder().status(ErrorCode.UNCATEGORIZED_ERROR.getCode())
                .message(ErrorCode.UNCATEGORIZED_ERROR.getMessage()).build();
        return ResponseEntity.status(ErrorCode.UNCATEGORIZED_ERROR.getHttpStatus()).body(dataResponse);
    }

    // Handle AppException Custom
    @ExceptionHandler(AppException.class)
    public ResponseEntity<DataResponse> handleAppException(AppException ex) {
        String message = ex.getCustomMessage() != null ? ex.getErrorCode().getMessage() + " " + ex.getCustomMessage()
                : ex.getErrorCode().getMessage();
        DataResponse dataResponse = DataResponse.builder()
                .status(ex.getErrorCode().getCode())
                .message(message)
                .build();
        return ResponseEntity.status(ex.getErrorCode().getHttpStatus()).body(dataResponse);
    }

}
