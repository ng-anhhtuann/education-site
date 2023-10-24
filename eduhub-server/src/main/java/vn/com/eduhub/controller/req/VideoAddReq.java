package vn.com.eduhub.controller.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import vn.com.eduhub.utils.CommonConstant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class VideoAddReq {
    @Schema(description = "id", example = "123-31jj-f8ura-3jsaf", required = false)
    private String id;
    @Schema(description = "title", example = "my avatar", required = true)
    private String title;
    @Schema(description = "url", example = CommonConstant.DEFAULT_AVATAR_URL, required = true)
    private String url;
    @Schema(description = "course_id", example = "asd81-asi28-243a-js2", required = false)
    private String courseId;
    @Schema(description = "description", example = "description vd", required = false)
    private String description;
}
