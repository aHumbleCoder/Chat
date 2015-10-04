package chat.kafka;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RawMessage implements Serializable  {
	private static final long serialVersionUID = 8116004791227598002L;

	private String channelId;
	
	private String userName;
	
	private String content;
}
