package vn.com.eduhub.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Document(collection = "courses")
public class Course {

    @Id
    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("price")
    @Expose
    @Field("price")
    private Long price;

    @SerializedName("title")
    @Expose
    @Field("title")
    private String title;

    @SerializedName("tagList")
    @Expose
    @Field("tag_list")
    private List<String> tagList;

    @SerializedName("studentCount")
    @Expose
    @Field("student_count")
    private Long studentCount;

    @SerializedName("teacherId")
    @Expose
    @Field("teacher_id")
    private String teacherId;

    @SerializedName("description")
    @Expose
    @Field("description")
    private String description;

    @SerializedName("thumbnailUrl")
    @Expose
    @Field("thumbnail_url")
    private String thumbnailUrl;

    @SerializedName("updatedDate")
    @Expose
    @Field("updated_date")
    private Date updatedDate;

    @SerializedName("createdDate")
    @Expose
    @Field("created_date")
    private Date createdDate;
}
