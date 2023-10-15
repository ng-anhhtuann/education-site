package vn.com.eduhub.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.com.eduhub.dto.auth.SignUpDto;
import vn.com.eduhub.dto.res.ObjectDataRes;
import vn.com.eduhub.entity.User;
import vn.com.eduhub.repository.UserRepository;
import vn.com.eduhub.service.IUserService;
import vn.com.eduhub.utils.CommonConstant;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    UserRepository userRepository;

    private final ModelMapper mapper = new ModelMapper();

    /**
     * @flow Nếu có id thì là update
     * Các field được phép update là avatarUrl và password
     * Nếu không có id thì là thêm mới hoàn toàn
     * @param dto
     * @return
     */
    @Override
    public User edit(SignUpDto dto) {
        if (dto.getId() == null || dto.getId().isEmpty() || dto.getId().isBlank()){
            User user = mapper.map(dto, User.class);
            user.setId(String.valueOf(UUID.randomUUID()).concat(String.valueOf(System.currentTimeMillis())));
            user.setBalance(999999999L);
            user.setAvatarUrl(CommonConstant.DEFAULT_AVATAR_URL);
            user.setCreatedDate(new Date());
            userRepository.insert(user);
            return user;
        } else {
            Optional<User> userOrNull = userRepository.findById(dto.getId());
            if (userOrNull.isPresent()){
                User user = userOrNull.get();
                user.setPassword(dto.getPassword());
                user.setAvatarUrl(dto.getAvatarUrl());
                userRepository.save(user);
                return user;
            }
            return null;
        }
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
