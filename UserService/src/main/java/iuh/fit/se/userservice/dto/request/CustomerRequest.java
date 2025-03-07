package iuh.fit.se.userservice.dto.request;

import iuh.fit.se.userservice.entities.Account;
import iuh.fit.se.userservice.entities.enumeration.CustomerStatus;
import iuh.fit.se.userservice.entities.enumeration.Gender;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerRequest {
    String id;
    CustomerStatus userStatus;
    @NotBlank(message = "NAME_NOT_EMPTY")
    String name;
    @NotBlank(message = "PHONE_NOT_EMPTY")
    @Pattern(regexp = "^0[0-9]{9}$", message = "PHONE_INVALID")
    String phone;
    Gender gender;
    String avatar;
    LocalDate dateOfBirth;
    Account account;
}
