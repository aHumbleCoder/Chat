package chat.controller;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import chat.model.Message;

@Controller
public class ChatController {
	@Autowired
	private SimpMessagingTemplate template;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView index() {
		return new ModelAndView("index");
	}

	@MessageMapping("/sendMessage")
	public void sendMessage(Message msg) throws Exception {
		ZonedDateTime dateTime = ZonedDateTime.now();
		String time = dateTime.format(DateTimeFormatter.ofPattern("hh:mm a"));
		msg.setMessage("(" + time + ") " + msg.getMessage());

		final String channelId = "/channel/" + msg.getChannelId();
		template.convertAndSend(channelId, msg);
	}
}
