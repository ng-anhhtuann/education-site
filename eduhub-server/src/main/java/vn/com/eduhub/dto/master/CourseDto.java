package vn.com.eduhub.dto.master;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CourseDto {
    private String id;
    private Long price;
    private String title;
    private List<String> tagList;
    private List<String> studentList;
    private Long studentCount;
    private String teacherId;
    private String description;
    private String thumbnailUrl;
}