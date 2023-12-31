package vn.com.eduhub.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import vn.com.eduhub.controller.req.CommonSearchReq;
import vn.com.eduhub.dto.master.CourseDto;
import vn.com.eduhub.dto.master.SubscriptionDto;
import vn.com.eduhub.dto.res.ObjectDataRes;
import vn.com.eduhub.entity.Course;
import vn.com.eduhub.entity.Subscription;
import vn.com.eduhub.entity.User;
import vn.com.eduhub.repository.CourseRepository;
import vn.com.eduhub.repository.SubscriptionRepository;
import vn.com.eduhub.repository.UserRepository;
import vn.com.eduhub.service.ISubscriptionService;
import vn.com.eduhub.service.IUserService;
import vn.com.eduhub.utils.CommonConstant;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class SubscriptionServiceImpl implements ISubscriptionService {

    private final ModelMapper mapper = new ModelMapper();

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    IUserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    SubscriptionRepository subscriptionRepository;

    /**
     * Add subscription with validate data Not edit
     */
    @Override
    public SubscriptionDto createSubscription(SubscriptionDto dto) throws Exception {
        Optional<Course> courseOptional = courseRepository.findById(dto.getCourseId());
        if (courseOptional.isEmpty())
            throw new Exception(CommonConstant.COURSE_NOT_FOUND);

        Optional<User> userOptional = userRepository.findById(dto.getStudentId());
        if (userOptional.isEmpty())
            throw new Exception(CommonConstant.USER_NOT_FOUND);

        Query query = new Query();
        query.addCriteria(new Criteria().andOperator(Criteria.where("student_id").is(dto.getStudentId()),
            Criteria.where("course_id").is(dto.getCourseId())));
        Subscription subQuery = mongoTemplate.findOne(query, Subscription.class);
        if (subQuery != null)
            throw new Exception(CommonConstant.DUPLICATE_SUBSCRIPTION);

        Long userBalance = userOptional.get().getBalance();
        Long coursePrice = courseOptional.get().getPrice();
        if (userBalance - coursePrice < 0) {
            throw new Exception(CommonConstant.BALANCE_INVALID);
        }

        try {
            Subscription subscription = new Subscription();
            subscription.setCourseId(dto.getCourseId());
            subscription.setStudentId(dto.getStudentId());
            subscription.setId(String.valueOf(UUID.randomUUID()).concat(String.valueOf(System.currentTimeMillis())));
            subscription.setCreatedDate(new Date());
            subscriptionRepository.insert(subscription);

            Course currentCourse = courseOptional.get();
            currentCourse.setStudentCount(currentCourse.getStudentCount() + 1);
            courseRepository.save(currentCourse);

            User user = userOptional.get();
            user.setBalance(userBalance - coursePrice);
            user.setUpdatedDate(new Date());
            userRepository.save(user);

            dto.setBalance(userBalance - coursePrice);
            return dto;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(CommonConstant.REPASSWORD_FAIL);
        }
    }

    @Override
    public Subscription edit(SubscriptionDto dto) throws Exception {
        return null;
    }

    /**
     * Search by student_id and course_id
     */
    @Override
    public ObjectDataRes<Subscription> getList(CommonSearchReq req) {
        return null;
    }

    @Override
    public SubscriptionDto detail(String id) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String delete(String id) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ObjectDataRes<CourseDto> searchCourseByUser(CommonSearchReq req) {
        List<Subscription> listData = new ArrayList<>();

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
            listData = mongoTemplate.find(query, Subscription.class);
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
                listData = mongoTemplate.find(query, Subscription.class);
            }

        }

        List<String> courseIds = listData.stream()
            .map(Subscription::getCourseId)
            .collect(Collectors.toList());

        List<Course> courseList = courseRepository.findAllById(courseIds);

        courseList.removeIf(Objects::isNull);

        List<CourseDto> courseDtoList = courseList.stream().map(course -> {
            CourseDto courseDto = mapper.map(course, CourseDto.class);
            try {
                courseDto.setTeacherName(userService.detail(course.getTeacherId()).getUserName());
            } catch (Exception e) {
                e.printStackTrace();
                courseDto.setTeacherName("UNKNOWN");
            }
            return courseDto;
        }).collect(Collectors.toList());

        query.skip(0);
        query.limit(0);
        int size = mongoTemplate.find(query, Subscription.class).size();

        return new ObjectDataRes<>(size, courseDtoList);
    }

    @Override
    public ObjectDataRes<User> searchStudentByCourse(CommonSearchReq req) {
        List<Subscription> listData = new ArrayList<>();

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
            listData = mongoTemplate.find(query, Subscription.class);
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
                listData = mongoTemplate.find(query, Subscription.class);
            }

        }

        List<String> userId = listData.stream()
            .map(Subscription::getStudentId)
            .collect(Collectors.toList());

        List<User> userList = userRepository.findAllById(userId);

        userList.removeIf(Objects::isNull);

        query.skip(0);
        query.limit(0);
        int size = mongoTemplate.find(query, Subscription.class).size();

        return new ObjectDataRes<>(size, userList);
    }

    @Override
    public boolean checkSubscription(String userId, String courseId) {
        Criteria criteria = new Criteria();
        criteria.andOperator(
            Criteria.where("student_id").is(userId),
            Criteria.where("course_id").is(courseId)
        );

        Query query = new Query();
        query.addCriteria(criteria);
        return mongoTemplate.exists(query, Subscription.class);
    }

}
