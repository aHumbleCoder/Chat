package chat.cassandraacess;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;

public final class SessionFactory {
  private static Session session;
  
  static {
    if (session == null) {
      Cluster cluster = Cluster.builder().addContactPoint("127.0.0.1").build();
      session = cluster.connect("chat");
    }
  }
  
  private SessionFactory() {
    
  }
  
  public static Session getSession() {
    return session;
  }
}
