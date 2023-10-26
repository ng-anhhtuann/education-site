package vn.com.eduhub.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

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
@Document(collection = "videos")
public class Video {

    @Id
    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("title")
    @Expose
    @Field("title")
    private String title;

    @SerializedName("name")
    @Expose
    @Field("name")
    private String name;

    @SerializedName("url")
    @Expose
    @Field("url")
    private String url;

    @SerializedName("courseId")
    @Expose
    @Field("course_id")
    private String courseId;

    @SerializedName("description")
    @Expose
    @Field("description")
    private String description;

    @SerializedName("updatedDate")
    @Expose
    @Field("updated_date")
    private Date updatedDate;

    @SerializedName("createdDate")
    @Expose
    @Field("created_date")
    private Date createdDate;

}
