package chat.kafka;

import org.springframework.stereotype.Component;

@Component
public interface MessageProducer {
  void send(String channelId, String userName, String message);
}
