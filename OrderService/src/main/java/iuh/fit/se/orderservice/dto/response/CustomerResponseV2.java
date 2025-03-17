package iuh.fit.se.orderservice.dto.response;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import iuh.fit.se.orderservice.entity.enumeration.CustomerStatus;
import iuh.fit.se.orderservice.entity.enumeration.Gender;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerResponseV2 implements Serializable {
    String id;
    CustomerStatus userStatus;
    String name;
    String phone;
    Gender gender;
    String avatar;
    LocalDate dateOfBirth;
    private AccountResponse account;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AccountResponse implements Serializable {
        private String email;
    }
}
