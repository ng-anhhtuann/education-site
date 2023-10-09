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
    @Field("userName")
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

    @SerializedName("avatar")
    @Expose
    @Field("avatar")
    private String avatar;

    @SerializedName("role")
    @Expose
    @Field("role")
    private String role;

    @SerializedName("updatedDate")
    @Expose
    @Field("updatedDate")
    private Date updatedDate;

    @SerializedName("createdDate")
    @Expose
    @Field("createdDate")
    private Date createdDate;

    @SerializedName("isVerified")
    @Expose
    @Field("isVerified")
    private Boolean isVerified;
}
