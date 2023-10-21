package vn.com.eduhub.controller.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SubscriptionReq {
    @Schema(description = "student_id", example = "123-31jj-f8ura-3jsaf", required = true)
	private String studentId;
    @Schema(description = "course_id", example = "123-31jj-f8ura-3jsaf", required = true)
	private String courseId;
}

