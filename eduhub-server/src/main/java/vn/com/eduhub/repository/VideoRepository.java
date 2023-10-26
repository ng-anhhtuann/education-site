package vn.com.eduhub.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import vn.com.eduhub.entity.Video;

public interface VideoRepository extends MongoRepository<Video, String> {
}
