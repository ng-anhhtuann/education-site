package vn.com.eduhub.dto.res;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ObjectDataRes<T> {
    private int totalData;
    private List<T> datas;
}
