package chat.controller;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import chat.consumer.service.RawMessageConsumerService;
import chat.entity.MessageEntity;
import chat.service.RawMessageProducerSerivce;

@Controller
public class ChatController {

  @Autowired
  private SimpMessagingTemplate template;

  @Autowired
  private RawMessageConsumerService msgConsumer;

  @Autowired
  private RawMessageProducerSerivce rawMessageProducer;

  @RequestMapping(value = "/", method = RequestMethod.GET)
  public ModelAndView index() {
    return new ModelAndView("index");
  }

  @MessageMapping("/sendMessage")
  public void sendMessage(MessageEntity msg) throws Exception {
    rawMessageProducer.send(msg);
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
