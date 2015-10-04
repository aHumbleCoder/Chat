package chat.kafka;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class MessageConsumerImpl implements MessageConsumer {

	private static final ObjectMapper objectMapper = new ObjectMapper();

	@Autowired
	private SimpMessagingTemplate template;

	private ExecutorService executor;

	private KafkaConsumer<String, byte[]> consumer;

	@Override
	public void run() {
		Properties props = getConsumerProps();
		consumer = new KafkaConsumer<String, byte[]>(props);
		consumer.subscribe(KafkaConfig.RAW_MSG_TOPIC);

		executor = Executors.newFixedThreadPool(1);
		executor.submit(new Runnable() {

			@Override
			public void run() {
				while (true) {
					Map<String, ConsumerRecords<String, byte[]>> data = consumer
							.poll(0);
					process(data.get(KafkaConfig.RAW_MSG_TOPIC));
				}
			}

			private void process(ConsumerRecords<String, byte[]> data) {
				List<ConsumerRecord<String, byte[]>> records = data.records();
				for (ConsumerRecord<String, byte[]> record : records) {
					try {
						RawMessage msg = objectMapper.readValue(record.value(),
								RawMessage.class);

						ZonedDateTime dateTime = ZonedDateTime.now();
						String time = dateTime.format(DateTimeFormatter
								.ofPattern("hh:mm a"));
						msg.setContent("(" + time + ") " + msg.getContent());

						final String channelId = "/channel/"
								+ msg.getChannelId();
						template.convertAndSend(channelId, msg);
					} catch (Exception e) {

					}
				}
			}

		});
	}

	private Properties getConsumerProps() {
		Properties props = new Properties();

		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
				KafkaConfig.BROKER_SERVERS);
		props.put(ConsumerConfig.GROUP_ID_CONFIG,
				KafkaConfig.GROUP_ID_FOR_RAW_MSG);
		props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
		props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
		props.put(ConsumerConfig.SESSION_TIMEOUT_MS, "30000");
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
				"org.apache.kafka.common.serializers.StringSerializer");
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
				"org.apache.kafka.common.serializers.StringSerializer");

		return props;
	}

	@Override
	public void shutdown() {
		executor.shutdown();
	}

}
