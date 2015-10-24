package chat.cassandra.dao;

import org.springframework.stereotype.Component;

import chat.cassandra.dto.ChatMessageDto;

@Component
public interface ChatMessageDao {
  void save(ChatMessageDto message);
}
