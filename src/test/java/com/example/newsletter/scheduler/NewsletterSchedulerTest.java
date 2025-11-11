package com.example.newsletter.scheduler;

import com.example.newsletter.entity.Content;
import com.example.newsletter.entity.Subscriber;
import com.example.newsletter.entity.Subscription;
import com.example.newsletter.entity.Topic;
import com.example.newsletter.repository.ContentRepository;
import com.example.newsletter.repository.SubscriptionRepository;
import com.example.newsletter.service.EmailService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class NewsletterSchedulerTest {

    @Mock
    private ContentRepository contentRepository;

    @Mock
    private SubscriptionRepository subscriptionRepository;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private NewsletterScheduler newsletterScheduler;

    @Test
    public void testSendNewsletters() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime past = now.minusMinutes(1);

        Topic topic = new Topic();
        topic.setId(1L);
        topic.setName("Test Topic");

        Subscriber subscriber = new Subscriber();
        subscriber.setId(1L);
        subscriber.setEmail("test@example.com");

        Subscription subscription = new Subscription();
        subscription.setSubscriber(subscriber);
        subscription.setTopic(topic);

        Content content = new Content();
        content.setId(1L);
        content.setTopic(topic);
        content.setText("Test Content");
        content.setSendTime(past);
        content.setSent(false);

        List<Content> contents = Arrays.asList(content);
        List<Subscription> subscriptions = Arrays.asList(subscription);

        when(contentRepository.findBySendTimeBeforeAndSentFalse(now)).thenReturn(contents);
        when(subscriptionRepository.findByTopicId(1L)).thenReturn(subscriptions);

        newsletterScheduler.sendNewsletters();

        verify(emailService).sendEmail("test@example.com", "Newsletter: Test Topic", "Test Content");
        verify(contentRepository).save(content);
        assert content.isSent();
    }
}
