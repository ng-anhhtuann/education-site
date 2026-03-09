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
@Document(collection = "images")
public class Image {

    @Id
    private String id;

    @Field("name")
    private String name;

    @Field("is_avatar")
    private Boolean isAvatar;

    @Field("url")
    private String url;

    @Field("owner_id")
    private String ownerId;

    @LastModifiedDate
    @Field("updated_date")
    private Instant updatedDate;

    @CreatedDate
    @Field("created_date")
    private Instant createdDate;
}
