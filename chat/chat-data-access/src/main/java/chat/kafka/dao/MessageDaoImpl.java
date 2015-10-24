package chat.kafka.dao;

import org.springframework.stereotype.Component;

import chat.dto.MessageDto;
import chat.kafka.config.KafkaConfig;

@Component
public class MessageDaoImpl extends KafkaGeneralDao implements MessageDao {

  @Override
  public void save(MessageDto message) {
    super.savePayload(message);
  }

  @Override
  String getTopic() {
    return KafkaConfig.MSG_TOPIC;
  }

}
