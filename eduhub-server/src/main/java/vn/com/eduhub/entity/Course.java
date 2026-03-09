package vn.com.eduhub.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "courses")
public class Course {

    @Id
    private String id;

    @Field("price")
    private Long price;

    @Indexed(unique = true)
    @Field("title")
    private String title;

    @Field("tag_list")
    private List<String> tagList;

    @Field("student_count")
    private Long studentCount;

    @Field("teacher_id")
    private String teacherId;

    @Field("description")
    private String description;

    @Field("thumbnail_url")
    private String thumbnailUrl;

    @LastModifiedDate
    @Field("updated_date")
    private Instant updatedDate;

    @CreatedDate
    @Field("created_date")
    private Instant createdDate;
}
