package vn.com.eduhub.dto.master;

import lombok.*;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImageDto {
    private String id;
    private String name;
    private Boolean isAvatar;
    private String url;
    private String ownerId;
    private String ownerName;
    private Instant updatedDate;
    private Instant createdDate;
}
