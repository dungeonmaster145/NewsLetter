package com.example.newsletter.controller;

import com.example.newsletter.entity.Subscriber;
import com.example.newsletter.repository.SubscriberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/subscribers")
public class SubscriberController {

    @Autowired
    private SubscriberRepository subscriberRepository;

    @GetMapping
    public List<Subscriber> getAllSubscribers() {
        return subscriberRepository.findAll();
    }

    @PostMapping
    public Subscriber createSubscriber(@RequestBody Subscriber subscriber) {
        return subscriberRepository.save(subscriber);
    }

    @GetMapping("/{id}")
    public Subscriber getSubscriberById(@PathVariable Long id) {
        return subscriberRepository.findById(id).orElse(null);
    }

    @DeleteMapping("/{id}")
    public void deleteSubscriber(@PathVariable Long id) {
        subscriberRepository.deleteById(id);
    }
}
