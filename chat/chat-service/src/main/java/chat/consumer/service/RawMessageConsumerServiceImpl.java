package chat.consumer.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import chat.kafka.config.KafkaConfig;
import chat.kafka.dto.RawMessageDto;
import chat.service.RawMessageDispatcherService;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class RawMessageConsumerServiceImpl implements RawMessageConsumerService {

  private static final ObjectMapper objectMapper = new ObjectMapper();

  private ConsumerConnector consumer;

  private ExecutorService executor;

  @Autowired
  private RawMessageDispatcherService messageDispatcher;

  @Override
  public void run() {
    this.consumer = Consumer.createJavaConsumerConnector(getConsumerConfig());
    Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
    topicCountMap.put(KafkaConfig.RAW_MSG_TOPIC, new Integer(1));

    Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap =
        consumer.createMessageStreams(topicCountMap);
    KafkaStream<byte[], byte[]> stream = consumerMap.get(KafkaConfig.RAW_MSG_TOPIC).get(0);

    executor = Executors.newFixedThreadPool(1);
    executor.submit(new Runnable() {

      @Override
      public void run() {
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

    });
  }

  private ConsumerConfig getConsumerConfig() {
    Properties props = new Properties();
    props.put("zookeeper.connect", KafkaConfig.ZOOKEEPER_SERVERS);
    props.put("group.id", KafkaConfig.GROUP_ID_FOR_RAW_MSG);
    props.put("zookeeper.session.timeout.ms", "400");
    props.put("zookeeper.sync.time.ms", "200");
    props.put("auto.commit.interval.ms", "1000");

    return new ConsumerConfig(props);
  }

  @Override
  public void shutdown() {
    consumer.shutdown();
    executor.shutdown();
  }

}
