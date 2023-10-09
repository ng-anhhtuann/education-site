package vn.com.eduhub.controller.req;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserAddReq {
    private String id;
    private String userName;
    private String password;
    private String email;
    private String avatar;
    private String role;
}
