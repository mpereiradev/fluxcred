package dev.matheuspereira.fluxcred;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest(classes = {FluxCredApplication.class})
class FluxCredApplicationTests {

  @Test
  void contextLoads() {
  }

}
