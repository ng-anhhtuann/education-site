package vn.com.eduhub.controller.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionReq {

    @NotBlank(message = "Student ID is required")
    @Schema(description = "student_id", example = "123-31jj-f8ura-3jsaf")
    private String studentId;

    @NotBlank(message = "Course ID is required")
    @Schema(description = "course_id", example = "123-31jj-f8ura-3jsaf")
    private String courseId;
}
