package vn.com.eduhub.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Document(collection = "images")
public class Image {

    @Id
    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("name")
    @Expose
    @Field("name")
    private String name;

    @SerializedName("isAvatar")
    @Expose
    @Field("is_avatar")
    private Boolean isAvatar;

    @SerializedName("url")
    @Expose
    @Field("url")
    private String url;

    @SerializedName("ownerId")
    @Expose
    @Field("owner_id")
    private String ownerId;

    @SerializedName("updatedDate")
    @Expose
    @Field("updated_date")
    private Date updatedDate;

    @SerializedName("createdDate")
    @Expose
    @Field("created_date")
    private Date createdDate;

}
