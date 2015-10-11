package chat.service;

import chat.entity.MessageEntity;

public interface RawMessageProducerSerivce {
  void send(MessageEntity messageEntity);
}
