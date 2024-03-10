package nl.berwout.springdrummer;

import static java.util.concurrent.TimeUnit.SECONDS;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class ConductorConfig {

  private final short bpm = 180;
  private final int MS_IN_MINUTE = 60_000;


  @Scheduled(fixedRate = 15, timeUnit = SECONDS)
  public void scheduleFixedRateTask() {
    int beatsPlayed = -1;
    long currentTimeMillis = System.currentTimeMillis();
    long nextBeatTime = currentTimeMillis + (MS_IN_MINUTE / bpm);
    while(beatsPlayed++ < 31) {
      while(System.currentTimeMillis() < nextBeatTime){
        try {
          Thread.sleep(1);
        } catch (InterruptedException e) {
          throw new RuntimeException(e);
        }
      }
      System.out.println(beatsPlayed % 8 + 1);
      nextBeatTime = nextBeatTime + (MS_IN_MINUTE / bpm);
    }
  }


}
