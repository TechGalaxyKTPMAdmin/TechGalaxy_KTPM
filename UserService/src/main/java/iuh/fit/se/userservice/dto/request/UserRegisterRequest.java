package iuh.fit.se.userservice.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterRequest {
    @NotBlank(message = "EMAIL_NOT_EMPTY")
    @Email(message = "EMAIL_INVALID")
    private String email;
    @NotBlank(message = "PASSWORD_NOT_EMPTY")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$", message = "PASSWORD_INVALID")
    private String password;

    @NotBlank(message = "FULL_NAME_NOT_EMPTY")
    private String fullName;
}
