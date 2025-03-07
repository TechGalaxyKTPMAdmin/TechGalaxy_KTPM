package iuh.fit.se.userservice.dto.response;

import iuh.fit.se.userservice.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ValidateTokenResponse {
    private boolean valid;
    private String userId;
    private String email;
    private List<PermissionAuthResponse> roles;
}