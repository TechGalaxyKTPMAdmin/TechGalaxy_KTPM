package iuh.fit.se.orderservice.dto.response;

import iuh.fit.se.orderservice.entity.enumeration.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SystemUserResponse implements Serializable {
    private String id;
    private String name;
    private String email;
    private Gender gender;
    private int age;
}
