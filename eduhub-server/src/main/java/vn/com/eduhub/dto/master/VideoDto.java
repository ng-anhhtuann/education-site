package vn.com.eduhub.dto.master;

import java.util.Date;

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
public class VideoDto {

    private String id;
    private String title;
    private String name;
    private String url;
    private String courseId;
    private String description;
    private Date updatedDate;
    private Date createdDate;

}
