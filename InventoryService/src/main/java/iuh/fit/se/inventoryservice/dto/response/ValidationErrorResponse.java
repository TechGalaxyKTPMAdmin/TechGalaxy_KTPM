package iuh.fit.se.inventoryservice.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Collection;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ValidationErrorResponse extends DataResponse<FieldErrorResponse> {

    public ValidationErrorResponse(int status, String message, Collection<FieldErrorResponse> errors) {
        super(status, message, errors);
    }

    public static ValidationErrorResponse of(int status, String message, Collection<FieldErrorResponse> errors) {
        return new ValidationErrorResponse(status, message, errors);
    }
}