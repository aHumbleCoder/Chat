package chat.kafka.dao;

import org.springframework.stereotype.Component;

import chat.kafka.dto.MessageDto;

@Component
public interface MessageDao {
  void save(MessageDto message);
}
