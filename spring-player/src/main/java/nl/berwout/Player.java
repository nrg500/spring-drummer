package nl.berwout;

import jakarta.annotation.PostConstruct;
import java.net.URI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

@Component
public class Player {

  private static final Logger LOG = LoggerFactory.getLogger(Player.class);
  @Value("${ws-url}")
  private URI websocketUri;
  @Value("${audioFolder}")
  private String audioFolder;
  private final ConductorRestClient conductorRestClient;
  private final DrumPatternRepository drumPatternRepository;

  public Player(ConductorRestClient conductorRestClient, DrumPatternRepository drumPatternRepository) {
    this.conductorRestClient = conductorRestClient;
    this.drumPatternRepository = drumPatternRepository;
  }

  @PostConstruct
  public void init() {
    String instrument = conductorRestClient.getInstrument();
    LOG.info("Got instrument {}", instrument);
    DrumPattern pattern = drumPatternRepository.findByName(instrument);
    LOG.info("Will play pattern {}", pattern.pattern);

    WebSocketClient client = new StandardWebSocketClient();
    client.execute(new Musician(instrument, pattern.pattern, audioFolder), new WebSocketHttpHeaders(), websocketUri);
  }
}
