package vn.com.eduhub.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import vn.com.eduhub.controller.req.CommonSearchReq;
import vn.com.eduhub.dto.auth.LogInDto;
import vn.com.eduhub.dto.auth.SignUpDto;
import vn.com.eduhub.dto.master.UserDto;
import vn.com.eduhub.dto.res.PagedResponse;
import vn.com.eduhub.entity.User;
import vn.com.eduhub.exception.BusinessException;
import vn.com.eduhub.exception.ResourceNotFoundException;
import vn.com.eduhub.exception.ValidationException;
import vn.com.eduhub.repository.UserRepository;
import vn.com.eduhub.service.IUserService;
import vn.com.eduhub.service.SearchHelper;
import vn.com.eduhub.utils.CommonConstant;

import java.time.Instant;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {

    private final ModelMapper mapper;
    private final MongoTemplate mongoTemplate;
    private final UserRepository userRepository;
    private final SearchHelper searchHelper;

    @Override
    public UserDto register(SignUpDto dto) {
        User user = mapper.map(dto, User.class);
        user.setId(UUID.randomUUID().toString() + System.currentTimeMillis());
        user.setBalance(999999999L);
        user.setAvatarUrl(CommonConstant.DEFAULT_AVATAR_URL);
        user.setCreatedDate(Instant.now());
        user.setIsVerified(false);
        userRepository.insert(user);
        log.info("User registered: {}", user.getUserName());
        return mapper.map(user, UserDto.class);
    }

    @Override
    public UserDto update(SignUpDto dto) {
        User user = userRepository.findById(dto.getId())
                .orElseThrow(() -> new ResourceNotFoundException(CommonConstant.USER_NOT_FOUND));

        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            user.setPassword(dto.getPassword());
        } else {
            throw new ValidationException(CommonConstant.EMPTY_PASSWORD);
        }

        if (dto.getAvatarUrl() != null && !dto.getAvatarUrl().isBlank()) {
            user.setAvatarUrl(dto.getAvatarUrl());
        }

        user.setUpdatedDate(Instant.now());
        userRepository.save(user);
        log.info("User updated: {}", user.getId());
        return mapper.map(user, UserDto.class);
    }

    @Override
    public UserDto login(LogInDto dto) {
        Query query = new Query();
        query.addCriteria(
                new Criteria().orOperator(
                        Criteria.where("user_name").is(dto.getAccount()),
                        Criteria.where("email").is(dto.getAccount())
                ).and("password").is(dto.getPassword())
        );
        User user = mongoTemplate.findOne(query, User.class);
        if (user == null) {
            throw new BusinessException(CommonConstant.AUTH_FAIL);
        }
        return mapper.map(user, UserDto.class);
    }

    @Override
    public PagedResponse<UserDto> search(CommonSearchReq req) {
        SearchHelper.SearchResult<User> result = searchHelper.search(req, User.class);
        var dtos = result.items().stream()
                .map(u -> mapper.map(u, UserDto.class))
                .collect(Collectors.toList());
        return new PagedResponse<>(result.total(), dtos);
    }

    @Override
    public UserDto detail(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(CommonConstant.USER_NOT_FOUND));
        return mapper.map(user, UserDto.class);
    }
}
