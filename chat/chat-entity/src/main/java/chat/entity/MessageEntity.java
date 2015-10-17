package chat.entity;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageEntity implements Serializable {
  private static final long serialVersionUID = 5432096974864448846L;

  private String channelId;

  private String userName;

  private String content;

  private String time;
}
