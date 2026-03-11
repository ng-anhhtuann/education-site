package vn.com.eduhub.controller.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserAddReq {

    @Schema(description = "id (null for create, present for update)")
    private String id;

    @NotBlank(message = "Username is required")
    @Schema(description = "userName", example = "chienbinh156")
    private String userName;

    @NotBlank(message = "Password is required")
    @Schema(description = "password", example = "chienbinh156")
    private String password;

    @NotBlank(message = "Re-typed password is required")
    @Schema(description = "rePassword", example = "chienbinh156")
    private String rePassword;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @Schema(description = "email", example = "chienbinh156@gmail.com")
    private String email;

    @Schema(description = "avatar URL")
    private String avatarUrl;

    @NotBlank(message = "Role is required")
    @Schema(description = "role", example = "TEACHER")
    private String role;
}
