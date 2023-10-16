package vn.com.eduhub.controller.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.HashMap;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CommonSearchReq {
    @Schema(description = "page", example = "1", required = false)
    private Integer page;
    @Schema(description = "pageSize", example = "10", required = false)
    private Integer pageSize;
    @Schema(description = "searchType", example = "ALL", required = true)
    private String searchType;
    private HashMap<String, Object> params;
}
