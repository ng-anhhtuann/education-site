package vn.com.eduhub.controller.validation;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import vn.com.eduhub.controller.req.CourseAddReq;
import vn.com.eduhub.entity.Course;
import vn.com.eduhub.entity.User;
import vn.com.eduhub.exception.ResourceNotFoundException;
import vn.com.eduhub.exception.ValidationException;
import vn.com.eduhub.utils.CommonConstant;

@Component
@RequiredArgsConstructor
public class CourseValidator {

    private final MongoTemplate mongoTemplate;

    public void validateEdit(CourseAddReq req) {
        if (req.getId() == null || req.getId().isBlank()) {
            validateCreate(req);
        } else {
            validateUpdate(req);
        }
    }

    private void validateCreate(CourseAddReq req) {
        Query titleQuery = new Query(Criteria.where("title").is(req.getTitle()));
        if (mongoTemplate.findOne(titleQuery, Course.class) != null) {
            throw new ValidationException(CommonConstant.DUPLICATE_TITLE);
        }

        Query teacherQuery = new Query(Criteria.where("id").is(req.getTeacherId()));
        User teacher = mongoTemplate.findOne(teacherQuery, User.class);
        if (teacher == null) {
            throw new ResourceNotFoundException(CommonConstant.USER_NOT_FOUND);
        }
        if (!"TEACHER".equals(teacher.getRole())) {
            throw new ValidationException(CommonConstant.ROLE_NOT_MATCH);
        }
    }

    private void validateUpdate(CourseAddReq req) {
        Query titleQuery = new Query(Criteria.where("title").is(req.getTitle()).and("id").ne(req.getId()));
        if (mongoTemplate.findOne(titleQuery, Course.class) != null) {
            throw new ValidationException(CommonConstant.DUPLICATE_TITLE);
        }
    }
}
