package vn.com.eduhub.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "subscription")
public class Subscription {

    @Id
    private String id;

    @Field("student_id")
    private String studentId;

    @Field("course_id")
    private String courseId;

    @CreatedDate
    @Field("created_date")
    private Instant createdDate;
}
