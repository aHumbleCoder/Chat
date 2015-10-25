package chat.kafka.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KafkaTimestampedMessage implements Serializable {
  private static final long serialVersionUID = 2475291145434027636L;

  private String channelId;

  private String userName;

  private String content;
  
  private Date dateTime;

}
