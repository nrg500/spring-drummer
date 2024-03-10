package nl.berwout;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("instruments")
public class InstrumentsController {

  String[] instruments = {"ClHat-08", "Kick-08", "Flam-01"};
  @GetMapping()
  public String getInstrument() {
    return instruments[(int)(Math.random() * instruments.length)];
  }
}
