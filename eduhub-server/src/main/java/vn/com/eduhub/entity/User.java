package vn.com.eduhub.entity;

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
@Document(collection = "USER")
public class User {

    @Id
    @SerializedName("ID")
    @Expose
    private String id;

    @SerializedName("USERNAME")
    @Expose
    @Field("USERNAME")
    private String userName;

    @SerializedName("PASSWORD")
    @Expose
    @Field("PASSWORD")
    private String password;

    @SerializedName("EMAIL")
    @Expose
    @Field("EMAIL")
    private String email;

    @SerializedName("BALANCE")
    @Expose
    @Field("BALANCE")
    private Long balance;

    @SerializedName("AVATAR")
    @Expose
    @Field("AVATAR")
    private String avatar;

    @SerializedName("ROLE")
    @Expose
    @Field("ROLE")
    private String role;

    @SerializedName("UPDATED_DATE")
    @Expose
    @Field("UPDATED_DATE")
    private Date updatedDate;

    @SerializedName("CREATED_DATE")
    @Expose
    @Field("CREATED_DATE")
    private Date createdDate;

    @SerializedName("IS_VERIFIED")
    @Expose
    @Field("IS_VERIFIED")
    private Boolean isVerified;
}
