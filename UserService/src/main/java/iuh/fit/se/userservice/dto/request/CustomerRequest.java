package iuh.fit.se.userservice.dto.request;

import iuh.fit.se.userservice.entities.Account;
import iuh.fit.se.userservice.entities.enumeration.CustomerStatus;
import iuh.fit.se.userservice.entities.enumeration.Gender;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerRequest {
    @NotBlank(message = "ID must not be blank")
    String id;

    @NotNull(message = "USER_STATUS_NOT_NULL")
    CustomerStatus userStatus;

    @NotBlank(message = "NAME_NOT_EMPTY")
    @Size(max = 255, message = "NAME_TOO_LONG")
    String name;

    @NotBlank(message = "PHONE_NOT_EMPTY")
    @Pattern(regexp = "^0[0-9]{9}$", message = "PHONE_INVALID")
    @Size(max = 50, message = "PHONE_TOO_LONG")
    String phone;

    Gender gender;

    @Size(max = 255, message = "AVATAR_URL_TOO_LONG")
    String avatar;

    @Past(message = "DATE_OF_BIRTH_MUST_BE_IN_THE_PAST")
    LocalDate dateOfBirth;

    @NotNull(message = "ACCOUNT_NOT_NULL")
    @Valid
    Account account;
}
