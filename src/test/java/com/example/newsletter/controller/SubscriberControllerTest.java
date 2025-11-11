package com.example.newsletter.controller;

import com.example.newsletter.entity.Subscriber;
import com.example.newsletter.repository.SubscriberRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SubscriberController.class)
public class SubscriberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SubscriberRepository subscriberRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetAllSubscribers() throws Exception {
        Subscriber subscriber = new Subscriber();
        subscriber.setId(1L);
        subscriber.setEmail("test@example.com");

        List<Subscriber> subscribers = Arrays.asList(subscriber);

        when(subscriberRepository.findAll()).thenReturn(subscribers);

        mockMvc.perform(get("/api/subscribers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].email").value("test@example.com"));
    }

    @Test
    public void testCreateSubscriber() throws Exception {
        Subscriber subscriber = new Subscriber();
        subscriber.setId(1L);
        subscriber.setEmail("test@example.com");

        when(subscriberRepository.save(any(Subscriber.class))).thenReturn(subscriber);

        mockMvc.perform(post("/api/subscribers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(subscriber)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("test@example.com"));
    }
}
