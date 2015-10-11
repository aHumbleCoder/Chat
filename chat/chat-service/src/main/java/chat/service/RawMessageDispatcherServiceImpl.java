package chat.service;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import chat.kafka.dto.RawMessageDto;

@Component
public class RawMessageDispatcherServiceImpl implements RawMessageDispatcherService {

  @Autowired
  private SimpMessagingTemplate template;

  @Override
  public void process(RawMessageDto rawMessage) {
    ZonedDateTime dateTime = ZonedDateTime.now();
    String time = dateTime.format(DateTimeFormatter.ofPattern("hh:mm a"));
    rawMessage.setContent("(" + time + ") " + rawMessage.getContent());

    final String channelId = "/channel/" + rawMessage.getChannelId();
    template.convertAndSend(channelId, rawMessage);
  }

}
