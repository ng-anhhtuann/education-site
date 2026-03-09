package vn.com.eduhub.dto.master;

import lombok.*;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseDto {
    private String id;
    private Long price;
    private String title;
    private List<String> tagList;
    private Long studentCount;
    private String teacherId;
    private String teacherName;
    private String description;
    private String thumbnailUrl;
    private Instant updatedDate;
    private Instant createdDate;
}
