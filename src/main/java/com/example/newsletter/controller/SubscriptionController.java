package com.example.newsletter.controller;

import com.example.newsletter.entity.Subscription;
import com.example.newsletter.repository.SubscriptionRepository;
import com.example.newsletter.repository.SubscriberRepository;
import com.example.newsletter.repository.TopicRepository;
import com.example.newsletter.entity.Subscriber;
import com.example.newsletter.entity.Topic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/subscriptions")
public class SubscriptionController {

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private SubscriberRepository subscriberRepository;

    @Autowired
    private TopicRepository topicRepository;

    @GetMapping
    public List<Subscription> getAllSubscriptions() {
        return subscriptionRepository.findAll();
    }

    @PostMapping
    public Subscription createSubscription(@RequestBody Map<String, Object> payload) {
        Long subscriberId = Long.valueOf(payload.get("subscriberId").toString());
        Long topicId = Long.valueOf(payload.get("topicId").toString());

        Subscriber subscriber = subscriberRepository.findById(subscriberId).orElse(null);
        Topic topic = topicRepository.findById(topicId).orElse(null);
        if (subscriber == null || topic == null) return null;

        Subscription subscription = new Subscription();
        subscription.setSubscriber(subscriber);
        subscription.setTopic(topic);

        return subscriptionRepository.save(subscription);
    }

    @GetMapping("/{id}")
    public Subscription getSubscriptionById(@PathVariable Long id) {
        return subscriptionRepository.findById(id).orElse(null);
    }

    @DeleteMapping("/{id}")
    public void deleteSubscription(@PathVariable Long id) {
        subscriptionRepository.deleteById(id);
    }
}
