package vn.com.eduhub.service;

import vn.com.eduhub.controller.req.CommonSearchReq;
import vn.com.eduhub.dto.auth.LogInDto;
import vn.com.eduhub.dto.auth.SignUpDto;
import vn.com.eduhub.dto.master.UserDto;
import vn.com.eduhub.dto.res.PagedResponse;

public interface IUserService {

    UserDto register(SignUpDto dto);

    UserDto update(SignUpDto dto);

    UserDto login(LogInDto dto);

    PagedResponse<UserDto> search(CommonSearchReq req);

    UserDto detail(String id);
}
