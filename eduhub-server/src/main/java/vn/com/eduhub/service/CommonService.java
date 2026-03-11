package vn.com.eduhub.service;

import vn.com.eduhub.controller.req.CommonSearchReq;
import vn.com.eduhub.dto.res.PagedResponse;

public interface CommonService<DTO> {

    DTO edit(DTO dto);

    PagedResponse<DTO> search(CommonSearchReq req);

    DTO detail(String id);

    String delete(String id);
}
