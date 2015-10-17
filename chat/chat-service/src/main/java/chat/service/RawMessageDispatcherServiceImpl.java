package chat.service;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import chat.entity.MessageEntity;
import chat.kafka.dto.RawMessageDto;

@Component
public class RawMessageDispatcherServiceImpl implements RawMessageDispatcherService {

  @Autowired
  private SimpMessagingTemplate template;

  @Override
  public void process(RawMessageDto rawMessage) {
    ZonedDateTime dateTime = ZonedDateTime.now();
    String time = dateTime.format(DateTimeFormatter.ofPattern("hh:mm a"));
    
    MessageEntity msgEntity = MessageEntity.builder()
        .channelId(rawMessage.getChannelId())
        .userName(rawMessage.getUserName())
        .time(time)
        .content(rawMessage.getContent())
        .build();

    final String channelId = "/channel/" + rawMessage.getChannelId();
    template.convertAndSend(channelId, msgEntity);
  }

}
