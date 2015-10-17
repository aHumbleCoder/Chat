package chat.consumer.service;

import org.springframework.stereotype.Component;

@Component
public interface RawMessageConsumerService extends Runnable {
  void shutdown();
}
