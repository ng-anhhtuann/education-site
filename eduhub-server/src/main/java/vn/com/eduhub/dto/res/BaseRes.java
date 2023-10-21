package vn.com.eduhub.dto.res;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Data
public class BaseRes {
    /** status : 200/400/500 - Mandatory */
    private Long status;

    /** message : Success/Bad request/Internal error - Mandatory */
    private String message;

    /**
     * title : nội dung message trả về ("Success" : status 200, "Validate error",
     * "Business exception" : 400, "System Exception" : status 500 ) - Mandatory
     */
    private String title;

    /** time : thời điểm thực thi xong - Mandatory */
    private Date time;

    /** took : khoảng thời gian đã thực thi - Mandatory */
    private Long took;

    /** errors : detail error - optional */
    private Object errors;

    /** data : data return */
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
