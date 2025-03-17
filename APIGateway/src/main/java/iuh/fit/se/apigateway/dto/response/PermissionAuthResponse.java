package iuh.fit.se.apigateway.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PermissionAuthResponse {

    private String apiPath;
    private String method;

}
