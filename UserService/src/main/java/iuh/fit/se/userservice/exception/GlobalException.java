package iuh.fit.se.userservice.exception;

import iuh.fit.se.userservice.dto.response.DataResponse;
import iuh.fit.se.userservice.dto.response.FieldErrorResponse;
import iuh.fit.se.userservice.dto.response.ValidationErrorResponse;
import jakarta.validation.ConstraintViolation;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

@ControllerAdvice
@Slf4j
public class GlobalException {
    private static final String MIN_ATTRIBUTE = "min";
    private static final String MAX_ATTRIBUTE = "max";

    // Handle all exception
    @ExceptionHandler(Exception.class)
    public ResponseEntity<DataResponse> handleException() {
        System.out.println("Exception: " + Exception.class.getName() + " occurred");
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

    // Handle NoResourceFoundException File
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<DataResponse> handleNoResourceFoundException() {
        DataResponse dataResponse = DataResponse.builder().status(ErrorCode.NO_RESOURCE_FOUND.getCode())
                .message(ErrorCode.NO_RESOURCE_FOUND.getMessage()).build();
        return ResponseEntity.status(ErrorCode.NO_RESOURCE_FOUND.getHttpStatus()).body(dataResponse);
    }

    // Handle File Exception
    @ExceptionHandler({ URISyntaxException.class, IOException.class })
    public ResponseEntity<DataResponse> handleFileException() {
        DataResponse dataResponse = DataResponse.builder().status(ErrorCode.CREATE_DIRECTORY_FAILED.getCode())
                .message(ErrorCode.CREATE_DIRECTORY_FAILED.getMessage()).build();
        return ResponseEntity.status(ErrorCode.CREATE_DIRECTORY_FAILED.getHttpStatus()).body(dataResponse);
    }

    // Handle DataIntegrityViolationException
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<DataResponse> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        if (ex.getCause() instanceof ConstraintViolationException) {
            ErrorCode errorCode = ErrorCode.DATA_INTEGRITY_VIOLATION_EXCEPTION;
            return ResponseEntity.status(errorCode.getHttpStatus())
                    .body(DataResponse.builder()
                            .status(errorCode.getCode())
                            .message(errorCode.getMessage())
                            .build());
        }

        log.error("DataIntegrityViolationException: {}", ex.getMessage());
        ErrorCode errorCode = ErrorCode.UNCATEGORIZED_ERROR;

        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(DataResponse.builder()
                        .status(errorCode.getCode())
                        .message(errorCode.getMessage())
                        .build());
    }

    // Handle Validation Exception
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
        int status = ErrorCode.INVALID_KEY.getCode();
        String message = "Validation failed";

        List<FieldErrorResponse> fieldErrors = ex.getBindingResult().getFieldErrors().stream().map(fieldError -> {
            String field = fieldError.getField();
            String errorMessage;

            try {
                ErrorCode code = ErrorCode.valueOf(fieldError.getDefaultMessage());
                Map<String, Object> attributes = ex.getBindingResult().getAllErrors().get(0)
                        .unwrap(ConstraintViolation.class)
                        .getConstraintDescriptor().getAttributes();
                errorMessage = mapAttributeMessage(code.getMessage(), attributes);
                System.out.println("Error message: " + errorMessage);
            } catch (Exception e) {
                errorMessage = fieldError.getDefaultMessage();
            }

            return new FieldErrorResponse(field, errorMessage);
        }).toList();

        ValidationErrorResponse response = ValidationErrorResponse.of(
                status,
                message,
                fieldErrors);

        return ResponseEntity.status(ErrorCode.DATA_INTEGRITY_VIOLATION_EXCEPTION.getHttpStatus()).body(response);
    }

    private String mapAttributeMessage(String message, Map<String, Object> attributes) {
        String minValue = String.valueOf(attributes.get(MIN_ATTRIBUTE));
        String maxValue = String.valueOf(attributes.get(MAX_ATTRIBUTE));
        return message.replace("{" + MIN_ATTRIBUTE + "}", minValue)
                .replace("{" + MAX_ATTRIBUTE + "}", maxValue);
    }

}