package com.example.newsletter.controller;

import com.example.newsletter.entity.Content;
import com.example.newsletter.repository.ContentRepository;
import com.example.newsletter.entity.Topic;
import com.example.newsletter.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/contents")
public class ContentController {

    @Autowired
    private ContentRepository contentRepository;

    @Autowired
    private TopicRepository topicRepository;

    @GetMapping
    public List<Content> getAllContents() {
        return contentRepository.findAll();
    }

    @PostMapping
    public Content createContent(@RequestBody Map<String, Object> payload) {
        Long topicId = Long.valueOf(payload.get("topicId").toString());
        String text = (String) payload.get("text");
        String sendTimeStr = (String) payload.get("sendTime");
        LocalDateTime sendTime = LocalDateTime.parse(sendTimeStr);

        Topic topic = topicRepository.findById(topicId).orElse(null);
        if (topic == null) return null;

        Content content = new Content();
        content.setTopic(topic);
        content.setText(text);
        content.setSendTime(sendTime);

        return contentRepository.save(content);
    }

    @GetMapping("/{id}")
    public Content getContentById(@PathVariable Long id) {
        return contentRepository.findById(id).orElse(null);
    }

    @DeleteMapping("/{id}")
    public void deleteContent(@PathVariable Long id) {
        contentRepository.deleteById(id);
    }
}
