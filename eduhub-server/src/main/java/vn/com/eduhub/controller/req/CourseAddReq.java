package vn.com.eduhub.controller.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CourseAddReq {

    @Schema(description = "id (null for create, present for update)")
    private String id;

    @NotNull(message = "Price is required")
    @Min(value = 0, message = "Price must be >= 0")
    @Schema(description = "price", example = "203")
    private Long price;

    @NotBlank(message = "Title is required")
    @Schema(description = "title", example = "title for course")
    private String title;

    private List<String> tagList;

    @Schema(description = "teacherId", example = "455f140a-48d7-4c7b-abf6-afd8612e39241697388340756")
    private String teacherId;

    @NotBlank(message = "Description is required")
    @Schema(description = "description", example = "des for course")
    private String description;

    @Schema(description = "thumbnailUrl")
    private String thumbnailUrl;
}
