package chat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import chat.entity.MessageEntity;
import chat.kafka.dao.RawMessageDao;
import chat.kafka.dto.RawMessageDto;

@Component
public class RawMessageProducerSerivceImpl implements RawMessageProducerSerivce {
  @Autowired
  private RawMessageDao rawMessageDao;

  @Override
  public void send(MessageEntity messageEntity) {
    RawMessageDto rawMessage =
        RawMessageDto.builder().channelId(messageEntity.getChannelId())
            .userName(messageEntity.getUserName()).content(messageEntity.getContent()).build();

    rawMessageDao.save(rawMessage);
  }
}
