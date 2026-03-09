package vn.com.eduhub.dto.master;

import lombok.*;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VideoDto {
    private String id;
    private String title;
    private String name;
    private String url;
    private String courseId;
    private String courseName;
    private String description;
    private Instant updatedDate;
    private Instant createdDate;
}
