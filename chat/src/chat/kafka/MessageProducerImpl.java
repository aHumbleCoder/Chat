package chat.kafka;

import java.util.Objects;
import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class MessageProducerImpl implements MessageProducer {
	private static final String TOPIC = "raw-chat-messages";

	private static final ObjectMapper objectMapper = new ObjectMapper();

	private KafkaProducer<String, byte[]> producer = null;

	@Override
	public void send(String channelId, String userName, String message) {
		configure();

		try {
			byte[] data = objectMapper.writeValueAsBytes(new RawMessage(
					userName, message));
			producer.send(new ProducerRecord<String, byte[]>(TOPIC, channelId,
					data));
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}

	private void configure() {
		if (Objects.isNull(producer)) {
			Properties props = new Properties();
			props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
			props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
					"org.apache.kafka.common.serialization.StringSerializer");
			props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
					"org.apache.kafka.common.serialization.ByteArraySerializer");
			producer = new KafkaProducer<String, byte[]>(props);
		}
	}
}
