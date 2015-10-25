package chat.kafka.dao;

import org.springframework.stereotype.Component;

import chat.kafka.dto.KafkaRawMessage;

@Component
public interface RawMessageDao {
  void save(KafkaRawMessage rawMessage);
}
