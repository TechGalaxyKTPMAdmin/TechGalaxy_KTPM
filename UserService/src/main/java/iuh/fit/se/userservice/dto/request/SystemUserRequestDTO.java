package iuh.fit.se.userservice.dto.request;

import iuh.fit.se.userservice.entities.Role;
import iuh.fit.se.userservice.entities.enumeration.Gender;
import iuh.fit.se.userservice.entities.enumeration.SystemUserLevel;
import iuh.fit.se.userservice.entities.enumeration.SystemUserStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SystemUserRequestDTO {
    private String id;
    @NotBlank(message = "NAME_NOT_EMPTY")
    private String name;

    @NotBlank(message = "PHONE_NOT_EMPTY")
    @Pattern(regexp = "^0[0-9]{9}$", message = "PHONE_INVALID")
    private String phone;
    @NotBlank(message = "ADDRESS_NOT_EMPTY")
    @Pattern(regexp = "^[a-zA-Z0-9\\s,.'-]{1,100}$", message = "ADDRESS_INVALID")
    private String address;

    private SystemUserStatus systemUserStatus;

    private SystemUserLevel level;

    private Gender gender;

    private String avatar;

    private AccountRequest account;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AccountRequest {
        private String email;
        private String password;
        private List<Role> roles;
    }
}
