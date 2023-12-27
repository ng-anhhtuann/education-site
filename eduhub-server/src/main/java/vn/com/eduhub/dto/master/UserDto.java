package vn.com.eduhub.dto.master;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Document(collection = "users")
public class UserDto {
    private String id;
    private String userName;
    private String password;
    private String email;
    private Long balance;
    private String avatarUrl;
    private String role;
    private Date updatedDate;
    private Date createdDate;
    private Boolean isVerified;
}
