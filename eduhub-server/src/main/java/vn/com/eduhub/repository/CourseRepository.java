package vn.com.eduhub.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import vn.com.eduhub.entity.Course;

public interface CourseRepository extends MongoRepository<Course, String> {

}
