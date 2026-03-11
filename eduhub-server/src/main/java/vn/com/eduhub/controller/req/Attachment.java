package vn.com.eduhub.controller.req;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class Attachment {
    private String id;
    private String url;
}
