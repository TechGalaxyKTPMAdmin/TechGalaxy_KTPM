package iuh.fit.se.userservice.dto.request;

import jakarta.validation.constraints.NotBlank;
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
    private String email;
    @NotBlank(message = "PASSWORD_NOT_EMPTY")
    private String password;

    private String fullName;
}
