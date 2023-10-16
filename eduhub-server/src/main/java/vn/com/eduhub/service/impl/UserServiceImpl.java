package vn.com.eduhub.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import vn.com.eduhub.controller.req.CommonSearchReq;
import vn.com.eduhub.dto.auth.SignUpDto;
import vn.com.eduhub.dto.res.ObjectDataRes;
import vn.com.eduhub.entity.User;
import vn.com.eduhub.repository.UserRepository;
import vn.com.eduhub.service.IUserService;
import vn.com.eduhub.utils.CommonConstant;

import java.util.*;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    UserRepository userRepository;

    private static final Query query = new Query();

    private final ModelMapper mapper = new ModelMapper();

    /**
     * @flow Nếu có id thì là update
     * Các field được phép update là avatarUrl và password
     * Nếu không có id thì là thêm mới hoàn toàn.
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

    /**
     * @flow Search field được cho phép ở user là userName, email, role
     * Datatype của các field search đều là String
     * Dynamic search lúc này kiểm tra chuỗi có chứa chuỗi con hay không.
     * Nếu page = 0 thì là lấy hết record theo trạng thái search
     * Nếu searchType = "ALL" thì sẽ là search tất cả mặc kệ các params
     * Nếu searchType = "FIELD" thì sẽ là search theo params
     * @default Sort by date created
     */
    @Override
    public ObjectDataRes<User> getList(CommonSearchReq req) {
        List<User> listData = new ArrayList<>();

        query.with(Sort.by(Sort.Order.desc("created_date")));

        if (req.getPage() != null && req.getPage() > 0 && req.getPageSize() != null && req.getPageSize() >= 0) {
            query.skip((long) (req.getPage() - 1) * req.getPageSize());
            query.limit(req.getPageSize());
        }
        if ((req.getPage() != null && req.getPage() == 0) || (req.getPageSize() == null && req.getPage() == null)) {
            query.limit(0);
        }

        if (req.getSearchType().equals("ALL")) {
            listData = mongoTemplate.find(query, User.class);
        }
        if (req.getSearchType().equals("FIELD") && req.getParams() != null) {
            Criteria criteria = new Criteria();
            for (Map.Entry<String, Object> entry : req.getParams().entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();

                if (value instanceof String) {
                    criteria.andOperator(Criteria.where(key).regex(String.valueOf(value), "i"));
                } else {
                    criteria.andOperator(Criteria.where(key).is(value));
                }
            }

            query.addCriteria(criteria);
            listData = mongoTemplate.find(query, User.class);
        }

        return new ObjectDataRes<>(listData.size(), listData);
    }

    /**
     * Lấy thông tin bằng id
     */
    @Override
    public User detail(String id) {
        Optional<User> userOptional = userRepository.findById(id);
        return userOptional.orElseGet(User::new);
    }

    @Override
    public boolean delete(String id) {
        return true;
    }
}
