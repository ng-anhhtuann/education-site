package vn.com.eduhub.dto.auth;

import lombok.*;

import java.util.Date;

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
    private String avatarUrl;
    private String role;
    private Date updatedDate;
    private Date createdDate;
}
