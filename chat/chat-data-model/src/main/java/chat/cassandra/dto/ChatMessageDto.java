package chat.cassandra.dto;

import java.util.Date;

import com.datastax.driver.mapping.annotations.ClusteringColumn;
import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
CREATE TABLE chat_message (
    channel_id text,
    post_time timestamp,
    user_name text,
    content text,
    PRIMARY KEY (channel_id, post_time)
) WITH CLUSTERING ORDER BY (post_time DESC);
*/

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(keyspace = "chat", name = "chat_message")
public class ChatMessageDto {
  @PartitionKey
  @Column(name = "channel_id")
  private String channelId;
  
  @ClusteringColumn
  @Column(name = "post_time")
  private Date postTime;
  
  @Column(name = "user_name")
  private String userName;
  
  private String content;
}
