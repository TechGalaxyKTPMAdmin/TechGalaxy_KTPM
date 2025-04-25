package iuh.fit.se.userservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmailRequestRegister {
    private String email;
    private String name;
    private String registrationDate;
}
