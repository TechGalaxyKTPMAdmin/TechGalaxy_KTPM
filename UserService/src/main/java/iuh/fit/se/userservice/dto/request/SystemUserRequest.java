package iuh.fit.se.userservice.dto.request;

import iuh.fit.se.userservice.entities.enumeration.Gender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
public class SystemUserRequest {
    	 private String id;

    	    @NotBlank(message = "NAME_NOT_EMPTY")
    	    @Size(max = 255, message = "NAME_MAX_LENGTH_255")
    	    private String name;

    	    @Email(message = "INVALID_EMAIL_FORMAT")
    	    @Size(max = 255, message = "EMAIL_MAX_LENGTH_255")
    	    private String email;

    	    private Gender gender;

    	    @Size(max = 255, message = "ADDRESS_MAX_LENGTH_255")
    	    private String address;

    	    @Min(value = 0, message = "AGE_MUST_BE_POSITIVE")
    	    @Max(value = 150, message = "AGE_TOO_LARGE")
    	    private int age;

    	    private LocalDateTime createdAt;
}
