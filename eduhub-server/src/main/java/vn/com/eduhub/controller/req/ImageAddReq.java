package vn.com.eduhub.controller.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ImageAddReq {

    @Schema(description = "id (null for create, present for update)")
    private String id;

    @NotBlank(message = "Name is required")
    @Schema(description = "name", example = "my avatar")
    private String name;

    @Schema(description = "is_avatar", example = "true")
    private Boolean isAvatar;

    @NotBlank(message = "URL is required")
    @Schema(description = "url")
    private String url;

    @Schema(description = "owner_id")
    private String ownerId;
}
