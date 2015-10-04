package chat.service;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import chat.dto.RawMessage;

@Component
public class MessageDispatcherImpl implements MessageDispatcher {
	@Autowired
	private SimpMessagingTemplate template;

	@Override
	public void process(RawMessage msg) {
		ZonedDateTime dateTime = ZonedDateTime.now();
		String time = dateTime.format(DateTimeFormatter.ofPattern("hh:mm a"));
		msg.setContent("(" + time + ") " + msg.getContent());

		final String channelId = "/channel/" + msg.getChannelId();
		template.convertAndSend(channelId, msg);
	}

	@Override
	public void process(List<RawMessage> msgs) {

	}

}
