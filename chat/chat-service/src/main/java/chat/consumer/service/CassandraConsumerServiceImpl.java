package chat.consumer.service;

import java.io.IOException;

import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import chat.cassandra.dao.ChatMessageDao;
import chat.cassandra.dto.ChatMessageDto;
import chat.kafka.config.KafkaConfig;
import chat.kafka.dto.MessageDto;

@Component
public class CassandraConsumerServiceImpl extends GeneralConsumerService implements
    CassandraConsumerService {

  private static final ObjectMapper objectMapper = new ObjectMapper();

  @Autowired
  private ChatMessageDao chatMessageDao;

  @Override
  public void run() {
    KafkaStream<byte[], byte[]> stream = getStream();
    ConsumerIterator<byte[], byte[]> it = stream.iterator();
    while (it.hasNext()) {
      byte[] data = it.next().message();

      try {
        MessageDto msg = objectMapper.readValue(data, MessageDto.class);
        ChatMessageDto chatMessageDto =
            ChatMessageDto.builder().channelId(msg.getChannelId()).postTime(msg.getDateTime())
                .userName(msg.getUserName()).content(msg.getContent()).build();

        chatMessageDao.save(chatMessageDto);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }
  }

  @Override
  public void shutdown() {
    super.closeConsumer();
  }

  @Override
  public String getTopic() {
    return KafkaConfig.MSG_TOPIC;
  }

}
