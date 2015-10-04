package chat.kafka;

import java.util.Objects;
import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.stereotype.Component;

import chat.dto.RawMessage;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class MessageProducerImpl implements MessageProducer {
	private static final String TOPIC = KafkaConfig.RAW_MSG_TOPIC;

	private static final ObjectMapper objectMapper = new ObjectMapper();

	private KafkaProducer<String, byte[]> producer = null;

	@Override
	public void send(String channelId, String userName, String message) {
		configure();

		try {
			byte[] data = objectMapper.writeValueAsBytes(new RawMessage(
					channelId, userName, message));
			producer.send(new ProducerRecord<String, byte[]>(TOPIC, data));
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}

	private void configure() {
		if (Objects.isNull(producer)) {
			Properties props = new Properties();
			props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
					KafkaConfig.BROKER_SERVERS);
			props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
					"org.apache.kafka.common.serializers.StringSerializer");
			props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
					"org.apache.kafka.common.serialization.ByteArraySerializer");
			producer = new KafkaProducer<String, byte[]>(props);
		}
	}
}
