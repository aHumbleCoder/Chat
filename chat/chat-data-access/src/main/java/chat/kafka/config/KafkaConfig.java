package chat.kafka.config;

public final class KafkaConfig {
  static public final String RAW_MSG_TOPIC = "raw-chat-messages";
  
  static public final String MSG_TOPIC = "chat-messages";

  static public final String BROKER_SERVERS = "localhost:9092";

  static public final String ZOOKEEPER_SERVERS = "localhost:2181";

  static public final String GROUP_ID_FOR_RAW_MSG = "raw-msg-group-id";
}
