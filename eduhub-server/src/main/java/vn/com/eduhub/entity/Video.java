package vn.com.eduhub.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "videos")
public class Video {

    @Id
    private String id;

    @Field("title")
    private String title;

    @Field("name")
    private String name;

    @Field("url")
    private String url;

    @Field("course_id")
    private String courseId;

    @Field("description")
    private String description;

    @LastModifiedDate
    @Field("updated_date")
    private Instant updatedDate;

    @CreatedDate
    @Field("created_date")
    private Instant createdDate;
}
