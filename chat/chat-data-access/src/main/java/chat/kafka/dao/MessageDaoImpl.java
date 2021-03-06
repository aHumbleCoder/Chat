package chat.kafka.dao;

import org.springframework.stereotype.Component;

import chat.kafka.config.KafkaConfig;
import chat.kafka.dto.KafkaTimestampedMessage;

@Component
public class MessageDaoImpl extends KafkaGeneralDao implements MessageDao {

  @Override
  public void save(KafkaTimestampedMessage message) {
    super.savePayload(message);
  }

  @Override
  String getTopic() {
    return KafkaConfig.MSG_TOPIC;
  }

}
