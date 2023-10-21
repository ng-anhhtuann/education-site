package vn.com.eduhub.service;

import vn.com.eduhub.controller.req.CommonSearchReq;
import vn.com.eduhub.dto.res.ObjectDataRes;

public interface CommonService<ENTITY, DTO> {

    ENTITY edit(DTO d) throws Exception;

    ObjectDataRes<ENTITY> getList(CommonSearchReq req);

    DTO detail(String id) throws Exception;

    String delete(String id) throws Exception;

}
