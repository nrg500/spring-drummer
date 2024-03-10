package nl.berwout;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class SocketHandler extends TextWebSocketHandler {

  ConcurrentHashMap<String, WebSocketSession> sessions = new ConcurrentHashMap<>();
  private static final Logger LOG = LoggerFactory.getLogger(SocketHandler.class);


  public void broadcast(String s) {
    LOG.info("send to sessions: " + sessions.size());
    for (WebSocketSession webSocketSession : sessions.values()) {
      try {
        webSocketSession.sendMessage(new TextMessage(s));
      } catch (IOException e) {
        LOG.warn("Could not send message to session");
        sessions.remove(webSocketSession);
      }
    }
  }

  @Override
  public void afterConnectionEstablished(WebSocketSession session) {
    //the messages will be broadcasted to all users.
    sessions.put(String.valueOf(sessions.size()), session);
    LOG.info(sessions.toString());
  }
}
