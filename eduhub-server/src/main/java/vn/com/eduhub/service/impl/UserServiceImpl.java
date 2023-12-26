package vn.com.eduhub.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import vn.com.eduhub.controller.req.CommonSearchReq;
import vn.com.eduhub.dto.auth.LogInDto;
import vn.com.eduhub.dto.auth.SignUpDto;
import vn.com.eduhub.dto.res.ObjectDataRes;
import vn.com.eduhub.entity.User;
import vn.com.eduhub.repository.CourseRepository;
import vn.com.eduhub.repository.SubscriptionRepository;
import vn.com.eduhub.repository.UserRepository;
import vn.com.eduhub.service.IUserService;
import vn.com.eduhub.utils.CommonConstant;

import java.util.*;

@Service
@Slf4j
public class UserServiceImpl implements IUserService {

    private final ModelMapper mapper = new ModelMapper();

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    SubscriptionRepository subscriptionRepository;

    /**
     * @throws Exception
     * @flow Nếu có id thì là update Các field được phép update là avatarUrl và password Nếu không có id thì là thêm mới hoàn toàn.
     */
    @Override
    public User edit(SignUpDto dto) throws Exception {
        if (dto.getId() == null || dto.getId().isEmpty() || dto.getId().isBlank()) {
            User user = mapper.map(dto, User.class);
            user.setId(String.valueOf(UUID.randomUUID()).concat(String.valueOf(System.currentTimeMillis())));
            user.setBalance(999999999L);
            user.setAvatarUrl(CommonConstant.DEFAULT_AVATAR_URL);
            user.setCreatedDate(new Date());
            user.setUpdatedDate(null);
            userRepository.insert(user);
            return user;
        } else {
            Optional<User> userOrNull = userRepository.findById(dto.getId());
            if (userOrNull.isPresent()) {
                User user = userOrNull.get();
                if (dto.getPassword() != null || !dto.getPassword().trim().isEmpty()) {
                    user.setPassword(dto.getPassword());
                } else {
                    throw new Exception(CommonConstant.EMPTY_PASSWORD);
                }

                if (dto.getAvatarUrl() != null || !dto.getPassword().trim().isEmpty()) {
                    user.setAvatarUrl(dto.getAvatarUrl());
                } else {
                    throw new Exception(CommonConstant.PROCESS_FAIL);
                }
                user.setUpdatedDate(new Date());
                userRepository.save(user);
                return user;
            } else {
                throw new Exception(CommonConstant.USER_NOT_FOUND);
            }
        }
    }

    /**
     * @flow Search field được cho phép ở user là user_name, email, role Datatype của các field search đều là String Dynamic search lúc này
     *       kiểm tra chuỗi có chứa chuỗi con hay không. Nếu page = 0 thì là lấy hết record theo trạng thái search Nếu searchType = "ALL"
     *       thì sẽ là search tất cả mặc kệ các params Nếu searchType = "FIELD" thì sẽ là search theo params
     * @default Sort by date created
     */
    @Override
    public ObjectDataRes<User> getList(CommonSearchReq req) {
        List<User> listData = new ArrayList<>();

        Query query = new Query();

        query.with(Sort.by(Sort.Order.desc("created_date")));

        if (req.getPage() != null && req.getPage() > 0 && req.getPageSize() != null && req.getPageSize() >= 0) {
            query.skip((long) (req.getPage() - 1) * req.getPageSize());
            query.limit(req.getPageSize());
        }

        /**
         * Limit the number of returned documents to limit. A zero or negative value is considered as unlimited.
         */
        if ((req.getPage() != null && req.getPage() == 0) || (req.getPageSize() == null && req.getPage() == null)) {
            query.limit(0);
        }

        if (req.getSearchType().equals("ALL")) {
            listData = mongoTemplate.find(query, User.class);
        }
        if (req.getSearchType().equals("FIELD") && req.getParams() != null) {
            Criteria criteria = new Criteria();
            List<Criteria> criteriaList = new ArrayList<>();

            for (Map.Entry<String, Object> entry : req.getParams().entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();

                if (value instanceof String)
                    criteriaList.add(Criteria.where(key).regex(String.valueOf(value), "i"));
            }

            if (!criteriaList.isEmpty()) {
                criteria.andOperator(criteriaList.toArray(new Criteria[0]));
                query.addCriteria(criteria);
                listData = mongoTemplate.find(query, User.class);
            }
        }

        return new ObjectDataRes<>(listData.size(), listData);
    }

    /**
     * Lấy thông tin bằng id
     * 
     * @throws Exception
     */
    @Override
    public SignUpDto detail(String id) throws Exception {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()) {
            throw new Exception(CommonConstant.USER_NOT_FOUND);
        }
        return mapper.map(userOptional.get(), SignUpDto.class);
    }

    @Override
    public String delete(String id) {
        return id;
    }

    @Override
    public User login(LogInDto dto) throws Exception {
        Query query = new Query();
        query.addCriteria(
                new Criteria().orOperator(Criteria.where("user_name").is(dto.getAccount()), Criteria.where("email").is(dto.getAccount()))
                        .and("password").is(dto.getPassword()));
        User user = mongoTemplate.findOne(query, User.class);
        if (user == null)
            throw new Exception(CommonConstant.AUTH_FAIL);
        return user;
    }

}
