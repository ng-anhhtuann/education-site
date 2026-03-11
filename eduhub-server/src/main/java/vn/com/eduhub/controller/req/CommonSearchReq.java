package vn.com.eduhub.controller.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.HashMap;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommonSearchReq {

    @Schema(description = "page", example = "1")
    private Integer page;

    @Schema(description = "pageSize", example = "10")
    private Integer pageSize;

    @NotBlank(message = "searchType is required (ALL or FIELD)")
    @Schema(description = "searchType", example = "ALL")
    private String searchType;

    private HashMap<String, Object> params;
}
