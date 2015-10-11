package chat.service;

import chat.kafka.dto.RawMessageDto;

public interface RawMessageDispatcherService {
  void process(RawMessageDto rawMessage);
}
