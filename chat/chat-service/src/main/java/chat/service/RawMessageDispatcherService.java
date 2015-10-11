package chat.service;

import org.springframework.stereotype.Component;

import chat.kafka.dto.RawMessageDto;

@Component
public interface RawMessageDispatcherService {
  void process(RawMessageDto rawMessage);
}
