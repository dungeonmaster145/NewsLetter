package com.example.newsletter.scheduler;

import com.example.newsletter.entity.Content;
import com.example.newsletter.entity.Subscription;
import com.example.newsletter.repository.ContentRepository;
import com.example.newsletter.repository.SubscriptionRepository;
import com.example.newsletter.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class NewsletterScheduler {

    @Autowired
    private ContentRepository contentRepository;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private EmailService emailService;

    @Scheduled(fixedRate = 60000) // Run every 60 seconds
    public void sendNewsletters() {
        LocalDateTime now = LocalDateTime.now();
        List<Content> contents = contentRepository.findBySendTimeBeforeAndSentFalse(now);

        for (Content content : contents) {
            List<Subscription> subscriptions = subscriptionRepository.findByTopicId(content.getTopic().getId());

            for (Subscription subscription : subscriptions) {
                emailService.sendEmail(
                    subscription.getSubscriber().getEmail(),
                    "Newsletter: " + content.getTopic().getName(),
                    content.getText()
                );
            }

            content.setSent(true);
            contentRepository.save(content);
        }
    }
}
