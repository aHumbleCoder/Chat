package chat.consumer.service;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import chat.kafka.config.KafkaConfig;
import chat.kafka.dto.RawMessageDto;
import chat.service.RawMessageDispatcherService;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class RawMessageConsumerServiceImpl extends GeneralConsumerService implements RawMessageConsumerService {

  private static final ObjectMapper objectMapper = new ObjectMapper();

  @Autowired
  private RawMessageDispatcherService messageDispatcher;

  @Override
  public void run() {
    KafkaStream<byte[], byte[]> stream = getStream();
    ConsumerIterator<byte[], byte[]> it = stream.iterator();
    while (it.hasNext()) {
      byte[] data = it.next().message();

      try {
        RawMessageDto msg = objectMapper.readValue(data, RawMessageDto.class);
        messageDispatcher.process(msg);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }
  }

  @Override
  public void shutdown() {
    super.closeConsumer();
  }

  @Override
  public String getTopic() {
    return KafkaConfig.RAW_MSG_TOPIC;
  }

}
