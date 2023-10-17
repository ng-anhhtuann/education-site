package vn.com.eduhub.controller.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import vn.com.eduhub.controller.req.UserAddReq;
import vn.com.eduhub.entity.User;
import vn.com.eduhub.repository.UserRepository;
import vn.com.eduhub.utils.CommonConstant;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserValidator {

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
        Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    @Autowired
    MongoTemplate mongoTemplate;
    @Autowired
    UserRepository userRepository;

    public static boolean check(String mail) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(mail);
        return matcher.find();
    }

    /**
     * Check userName - email có tồn tại trong hệ thống hay không
     */
    public void validateEdit(UserAddReq req) throws Exception {
        if (req.getId() != null && !req.getId().isEmpty() && !req.getId().isBlank()) {
            if (!userRepository.existsById(req.getId()))
                throw new Exception(CommonConstant.DATA_NOT_FOUND);
        } else {
            if (!check(req.getEmail()))
                throw new Exception(CommonConstant.VALID_EMAIL);

            Query queryEmail = new Query();
            queryEmail.addCriteria(Criteria.where("email").is(req.getEmail()));
            if (mongoTemplate.findOne(queryEmail, User.class) != null)
                throw new Exception(CommonConstant.DUPLICATE_EMAIL);

            Query queryUsername = new Query();
            queryUsername.addCriteria(Criteria.where("user_name").is(req.getUserName()));
            if (mongoTemplate.findOne(queryUsername, User.class) != null)
                throw new Exception(CommonConstant.DUPLICATE_USERNAME);

        }
    }

    /**
     * Validate data not found
     */
    public void validateId(String id) throws Exception {
        if (!userRepository.existsById(id))
            throw new Exception(CommonConstant.DATA_NOT_FOUND);
    }
}
