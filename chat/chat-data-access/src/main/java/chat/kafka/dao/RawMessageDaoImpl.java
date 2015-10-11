package chat.kafka.dao;

import java.util.Objects;
import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import chat.kafka.config.KafkaConfig;
import chat.kafka.dto.RawMessageDto;

@Component
public class RawMessageDaoImpl implements RawMessageDao {

  private static final String TOPIC = KafkaConfig.RAW_MSG_TOPIC;

  private static final ObjectMapper objectMapper = new ObjectMapper();

  private KafkaProducer<String, byte[]> producer = null;

  @Override
  public void save(RawMessageDto rawMessage) {
    configure();

    try {
      byte[] data = objectMapper.writeValueAsBytes(rawMessage);
      producer.send(new ProducerRecord<String, byte[]>(TOPIC, data));
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }

  private void configure() {
    if (Objects.isNull(producer)) {
      Properties props = new Properties();
      props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaConfig.BROKER_SERVERS);
      props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
          "org.apache.kafka.common.serialization.StringSerializer");
      props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
          "org.apache.kafka.common.serialization.ByteArraySerializer");
      producer = new KafkaProducer<String, byte[]>(props);
    }
  }

}
