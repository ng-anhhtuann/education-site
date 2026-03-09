package vn.com.eduhub.dto.auth;

import lombok.*;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignUpDto {
    private String id;
    private String userName;
    private String password;
    private String email;
    private String avatarUrl;
    private String role;
    private Instant updatedDate;
    private Instant createdDate;
}
