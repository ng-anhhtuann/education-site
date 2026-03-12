package vn.com.eduhub.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import vn.com.eduhub.controller.req.CommonSearchReq;
import vn.com.eduhub.dto.master.CourseDto;
import vn.com.eduhub.dto.master.SubscriptionDto;
import vn.com.eduhub.dto.master.UserDto;
import vn.com.eduhub.dto.res.PagedResponse;
import vn.com.eduhub.entity.Course;
import vn.com.eduhub.entity.Subscription;
import vn.com.eduhub.entity.User;
import vn.com.eduhub.exception.BusinessException;
import vn.com.eduhub.exception.ResourceNotFoundException;
import vn.com.eduhub.repository.CourseRepository;
import vn.com.eduhub.repository.SubscriptionRepository;
import vn.com.eduhub.repository.UserRepository;
import vn.com.eduhub.service.ISubscriptionService;
import vn.com.eduhub.service.IUserService;
import vn.com.eduhub.service.SearchHelper;
import vn.com.eduhub.utils.CommonConstant;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements ISubscriptionService {

    private final ModelMapper mapper;
    private final MongoTemplate mongoTemplate;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final IUserService userService;
    private final SearchHelper searchHelper;

    @Override
    public SubscriptionDto createSubscription(SubscriptionDto dto) {
        Course course = courseRepository.findById(dto.getCourseId())
                .orElseThrow(() -> new ResourceNotFoundException(CommonConstant.COURSE_NOT_FOUND));
        User user = userRepository.findById(dto.getStudentId())
                .orElseThrow(() -> new ResourceNotFoundException(CommonConstant.USER_NOT_FOUND));

        Query dupCheck = new Query();
        dupCheck.addCriteria(new Criteria().andOperator(
                Criteria.where("student_id").is(dto.getStudentId()),
                Criteria.where("course_id").is(dto.getCourseId())
        ));
        if (mongoTemplate.exists(dupCheck, Subscription.class)) {
            throw new BusinessException(CommonConstant.DUPLICATE_SUBSCRIPTION);
        }

        if (user.getBalance() - course.getPrice() < 0) {
            throw new BusinessException(CommonConstant.BALANCE_INVALID);
        }

        Subscription subscription = Subscription.builder()
                .id(UUID.randomUUID().toString() + System.currentTimeMillis())
                .courseId(dto.getCourseId())
                .studentId(dto.getStudentId())
                .createdDate(Instant.now())
                .build();
        subscriptionRepository.insert(subscription);

        course.setStudentCount(course.getStudentCount() + 1);
        courseRepository.save(course);

        user.setBalance(user.getBalance() - course.getPrice());
        user.setUpdatedDate(Instant.now());
        userRepository.save(user);

        dto.setBalance(user.getBalance());
        log.info("Subscription created: student={} course={}", dto.getStudentId(), dto.getCourseId());
        return dto;
    }

    @Override
    public PagedResponse<CourseDto> searchCourseByUser(CommonSearchReq req) {
        SearchHelper.SearchResult<Subscription> result = searchHelper.search(req, Subscription.class);

        var courseIds = result.items().stream()
                .map(Subscription::getCourseId)
                .collect(Collectors.toList());

        var courses = courseRepository.findAllById(courseIds).stream()
                .filter(Objects::nonNull)
                .map(course -> {
                    CourseDto dto = mapper.map(course, CourseDto.class);
                    try {
                        dto.setTeacherName(userService.detail(course.getTeacherId()).getUserName());
                    } catch (Exception e) {
                        log.warn("Could not resolve teacher for teacherId={}", course.getTeacherId());
                        dto.setTeacherName("UNKNOWN");
                    }
                    return dto;
                })
                .collect(Collectors.toList());

        return new PagedResponse<>(result.total(), courses);
    }

    @Override
    public PagedResponse<UserDto> searchStudentByCourse(CommonSearchReq req) {
        SearchHelper.SearchResult<Subscription> result = searchHelper.search(req, Subscription.class);

        var studentIds = result.items().stream()
                .map(Subscription::getStudentId)
                .collect(Collectors.toList());

        var users = userRepository.findAllById(studentIds).stream()
                .filter(Objects::nonNull)
                .map(u -> mapper.map(u, UserDto.class))
                .collect(Collectors.toList());

        return new PagedResponse<>(result.total(), users);
    }

    @Override
    public boolean checkSubscription(String userId, String courseId) {
        Query query = new Query();
        query.addCriteria(new Criteria().andOperator(
                Criteria.where("student_id").is(userId),
                Criteria.where("course_id").is(courseId)
        ));
        return mongoTemplate.exists(query, Subscription.class);
    }
}
