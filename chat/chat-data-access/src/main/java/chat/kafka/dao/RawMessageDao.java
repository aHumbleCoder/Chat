package chat.kafka.dao;

import org.springframework.stereotype.Component;

import chat.kafka.dto.RawMessageDto;

@Component
public interface RawMessageDao {
  void save(RawMessageDto rawMessage);
}
