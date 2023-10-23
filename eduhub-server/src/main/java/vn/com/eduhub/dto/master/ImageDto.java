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
public class ImageDto {

    private String id;
    private String name;
    private Boolean isAvatar;
    private String url;
    private String ownerId;
    private Date updatedDate;
    private Date createdDate;
}
