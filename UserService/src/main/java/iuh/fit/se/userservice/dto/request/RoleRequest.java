package iuh.fit.se.userservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class RoleRequest {
    private String id;
    @NotBlank(message = "NAME_NOT_EMPTY")
    @Size(max = 255, message = "NAME_MAX_LENGTH_255")
    private String name;

    @NotNull(message = "ACTIVE_STATUS_REQUIRED")
    private boolean active;  // Changed from `boolean` to `Boolean` for validation

    @Size(max = 65535, message = "DESCRIPTION_MAX_LENGTH_EXCEEDED") // Optional but good practice
    private String description;
    private List<String> permissionIds;
}