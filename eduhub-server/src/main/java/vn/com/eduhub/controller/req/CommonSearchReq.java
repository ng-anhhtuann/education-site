package vn.com.eduhub.controller.req;

import lombok.*;

import java.util.HashMap;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CommonSearchReq {
    private Integer page;
    private Integer pageSize;
    private HashMap<String, Object> params;
}
