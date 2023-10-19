package vn.com.eduhub.controller.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import vn.com.eduhub.controller.req.CourseAddReq;
import vn.com.eduhub.entity.Course;
import vn.com.eduhub.entity.User;
import vn.com.eduhub.repository.CourseRepository;
import vn.com.eduhub.repository.UserRepository;
import vn.com.eduhub.utils.CommonConstant;

@Service
public class CourseValidator {

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    UserRepository userRepository;

    public void validateEdit(CourseAddReq req) throws Exception {
        if (req.getId() == null) {
            Query query = new Query();
            query.addCriteria(Criteria.where("title").is(req.getTitle()));
            if (mongoTemplate.findOne(query, Course.class) != null)
                throw new Exception(CommonConstant.DUPLICATE_TITLE);

            Query queryId = new Query();
            Criteria criteriaId = Criteria.where("id").is(req.getTeacherId());
            queryId.addCriteria(criteriaId);
            if (mongoTemplate.findOne(queryId, User.class) == null)
                throw new Exception(CommonConstant.USER_NOT_FOUND);

            Query queryRole = new Query();
            Criteria criteriaRole = Criteria.where("role").is("TEACHER");
            queryRole.addCriteria(criteriaRole.andOperator(criteriaId));
            if (mongoTemplate.findOne(queryRole, User.class) == null)
                throw new Exception(CommonConstant.ROLE_NOT_MATCH);

        }
    }

}
