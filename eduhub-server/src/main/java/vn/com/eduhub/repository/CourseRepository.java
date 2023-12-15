package vn.com.eduhub.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import vn.com.eduhub.entity.Course;

import java.util.ArrayList;
import java.util.List;

public interface CourseRepository extends MongoRepository<Course, String> {

    @Query("{ \"$and\": [ { \"price\": { \"$gte\": ?0 } }, { \"price\": { \"$lte\": ?1 } }, { \"tag_list\": { \"$in\": ?2 } }, { \"title\": { \"$regex\": ?3, \"$options\": \"i\" } } ] }")
    List<Course> listCourseByCondition(Integer min, Integer max, ArrayList<String> tagList, String title);
}
