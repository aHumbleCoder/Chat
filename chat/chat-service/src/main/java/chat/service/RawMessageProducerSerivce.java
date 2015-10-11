package chat.service;

import org.springframework.stereotype.Component;

import chat.entity.MessageEntity;

@Component
public interface RawMessageProducerSerivce {
  void send(MessageEntity messageEntity);
}
