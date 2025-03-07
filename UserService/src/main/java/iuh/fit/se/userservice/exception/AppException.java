package iuh.fit.se.userservice.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AppException extends RuntimeException {
    ErrorCode errorCode;
    String customMessage;

    public AppException(ErrorCode errorCode, String customMessage) {
        super(errorCode + " " + customMessage);
        this.errorCode = errorCode;
        this.customMessage = customMessage;
    }

    public AppException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.customMessage = null;
    }


}

