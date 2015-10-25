package chat.kafka.dao;

import org.springframework.stereotype.Component;

import chat.kafka.dto.KafkaTimestampedMessage;

@Component
public interface MessageDao {
  void save(KafkaTimestampedMessage message);
}
