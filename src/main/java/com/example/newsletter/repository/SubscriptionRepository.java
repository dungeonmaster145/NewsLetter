package com.example.newsletter.repository;

import com.example.newsletter.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    List<Subscription> findByTopicId(Long topicId);
}
