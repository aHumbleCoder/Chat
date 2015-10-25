package chat.service;

import org.springframework.stereotype.Component;

import chat.kafka.dto.KafkaRawMessage;

@Component
public interface RawMessageDispatcherService {
  void process(KafkaRawMessage rawMessage);
}
