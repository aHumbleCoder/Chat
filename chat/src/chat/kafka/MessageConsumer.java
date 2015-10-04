package chat.kafka;

import org.springframework.stereotype.Component;

@Component
public interface MessageConsumer {
	void run();
	
	void shutdown();
}
