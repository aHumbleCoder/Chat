package chat.kafka.dao;

import org.springframework.stereotype.Component;

import chat.kafka.config.KafkaConfig;
import chat.kafka.dto.KafkaRawMessage;

@Component
public class RawMessageDaoImpl extends KafkaGeneralDao implements RawMessageDao  {

  @Override
  public void save(KafkaRawMessage rawMessage) {
    super.savePayload(rawMessage);
  }

  @Override
  String getTopic() {
    return KafkaConfig.RAW_MSG_TOPIC;
  }

}
