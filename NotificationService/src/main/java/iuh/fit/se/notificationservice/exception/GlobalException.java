package iuh.fit.se.notificationservice.exception;

import iuh.fit.se.notificationservice.dto.response.DataResponse;
import jakarta.validation.ConstraintViolation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

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
        DataResponse dataResponse = DataResponse.builder().status(ErrorCode.UNCATEGORIZED_ERROR.getCode()).message(ErrorCode.UNCATEGORIZED_ERROR.getMessage()).build();
        return ResponseEntity.status(ErrorCode.UNCATEGORIZED_ERROR.getHttpStatus()).body(dataResponse);
    }

    // Handle AppException Custom
    @ExceptionHandler(AppException.class)
    public ResponseEntity<DataResponse> handleAppException(AppException ex) {
        String message = ex.getCustomMessage() != null ? ex.getErrorCode().getMessage() + " " + ex.getCustomMessage() : ex.getErrorCode().getMessage();
        DataResponse dataResponse = DataResponse.builder()
                .status(ex.getErrorCode().getCode())
                .message(message)
                .build();
        return ResponseEntity.status(ex.getErrorCode().getHttpStatus()).body(dataResponse);
    }

    // Handle NoResourceFoundException File
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<DataResponse> handleNoResourceFoundException() {
        DataResponse dataResponse = DataResponse.builder().status(ErrorCode.NO_RESOURCE_FOUND.getCode()).message(ErrorCode.NO_RESOURCE_FOUND.getMessage()).build();
        return ResponseEntity.status(ErrorCode.NO_RESOURCE_FOUND.getHttpStatus()).body(dataResponse);
    }

    // Handle File Exception
    @ExceptionHandler({URISyntaxException.class, IOException.class})
    public ResponseEntity<DataResponse> handleFileException() {
        DataResponse dataResponse = DataResponse.builder().status(ErrorCode.CREATE_DIRECTORY_FAILED.getCode()).message(ErrorCode.CREATE_DIRECTORY_FAILED.getMessage()).build();
        return ResponseEntity.status(ErrorCode.CREATE_DIRECTORY_FAILED.getHttpStatus()).body(dataResponse);
    }


    // Handle Validation Exception
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<DataResponse> handleValidationException(MethodArgumentNotValidException ex) {
        ErrorCode errorCode;
        Map<String, Object> attributes = null;

        try {
            var fieldError = ex.getBindingResult().getFieldError();
            if (fieldError != null) {
                errorCode = ErrorCode.valueOf(fieldError.getDefaultMessage());
                var constraint = ex.getBindingResult().getAllErrors().get(0).unwrap(ConstraintViolation.class);
                attributes = constraint.getConstraintDescriptor().getAttributes();
            } else {
                errorCode = ErrorCode.INVALID_KEY;
            }
        } catch (Exception e) {
            errorCode = ErrorCode.INVALID_KEY;
        }

        DataResponse dataResponse = DataResponse.builder()
                .status(errorCode.getCode())
                .message(Objects.nonNull(attributes) ? mapAttributeMessage(errorCode.getMessage(), attributes) : errorCode.getMessage())
                .build();

        return ResponseEntity.status(errorCode.getHttpStatus()).body(dataResponse);
    }

    private String mapAttributeMessage(String message, Map<String, Object> attributes) {
        String minValue = String.valueOf(attributes.get(MIN_ATTRIBUTE));
        String maxValue = String.valueOf(attributes.get(MAX_ATTRIBUTE));
        return message.replace("{" + MIN_ATTRIBUTE + "}", minValue)
                .replace("{" + MAX_ATTRIBUTE + "}", maxValue);
    }

}
