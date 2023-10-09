package vn.com.eduhub.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.com.eduhub.dto.auth.SignUpDto;
import vn.com.eduhub.dto.res.ObjectDataRes;
import vn.com.eduhub.entity.User;
import vn.com.eduhub.repository.UserRepository;
import vn.com.eduhub.service.IUserService;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public User edit(SignUpDto signUpDto) {
        return null;
    }

    @Override
    public ObjectDataRes<User> getList() {
        return null;
    }

    @Override
    public User detail(String id) {
        return null;
    }

    @Override
    public boolean delete(String id) {
        return false;
    }
}
