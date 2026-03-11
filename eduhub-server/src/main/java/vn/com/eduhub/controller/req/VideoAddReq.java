package vn.com.eduhub.controller.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VideoAddReq {

    @Schema(description = "id (null for create, present for update)")
    private String id;

    @Schema(description = "name on storage")
    private String name;

    @NotBlank(message = "Title is required")
    @Schema(description = "title", example = "my video")
    private String title;

    @NotBlank(message = "URL is required")
    @Schema(description = "url")
    private String url;

    @Schema(description = "course_id")
    private String courseId;

    @Schema(description = "description")
    private String description;
}
