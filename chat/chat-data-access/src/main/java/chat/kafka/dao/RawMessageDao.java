package chat.kafka.dao;

import chat.kafka.dto.RawMessageDto;

public interface RawMessageDao {
  void save(RawMessageDto rawMessage);
}
