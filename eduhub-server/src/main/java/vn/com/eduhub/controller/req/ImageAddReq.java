package vn.com.eduhub.controller.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import vn.com.eduhub.utils.CommonConstant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ImageAddReq {
    @Schema(description = "id", example = "123-31jj-f8ura-3jsaf", required = false)
    private String id;
    @Schema(description = "name", example = "my avatar", required = true)
    private String name;
    @Schema(description = "is_avatar", example = "true", required = false)
    private Boolean isAvatar;
    @Schema(description = "url", example = CommonConstant.DEFAULT_AVATAR_URL, required = true)
    private String url;
    @Schema(description = "owner_id", example = "3245-f1234a-124jand", required = false)
    private String ownerId;
}
