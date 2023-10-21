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
@Document(collection = "subscription")
public class Subscription {

	@Id
	@SerializedName("id")
	@Expose
	private String id;

	@SerializedName("studentId")
	@Expose
	@Field("student_id")
	private String studentId;

	@SerializedName("courseId")
	@Expose
	@Field("course_id")
	private String courseId;

	@SerializedName("createdDate")
	@Expose
	@Field("created_date")
	private Date createdDate;

}
