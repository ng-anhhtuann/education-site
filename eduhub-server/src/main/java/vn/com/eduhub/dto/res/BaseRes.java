package vn.com.eduhub.dto.res;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BaseRes {
    private Long status;
    private String message;
    private String title;
    private Date time;
    private Long took;
    private Object errors;
    private Object data;
    /**
     * Sample:
     * {
     *      "status" : 200,
     *      "message" : "OK",
     *      "title" : "Successfully",
     *      "time" : 1628372222827,
     *      "took" : 42,
     *      "errors" : null,
     *      "data" : {
     *          "totalData" : 0,
     *          "datas" = []
     *      }
     * }
     */
}
