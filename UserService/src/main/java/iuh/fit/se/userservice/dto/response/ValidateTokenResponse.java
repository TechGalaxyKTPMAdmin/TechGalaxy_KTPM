package iuh.fit.se.userservice.dto.response;

import iuh.fit.se.userservice.entities.Role;
import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ValidateTokenResponse {
    private boolean valid;
    private String userId;
    private String email;
    private List<PermissionAuthResponse> permissions;
}