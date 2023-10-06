package vn.com.eduhub.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import vn.com.eduhub.entity.User;

public interface UserRepository extends MongoRepository<User, String> {
}
