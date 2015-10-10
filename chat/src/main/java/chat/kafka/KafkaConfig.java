package chat.kafka;

class KafkaConfig {
  static final String RAW_MSG_TOPIC = "raw-chat-messages";

  static final String BROKER_SERVERS = "localhost:9092";

  static final String ZOOKEEPER_SERVERS = "localhost:2181";

  static final String GROUP_ID_FOR_RAW_MSG = "raw-msg-group-id";
}
