package chat.service;

import java.util.List;

import org.springframework.stereotype.Component;

import chat.dto.RawMessage;

@Component
public interface MessageDispatcher {
  void process(RawMessage msg);

  void process(List<RawMessage> msgs);
}
