package chat.consumer.service;

import org.springframework.stereotype.Component;

@Component
public interface CassandraConsumerService extends Runnable {
  void shutdown();
}
