package iuh.fit.se.userservice.dto.request;

import iuh.fit.se.userservice.entities.enumeration.Gender;
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
        private String name;
        private String email;
        private Gender gender;
        private String address;
        private int age;
        private LocalDateTime createdAt;
}
