package vn.com.eduhub.controller.validation;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import vn.com.eduhub.controller.req.UserAddReq;
import vn.com.eduhub.entity.User;
import vn.com.eduhub.exception.ValidationException;
import vn.com.eduhub.utils.CommonConstant;

import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class UserValidator {

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    private final MongoTemplate mongoTemplate;

    public void validateEdit(UserAddReq req) {
        if (req.getId() == null || req.getId().isBlank()) {
            validateCreate(req);
        }

        if (!req.getPassword().equals(req.getRePassword())) {
            throw new ValidationException(CommonConstant.REPASSWORD_FAIL);
        }
    }

    private void validateCreate(UserAddReq req) {
        if (!EMAIL_PATTERN.matcher(req.getEmail()).matches()) {
            throw new ValidationException(CommonConstant.VALID_EMAIL);
        }

        Query emailQuery = new Query(Criteria.where("email").is(req.getEmail()));
        if (mongoTemplate.findOne(emailQuery, User.class) != null) {
            throw new ValidationException(CommonConstant.DUPLICATE_EMAIL);
        }

        Query usernameQuery = new Query(Criteria.where("user_name").is(req.getUserName()));
        if (mongoTemplate.findOne(usernameQuery, User.class) != null) {
            throw new ValidationException(CommonConstant.DUPLICATE_USERNAME);
        }
    }
}
