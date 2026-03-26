package vn.com.eduhub.dto.res;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class PagedResponse<T> {
    private final long totalData;
    private final List<T> datas;
}
