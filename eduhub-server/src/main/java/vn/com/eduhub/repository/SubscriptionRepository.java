package vn.com.eduhub.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import vn.com.eduhub.entity.Subscription;

public interface SubscriptionRepository extends MongoRepository<Subscription, String>{

}
