package iuh.fit.se.userservice.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResCreateAccountDTO {
    private String id;
    private String email;
    private String password;
}
