package chat.controller;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import chat.kafka.MessageConsumer;
import chat.kafka.MessageProducer;
import chat.model.Message;

@Controller
public class ChatController {
	@Autowired
	private SimpMessagingTemplate template;

	@Autowired
	private MessageProducer msgProducer;

	@Autowired
	private MessageConsumer msgConsumer;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView index() {
		return new ModelAndView("index");
	}

	@MessageMapping("/sendMessage")
	public void sendMessage(Message msg) throws Exception {
		msgProducer.send(msg.getChannelId(), msg.getUserName(),
				msg.getMessage());
	}

	@PostConstruct
	private void startMsgConsumer() {
		msgConsumer.run();
	}

	@PreDestroy
	private void cleanUp() {
		msgConsumer.shutdown();
	}
}
