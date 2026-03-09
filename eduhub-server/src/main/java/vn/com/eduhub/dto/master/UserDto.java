package vn.com.eduhub.dto.master;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    private String id;
    private String userName;
    @JsonIgnore
    private String password;
    private String email;
    private Long balance;
    private String avatarUrl;
    private String role;
    private Instant updatedDate;
    private Instant createdDate;
    private Boolean isVerified;
}
