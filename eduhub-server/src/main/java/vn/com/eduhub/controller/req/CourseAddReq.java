package vn.com.eduhub.controller.req;

import lombok.*;
import vn.com.eduhub.utils.CommonConstant;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CourseAddReq {

    @Schema(description = "id", example = "2fcbaecc-bf14-4a02-8e45-aed45370e5fa1697471802670", required = false)
    private String id;
    @Schema(description = "price", example = "203", required = false)
    private Long price;
    @Schema(description = "title", example = "title fpor course", required = false)
    private String title;
    private List<String> tagList;
    @Schema(description = "studentCount", example = "0", required = false)
    private Long studentCount;
    @Schema(description = "teacherId", example = "455f140a-48d7-4c7b-abf6-afd8612e39241697388340756", required = false)
    private String teacherId;
    @Schema(description = "description", example = "des for course", required = false)
    private String description;
    @Schema(description = "avatar", example = CommonConstant.DEFAULT_AVATAR_URL, required = false)
    private String thumbnailUrl;
}
