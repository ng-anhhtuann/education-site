package vn.com.eduhub.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "users")
public class User {

    @Id
    private String id;

    @Indexed(unique = true)
    @Field("user_name")
    private String userName;

    @JsonIgnore
    @Field("password")
    private String password;

    @Indexed(unique = true)
    @Field("email")
    private String email;

    @Field("balance")
    private Long balance;

    @Field("avatar_url")
    private String avatarUrl;

    @Field("role")
    private String role;

    @LastModifiedDate
    @Field("updated_date")
    private Instant updatedDate;

    @CreatedDate
    @Field("created_date")
    private Instant createdDate;

    @Field("is_verified")
    private Boolean isVerified;
}
