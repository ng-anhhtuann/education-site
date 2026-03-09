package vn.com.eduhub.dto.master;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FileDto {
    private String fileName;
    private String fileUrl;
}
