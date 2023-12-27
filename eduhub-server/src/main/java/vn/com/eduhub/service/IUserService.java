package vn.com.eduhub.service;

import vn.com.eduhub.dto.auth.LogInDto;
import vn.com.eduhub.dto.auth.SignUpDto;
import vn.com.eduhub.dto.master.UserDto;
import vn.com.eduhub.entity.User;

public interface IUserService extends CommonService<User, UserDto> {

    User login(LogInDto dto) throws Exception;

    User edit(SignUpDto dto) throws Exception;

}
