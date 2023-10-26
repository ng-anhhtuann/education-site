package vn.com.eduhub.dto.master;

import lombok.*;

import java.util.Date;

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
