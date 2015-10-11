package chat.kafka.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RawMessageDto implements Serializable {
  private static final long serialVersionUID = 8116004791227598002L;

  private String channelId;

  private String userName;

  private String content;
}
