package vn.com.eduhub.dto.auth;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LogInDto {

    /**
     * Representing `userName` or `email` 
     * User can use either field for logging in to their account
     */
    private String account;
    private String password;
}
