package vn.com.eduhub.service;

import vn.com.eduhub.dto.res.ObjectDataRes;

public interface CommonService<ENTITY, DTO> {

    ENTITY edit(DTO d);

    ObjectDataRes<ENTITY> getList();

    ENTITY detail(String id);

    boolean delete(String id);

}
