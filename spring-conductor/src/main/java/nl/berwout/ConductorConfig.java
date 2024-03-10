package nl.berwout;

import static java.util.concurrent.TimeUnit.SECONDS;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class ConductorConfig {

  private static final Logger LOG = LoggerFactory.getLogger(ConductorConfig.class);
  private final short bpm = 180;
  private final int MS_IN_MINUTE = 60_000;
  private final SocketHandler socketHandler;

  public ConductorConfig(SocketHandler socketHandler) {
    this.socketHandler = socketHandler;
  }

  @Scheduled(fixedRate = 15, timeUnit = SECONDS)
  public void scheduleFixedRateTask() {
    LOG.info("scheduled");
    int beatsPlayed = -1;
    long currentTimeMillis = System.currentTimeMillis();
    long nextBeatTime = currentTimeMillis + (MS_IN_MINUTE / bpm);
    while (++beatsPlayed < 32) {
      while (System.currentTimeMillis() < nextBeatTime) {
        try {
          Thread.sleep(1);
        } catch (InterruptedException e) {
          throw new RuntimeException(e);
        }
      }
      socketHandler.broadcast(Integer.toString(beatsPlayed % 8 + 1));
      nextBeatTime = nextBeatTime + (MS_IN_MINUTE / bpm);
    }
  }


}
