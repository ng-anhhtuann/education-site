package vn.com.eduhub.dto.master;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubscriptionDto {
    private String studentId;
    private String courseId;
    private Long balance;
}
