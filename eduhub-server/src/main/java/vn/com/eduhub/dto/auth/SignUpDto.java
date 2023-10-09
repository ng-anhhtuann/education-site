package vn.com.eduhub.dto.auth;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SignUpDto {
    private String id;
    private String userName;
    private String password;
    private String email;
    private String avatar;
    private String role;
}
