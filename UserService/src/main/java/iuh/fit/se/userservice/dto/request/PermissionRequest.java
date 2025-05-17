package iuh.fit.se.userservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PermissionRequest {
		private String id;
	 @NotBlank(message = "name không được để trống")
	    @Size(max = 255, message = "name không được vượt quá 255 ký tự")
	    private String name;

	    @NotBlank(message = "apiPath không được để trống")
	    @Size(max = 255, message = "apiPath không được vượt quá 255 ký tự")
	    private String apiPath;

	    @NotBlank(message = "method không được để trống")
	    @Size(max = 50, message = "method không được vượt quá 50 ký tự")
	    private String method;

	    @NotBlank(message = "module không được để trống")
	    @Size(max = 100, message = "module không được vượt quá 100 ký tự")
	    private String module;

	    @Size(max = 255, message = "createdBy không được vượt quá 255 ký tự")
	    private String createdBy;

	    @Size(max = 255, message = "updatedBy không được vượt quá 255 ký tự")
	    private String updatedBy;
}
