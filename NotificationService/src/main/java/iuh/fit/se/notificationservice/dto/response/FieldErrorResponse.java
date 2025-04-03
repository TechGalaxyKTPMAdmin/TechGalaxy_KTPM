package iuh.fit.se.notificationservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FieldErrorResponse {
    private String field;
    private String message;
}