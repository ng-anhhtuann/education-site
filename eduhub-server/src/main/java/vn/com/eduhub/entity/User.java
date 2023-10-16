package vn.com.eduhub.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Document(collection = "users")
public class User {

    @Id
    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("userName")
    @Expose
    @Field("user_name")
    private String userName;

    @JsonIgnore
    @SerializedName("password")
    @Expose
    @Field("password")
    private String password;

    @SerializedName("email")
    @Expose
    @Field("email")
    private String email;

    @SerializedName("balance")
    @Expose
    @Field("balance")
    private Long balance;

    @SerializedName("avatarUrl")
    @Expose
    @Field("avatar_url")
    private String avatarUrl;

    @SerializedName("role")
    @Expose
    @Field("role")
    private String role;

    @SerializedName("updatedDate")
    @Expose
    @Field("updated_date")
    private Date updatedDate;

    @SerializedName("createdDate")
    @Expose
    @Field("created_date")
    private Date createdDate;

    @SerializedName("isVerified")
    @Expose
    @Field("is_verified")
    private Boolean isVerified;
}
