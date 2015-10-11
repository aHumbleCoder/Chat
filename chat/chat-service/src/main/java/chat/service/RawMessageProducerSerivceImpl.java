package chat.service;

import chat.entity.MessageEntity;
import chat.kafka.dao.RawMessageDao;
import chat.kafka.dto.RawMessageDto;

public class RawMessageProducerSerivceImpl implements RawMessageProducerSerivce {
  private RawMessageDao rawMessageDao;

  @Override
  public void send(MessageEntity messageEntity) {
    RawMessageDto rawMessage =
        RawMessageDto.builder().channelId(messageEntity.getChannelId())
            .userName(messageEntity.getUserName()).content(messageEntity.getContent()).build();

    rawMessageDao.save(rawMessage);
  }
}
