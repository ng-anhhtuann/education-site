package vn.com.eduhub.controller.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import vn.com.eduhub.utils.CommonConstant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserAddReq {
    @Schema(description = "id", example = "123-31jj-f8ura-3jsaf", required = false)
    private String id;
    @Schema(description = "userName", example = "chienbinh156", required = true)
    private String userName;
    @Schema(description = "password", example = "chienbinh156", required = true)
    private String password;
    @Schema(description = "password", example = "chienbinh156", required = true)
    private String rePassword;
    @Schema(description = "email", example = "chienbinh156@gmail.com", required = true)
    private String email;
    @Schema(description = "avatar", example = CommonConstant.DEFAULT_AVATAR_URL, required = false)
    private String avatarUrl;
    @Schema(description = "role", example = "TEACHER", required = true)
    private String role;
}
