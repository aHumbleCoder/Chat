package chat.cassandra.dao;

import org.springframework.stereotype.Component;

import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;

import chat.cassandra.dto.ChatMessageDto;
import chat.cassandraacess.SessionFactory;

@Component
public class ChatMessageDaoImpl implements ChatMessageDao {

  @Override
  public void save(ChatMessageDto message) {
    Mapper<ChatMessageDto> mapper =
        new MappingManager(SessionFactory.getSession()).mapper(ChatMessageDto.class);
    
    mapper.save(message);
  }

}
