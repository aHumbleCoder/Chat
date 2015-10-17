package chat.kafka.dao;

import java.util.Objects;
import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

import chat.kafka.config.KafkaConfig;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class KafkaGeneralDao {
  private static final ObjectMapper objectMapper = new ObjectMapper();
  
  private KafkaProducer<String, byte[]> producer = null;
  
  abstract String getTopic();
  
  void savePayload(Object payload) {
    configure();

    try {
      byte[] data = objectMapper.writeValueAsBytes(payload);
      producer.send(new ProducerRecord<String, byte[]>(getTopic(), data));
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
