package chat.kafka.dao;

import org.springframework.stereotype.Component;

import chat.dto.ChatMessageDto;

@Component
public interface MessageDao {
  void save(ChatMessageDto message);
}
