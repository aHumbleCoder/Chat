package chat.consumer.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import chat.kafka.config.KafkaConfig;

public abstract class GeneralConsumerService {

  private ConsumerConnector consumer;

  public ConsumerConfig getConsumerConfig() {
    Properties props = new Properties();
    props.put("zookeeper.connect", KafkaConfig.ZOOKEEPER_SERVERS);
    props.put("group.id", KafkaConfig.GROUP_ID_FOR_RAW_MSG);
    props.put("zookeeper.session.timeout.ms", "400");
    props.put("zookeeper.sync.time.ms", "200");
    props.put("auto.commit.interval.ms", "1000");

    return new ConsumerConfig(props);
  }

  public abstract String getTopic();

  public ConsumerConnector getConsumerConnector() {
    consumer = Consumer.createJavaConsumerConnector(getConsumerConfig());
    return consumer;
  }

  public KafkaStream<byte[], byte[]> getStream() {
    ConsumerConnector consumer = getConsumerConnector();
    Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
    topicCountMap.put(getTopic(), new Integer(1));

    Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap =
        consumer.createMessageStreams(topicCountMap);
    KafkaStream<byte[], byte[]> stream = consumerMap.get(getTopic()).get(0);

    return stream;
  }

  public void closeConsumer() {
    if (Objects.nonNull(consumer)) {
      consumer.shutdown();
    }
  }
}
