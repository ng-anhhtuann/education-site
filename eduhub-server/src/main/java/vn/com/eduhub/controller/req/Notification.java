package vn.com.eduhub.controller.req;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class Notification {

    @Valid
    private List<Attachment> attachment;
    private String content;

    @Min(value = 0, message = "createdAt must be >= 0")
    private Long createdAt;

    private String html;
    private String id;
    private String ownerId;
    private Boolean seen;
    private String title;
}
