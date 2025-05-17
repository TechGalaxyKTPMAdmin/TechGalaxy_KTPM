package iuh.fit.se.userservice.dto.request;

import iuh.fit.se.userservice.entities.Role;
import iuh.fit.se.userservice.entities.enumeration.Gender;
import iuh.fit.se.userservice.entities.enumeration.SystemUserLevel;
import iuh.fit.se.userservice.entities.enumeration.SystemUserStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
	    @Size(max = 255, message = "NAME_MAX_LENGTH_255")
	    private String name;

	    @NotBlank(message = "PHONE_NOT_EMPTY")
	    @Pattern(regexp = "^0[0-9]{9}$", message = "PHONE_INVALID")
	    @Size(max = 50, message = "PHONE_MAX_LENGTH_50")
	    private String phone;

	    @NotBlank(message = "ADDRESS_NOT_EMPTY")
	    @Size(max = 255, message = "ADDRESS_MAX_LENGTH_255")
	    private String address;

	    @NotNull(message = "STATUS_NOT_NULL")
	    private SystemUserStatus systemUserStatus;

	    @NotNull(message = "LEVEL_NOT_NULL")
	    private SystemUserLevel level;

	    private Gender gender;

	    @Size(max = 255, message = "AVATAR_MAX_LENGTH_255")
	    private String avatar;

	    @Valid
	    @NotNull(message = "ACCOUNT_INFO_REQUIRED")
	    private AccountRequest account;

	    @Getter
	    @Setter
	    @NoArgsConstructor
	    @AllArgsConstructor
	    public static class AccountRequest {
	        @NotBlank(message = "EMAIL_NOT_EMPTY")
	        @Email(message = "INVALID_EMAIL_FORMAT")
	        @Size(max = 255, message = "EMAIL_MAX_LENGTH_255")
	        private String email;

	        @NotBlank(message = "PASSWORD_NOT_EMPTY")
	        @Size(min = 6, max = 255, message = "PASSWORD_LENGTH_INVALID")
	        private String password;

	        @NotNull(message = "ROLES_REQUIRED")
	        private List<Role> roles;
	    }

}
