package vn.com.eduhub.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import vn.com.eduhub.entity.Image;

public interface ImageRepository extends MongoRepository<Image, String> {
}
