package com.example.newsletter.repository;

import com.example.newsletter.entity.Content;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;

public interface ContentRepository extends JpaRepository<Content, Long> {
    List<Content> findBySendTimeBeforeAndSentFalse(LocalDateTime time);
}
