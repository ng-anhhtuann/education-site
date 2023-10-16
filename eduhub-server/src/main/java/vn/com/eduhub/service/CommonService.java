package vn.com.eduhub.service;

import vn.com.eduhub.controller.req.CommonSearchReq;
import vn.com.eduhub.dto.res.ObjectDataRes;

public interface CommonService<ENTITY, DTO> {

    ENTITY edit(DTO d);

    ObjectDataRes<ENTITY> getList(CommonSearchReq req);

    ENTITY detail(String id);

    boolean delete(String id);

}
